package me.jaskri.Shop.Item;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.ArmorType;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.events.Player.GamePlayerItemBuyEvent;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArmorShopItem extends AbstractShopItem{

    private ItemStack unbuyable;
    private ItemStack buyable;
    private ItemStack unlocked;
    private ItemStack high_tier;
    private ArmorType type;

    public ArmorShopItem(String name, ItemStack display, ArmorType type, ItemCost cost, ItemDescription desc) {
        super(name, cost, desc);
        Preconditions.checkNotNull(type, "Armor type cannot be null!");
        this.type = type;
        if (name == null) {
            this.name = "Permanent " + type + " Armor";
        }

        this.initDisplayItem(display);
    }

    private void initDisplayItem(ItemStack item) {
        ItemManager manager = new ItemManager(item != null ? item : new ItemStack(this.type.getBoots()));
        ItemStack[] display_items = ShopUtils.toShopDisplayItems(manager, this.name, this.cost, this.desc);
        this.unbuyable = display_items[0];
        this.buyable = display_items[1];
        List<String> lore = manager.getLore();
        lore.set(lore.size() - 1, "§aUNLOCKED!");
        this.unlocked = manager.setName(ChatColor.GREEN + this.name).getItem().clone();
        lore.set(lore.size() - 1, "§cYou already have a higher tier item.");
        this.high_tier = manager.setName(ChatColor.GREEN + this.name).getItem().clone();
    }

    public ItemStack getRawItem(GamePlayer gp) {
        return null;
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            ArmorType armor = gp.getArmorType();
            if (armor != null) {
                if (armor == this.type) {
                    return this.unlocked.clone();
                }

                if (armor.ordinal() > this.type.ordinal()) {
                    return this.high_tier.clone();
                }
            }

            return ShopUtils.hasEnough(gp.getPlayer(), this.cost) ? this.buyable.clone() : this.unbuyable.clone();
        }
    }

    public ShopItemType getShopItemType() {
        return ShopItemType.ARMOR;
    }

    public ArmorType getArmorType() {
        return this.type;
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            Player player = gp.getPlayer();
            ArmorType armor = gp.getArmorType();
            if (armor != null) {
                if (armor == this.type) {
                    player.sendMessage("§cYou already have this armor equipped!");
                    return false;
                }

                if (armor.ordinal() > this.type.ordinal()) {
                    player.sendMessage("§cYou already have a higher tier armor");
                    return false;
                }
            }

            int needed = ShopUtils.getAmountNeeded(player, this.cost);
            if (needed > 0) {
                player.sendMessage("§cYou don't have enough " + ShopUtils.formatResource(this.cost) + "! Need " + needed + " more!");
                return false;
            } else {
                GamePlayerItemBuyEvent event = new GamePlayerItemBuyEvent(gp, this, "§aYou purchased §6" + this.name);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled() && ShopUtils.removeCost(player, this.cost)) {
                    TeamUtils.setPlayerArmor(player, gp.getTeam(), this.type);
                    gp.setArmorType(this.type);
                    player.sendMessage(event.getBuyMessage());
                    XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
