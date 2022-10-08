package me.jaskri.Shop.Item;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Team.Team;
import me.jaskri.API.events.Player.GamePlayerItemBuyEvent;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class ShopItem extends AbstractShopItem{

    private ItemStack unbuyable;
    private ItemStack buyable;
    private ItemStack exist;
    private ItemStack raw;
    private Material toReplace;
    private boolean isPermanent;

    public ShopItem(String name, ItemStack raw, ItemStack display, ItemCost cost, ItemDescription desc, Material replace, boolean permanent) {
        super(name, cost, desc);
        Preconditions.checkNotNull(raw, "Raw item cannot be null!");
        this.isPermanent = permanent;
        this.toReplace = replace;
        this.initItems(display, raw);
    }

    public ShopItem(String name, ItemStack raw, ItemStack display, ItemCost cost, ItemDescription desc, boolean permanent) {
        this(name, raw, display, cost, desc, (Material)null, permanent);
    }

    public ShopItem(String name, ItemStack raw, ItemCost cost, ItemDescription desc, Material replace, boolean isPermanent) {
        this(name, raw, (ItemStack)null, cost, desc, replace, isPermanent);
    }

    public ShopItem(String name, ItemStack raw, ItemCost cost, ItemDescription desc, boolean isPermanent) {
        this(name, raw, (ItemStack)null, cost, desc, (Material)null, isPermanent);
    }

    public ShopItem(String name, ItemStack raw, ItemCost cost, ItemDescription desc) {
        this(name, raw, cost, desc, false);
    }

    private void initItems(ItemStack display, ItemStack raw) {
        ItemManager manager = new ItemManager(display != null ? display : raw.clone());
        ItemStack[] display_items = ShopUtils.toShopDisplayItems(manager, this.name, this.cost, this.desc);
        this.unbuyable = display_items[0];
        this.buyable = display_items[1];
        if (this.isPermanent) {
            List<String> lore = manager.getLore();
            lore.set(lore.size() - 1, "§cYou already have this item!");
            this.exist = manager.setName(ChatColor.RED + this.name).getItem().clone();
        }

        this.raw = raw;
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            Player player = gp.getPlayer();
            if (this.isPermanent && player.getInventory().contains(this.raw.getType())) {
                return this.exist.clone();
            } else {
                return ShopUtils.hasEnough(player, this.cost) ? this.buyable.clone() : this.unbuyable.clone();
            }
        }
    }

    public ItemStack getRawItem(GamePlayer gp) {
        ItemStack result = this.raw.clone();
        this.checkColor(result, gp.getTeam());
        return result;
    }

    public ShopItemType getShopItemType() {
        return ShopItemType.ITEM;
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            ItemStack raw = this.getRawItem(gp);
            Player player = gp.getPlayer();
            if (this.isPermanent && player.getInventory().contains(raw.getType())) {
                player.sendMessage("§cYou've already purchased this item!");
                return false;
            } else {
                GamePlayerItemBuyEvent event = new GamePlayerItemBuyEvent(gp, this, "§aYou purchased §6" + this.name);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled() && ShopUtils.buyItem(gp, this)) {
                    if (this.toReplace != null) {
                        player.getInventory().remove(this.toReplace);
                    }

                    if (this.isPermanent) {
                        gp.getInventory().addItem(raw);
                    }

                    player.sendMessage(event.getBuyMessage());
                    XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean isPermanent() {
        return this.isPermanent;
    }

    private void checkColor(ItemStack item, Team team) {
        Material type = item.getType();
        String typeString = type.toString();
        if (typeString.endsWith("WOOL")) {
            TeamUtils.getTeamColoredWool(team).setType(item);
        } else if (typeString.endsWith("STAINED_GLASS")) {
            TeamUtils.getTeamColoredGlass(team).setType(item);
        } else if (typeString.endsWith("STAINED_GLASS_PANE")) {
            TeamUtils.getTeamColoredGlassPane(team).setType(item);
        } else if (typeString.endsWith("TERRACOTTA") || typeString.equals("HARD_CLAY")) {
            TeamUtils.getTeamColoredTerracotta(team).setType(item);
        }

    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.isPermanent, this.raw, this.unbuyable, this.buyable});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ShopItem)) {
            return false;
        } else {
            ShopItem other = (ShopItem)obj;
            return this.isPermanent == other.isPermanent && this.raw.equals(other.raw) && this.unbuyable.equals(other.unbuyable) && this.buyable.equals(other.buyable);
        }
    }

    public String toString() {
        return "ShopItem [Name=" + this.name + ", Cost=" + this.cost + ", Description=" + this.desc + ", Permanent=" + this.isPermanent + "]";
    }
}
