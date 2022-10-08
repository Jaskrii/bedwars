package me.jaskri.Upgrade.Shop.Item;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Upgrade.Shop.Item.TrapItem;
import me.jaskri.API.events.trap.TrapBuyEvent;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TrapShopItem implements TrapItem {

    private String name;
    private ItemStack display;
    private ItemDescription desc;
    private Trap trap;

    public TrapShopItem(String name, ItemStack display, ItemDescription desc, Trap trap) {
        this.name = name;
        this.desc = desc;
        this.trap = trap;
        this.initDisplayItem(display);
    }

    private void initDisplayItem(ItemStack item) {
        ItemManager manager = new ItemManager(item.getType(), item.getAmount(), item.getDurability());
        manager.setName(ChatColor.RESET + this.name);
        List<String> lore = new ArrayList();
        if (!this.desc.isEmpty()) {
            this.desc.apply(lore);
            lore.add((Object)null);
        }

        manager.addItemFlags(ItemFlag.values());
        manager.setLore(lore);
        this.display = manager.getItem();
    }

    public String getName() {
        return this.name;
    }

    public Trap getTrap() {
        return this.trap;
    }

    public ItemDescription getDescription() {
        return this.desc.clone();
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return null;
            } else {
                TrapManager trapManager = team.getTrapManager();
                List<Trap> traps = trapManager.getTraps();
                int price = (int)Math.pow(2.0, !traps.isEmpty() ? (double)(traps.size() - 1) : 0.0);
                ItemManager itemManager = new ItemManager(this.display.clone());
                itemManager.addToLore("§7Cost: " + ShopUtils.formatCost(Resource.DIAMOND, price));
                itemManager.addToLore((String)null);
                if (traps.contains(this.trap)) {
                    itemManager.addEnchantment(Enchantment.WATER_WORKER, 1, true);
                }

                if (traps.size() >= 3) {
                    itemManager.addToLore("§cYou reached traps limit!");
                } else {
                    int needed = ShopUtils.getAmountNeeded(gp.getPlayer(), Resource.DIAMOND, price);
                    if (needed > 0) {
                        itemManager.addToLore("§cYou don't have enough " + ShopUtils.formatResource(Resource.DIAMOND, price) + "!");
                        itemManager.setName(ChatColor.RED + this.name);
                    } else {
                        itemManager.addToLore("§eClick to purchase!e");
                        itemManager.setName(ChatColor.GREEN + this.name);
                    }
                }

                return itemManager.getItem();
            }
        }
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return false;
            } else {
                Player player = gp.getPlayer();
                TrapManager trapManager = team.getTrapManager();
                List<Trap> traps = trapManager.getTraps();
                if (traps.size() >= 3) {
                    player.sendMessage("§cYou reached traps limit!");
                    return false;
                } else {
                    TrapBuyEvent event = new TrapBuyEvent(this, gp, "§aYou purchased §6" + this.name + "§a!");
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return false;
                    } else {
                        int price = (int)Math.pow(2.0, !traps.isEmpty() ? (double)(traps.size() - 1) : 0.0);
                        int needed = ShopUtils.getAmountNeeded(player, Resource.DIAMOND, price);
                        if (needed > 0) {
                            player.sendMessage("§cYou don't have enough Diamond! Need " + needed + " more!");
                            return false;
                        } else if (!ShopUtils.removeCost(player, Resource.DIAMOND, price)) {
                            return false;
                        } else {
                            trapManager.addTrap(this.trap);
                            player.sendMessage(event.getBuyMessage());
                            XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                            return true;
                        }
                    }
                }
            }
        }
    }
}
