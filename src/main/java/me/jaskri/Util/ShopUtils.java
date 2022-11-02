package me.jaskri.Util;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Shop.Item.Item;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.Manager.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ShopUtils {

    public ShopUtils() {
    }

    public static boolean hasEnough(Player player, ItemCost cost) {
        return hasEnough(player, cost.getResource(), cost.getPrice());
    }

    public static boolean hasEnough(Player player, Resource rsc, int amount) {
        return player.getInventory().contains(rsc.getMaterial(), amount);
    }

    public static boolean removeCost(Player player, ItemCost cost) {
        return InventoryUtils.removeItem(player.getInventory(), cost.getResource().getMaterial(), cost.getPrice());
    }

    public static boolean removeCost(Player player, Resource rsc, int price) {
        return InventoryUtils.removeItem(player.getInventory(), rsc.getMaterial(), price);
    }

    public static boolean buyItem(GamePlayer gp, Item item) {
        Player player = gp.getPlayer();
        ItemCost cost = item.getCost();
        int needed = getAmountNeeded(player, cost);
        if (needed > 0) {
            player.sendMessage("§cYou don't have enough " + formatResource(cost) + "! Need " + needed + " more!");
            return false;
        } else {
            ItemStack raw = item.getRawItem(gp);
            if (!InventoryUtils.canAddItem(player.getInventory(), raw.getType(), raw.getAmount())) {
                player.sendMessage("§cYour inventory is full!");
                return false;
            } else if (!removeCost(player, cost)) {
                return false;
            } else {
                player.getInventory().addItem(new ItemStack[]{raw});
                return true;
            }
        }
    }

    public static ItemStack[] toShopDisplayItems(ItemManager manager, String name, ItemCost cost, ItemDescription desc) {
        manager.addItemFlags(ItemFlag.values());
        List<String> lore = new ArrayList(desc.getSize() + 4);
        lore.add("§7Cost: " + formatCost(cost));
        lore.add((Object)null);
        if (!desc.isEmpty()) {
            desc.apply(lore);
            lore.add((Object)null);
        }

        manager.setLore(lore);
        lore.add("§cYou don't have enough " + formatResource(cost) + "!");
        ItemStack unbuyable = manager.setName(ChatColor.RED + name).getItem().clone();
        lore.set(lore.size() - 1, "§eClick to purchase!");
        ItemStack buyable = manager.setName(ChatColor.GREEN + name).getItem().clone();
        return new ItemStack[]{unbuyable, buyable};
    }

    public static void setBuyable(Player player, ItemManager manager, ItemCost cost) {
        setBuyable(player, manager, cost.getResource(), cost.getPrice());
    }

    public static void setBuyable(Player player, ItemManager manager, Resource rsc, int price) {
        int needed = getAmountNeeded(player, rsc, price);
        if (needed > 0) {
            manager.addToLore("§cYou don't have enough " + formatResource(rsc, price) + "!");
            manager.setName(ChatColor.RED + manager.getName());
        } else {
            manager.addToLore("§eClick to purchase!");
            manager.setName(ChatColor.GREEN + manager.getName());
        }

    }

    public static String formatResource(ItemCost cost) {
        return formatResource(cost.getResource(), cost.getPrice());
    }

    public static String formatResource(Resource rsc, int price) {
        String result = rsc.getName();
        return price > 1 && (rsc == Resource.DIAMOND || rsc == Resource.EMERALD) ? result + "s" : result;
    }

    public static String formatCost(ItemCost cost) {
        return formatCost(cost.getResource(), cost.getPrice());
    }

    public static String formatCost(Resource rsc, int price) {
        return rsc == Resource.FREE ? "§aFREE" : "" + rsc.getChatColor() + price + " " + formatResource(rsc, price);
    }

    public static int getAmountNeeded(Player player, ItemCost cost) {
        return getAmountNeeded(player, cost.getResource(), cost.getPrice());
    }

    public static int getAmountNeeded(Player player, Resource rsc, int price) {
        if (rsc == Resource.FREE) {
            return 0;
        } else {
            Material type = rsc.getMaterial();
            ListIterator var4 = player.getInventory().iterator();

            while(var4.hasNext()) {
                ItemStack item = (ItemStack)var4.next();
                if (item != null && item.getType() == type) {
                    price -= item.getAmount();
                    if (price <= 0) {
                        return 0;
                    }
                }
            }

            return price;
        }
    }

    public static int getValidIndex(int index) {
        int result = Math.abs(index);
        if (index > 21) {
            result = index % 21;
        }

        if (result >= 0 && result <= 6) {
            return result + 19;
        } else if (result >= 7 && result <= 13) {
            return result + 21;
        } else {
            return result >= 14 && result <= 20 ? result + 23 : result + 21;
        }
    }

    public static boolean isValidIndex(int index) {
        return index >= 19 && index <= 25 || index >= 28 && index <= 34 || index >= 37 && index <= 43;
    }
}
