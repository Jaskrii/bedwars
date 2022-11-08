package me.jaskri.Listener;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.NPC.Npc;
import me.jaskri.API.NPC.Shopkeeper;
import me.jaskri.API.NPC.Upgrader;
import me.jaskri.API.Shop.Category;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.Shop.Shop;
import me.jaskri.API.Upgrade.Shop.UpgradeShop;
import me.jaskri.API.User.User;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.events.Shop.ShopCloseEvent;
import me.jaskri.API.events.Shop.ShopItemClickEvent;
import me.jaskri.API.events.quickbuy.QuickBuyCloseEvent;
import me.jaskri.API.events.quickbuy.QuickBuyEditEvent;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class ShopListener implements Listener {

    public ShopListener() {
    }

    @EventHandler
    public void onGamePlayerDamageNPC(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Npc npc = Bedwars.getInstance().getNPCManager().getNPC(event.getEntity());
            if (npc != null) {
                this.openNPCShop((Player)event.getDamager(), npc);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGamePlayerNPCClick(PlayerInteractEntityEvent event) {
        NPC npc = Bedwars.getInstance().getNPCManager().getNPC(event.getRightClicked());
        if (npc != null) {
            this.openNPCShop(event.getPlayer(), npc);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGamePlayerShopInteract(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            Player player = (Player)event.getWhoClicked();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted()) {
                GamePlayer gp = game.getGamePlayer(player);
                if (event.getView().getTitle().equalsIgnoreCase("Upgrades & Traps")) {
                    UpgradeShop shop = Bedwars.getInstance().getTeamUpgradeShop(game.getMode());
                    Buyable item = shop.getItem(event.getSlot());
                    if (item != null) {
                        item.onBuy(gp);
                        shop.openShop(gp);
                        GamePlayerListener.updateTeam(gp);
                        GamePlayerListener.checkForSword(gp);
                    }

                    event.setCancelled(true);
                } else {
                    Shop shop = Bedwars.getInstance().getTeamShop(game.getMode());
                    Category current = this.getCurrentOpenedCategory(shop, event.getView());
                    if (current != null) {
                        event.setCancelled(true);
                        if (event.getRawSlot() <= 53) {
                            ItemStack clicked = event.getCurrentItem();
                            if (clicked != null) {
                                if (event.getRawSlot() <= 8) {
                                    shop.openShop(gp, event.getRawSlot());
                                } else {
                                    Buyable buyable = current.getItem(event.getRawSlot());
                                    if (buyable == null) {
                                        return;
                                    }

                                    if (event.isShiftClick() && current instanceof QuickBuy) {
                                        User user = Bedwars.getInstance().getUser(player);
                                        if (user != null) {
                                            QuickBuy qb = user.getQuickBuy(game.getMode());
                                            if (qb == null) {
                                                return;
                                            }

                                            QuickBuyEditEvent qbEvent = new QuickBuyEditEvent(gp, qb, buyable, QuickBuyAction.REMOVE);
                                            Bukkit.getPluginManager().callEvent(qbEvent);
                                            if (qbEvent.isCancelled()) {
                                                return;
                                            }

                                            qb.removeItem(event.getRawSlot());
                                            shop.openShop(gp, current);
                                            return;
                                        }
                                    }

                                    ShopItemClickEvent bwEvent = new ShopItemClickEvent(gp, shop, current, buyable);
                                    Bukkit.getPluginManager().callEvent(bwEvent);
                                    if (bwEvent.isCancelled()) {
                                        return;
                                    }

                                    buyable.onBuy(gp);
                                    shop.openShop(gp, current);
                                    GamePlayerListener.updateTeam(gp);
                                    GamePlayerListener.checkForSword(gp);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerShopClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null && game.hasStarted()) {
            GamePlayer gp = game.getGamePlayer(player);
            Shop shop = Bedwars.getInstance().getTeamShop(game.getMode());
            Category current = this.getCurrentOpenedCategory(shop, event.getView());
            if (current != null) {
                if (current instanceof QuickBuy) {
                    QuickBuyCloseEvent bwEvent = new QuickBuyCloseEvent(gp, (QuickBuy)current);
                    Bukkit.getPluginManager().callEvent(bwEvent);
                } else {
                    ShopCloseEvent bwEvent = new ShopCloseEvent(gp, shop, current);
                    Bukkit.getPluginManager().callEvent(bwEvent);
                }

            }
        }
    }

    private Category getCurrentOpenedCategory(Shop shop, InventoryView view) {
        Iterator var3 = shop.getCategories().iterator();

        Category category;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            category = (Category)var3.next();
        } while(!view.getTitle().equals(category.getName()));

        return category;
    }

    private void openNPCShop(Player player, NPC npc) {
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null && game.hasStarted() && !game.isSpectator(player)) {
            if (npc instanceof Shopkeeper) {
                Bedwars.getInstance().getTeamShop(game.getMode()).openShop(game.getGamePlayer(player));
            } else if (npc instanceof Upgrader) {
                Bedwars.getInstance().getTeamUpgradeShop(game.getMode()).openShop(game.getGamePlayer(player));
            }

        }
    }
}
