package me.jaskri.Util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public InventoryUtils() {
    }

    public static int getAmount(Inventory inv, Material type) {
        if (inv != null && type != null && type != Material.AIR) {
            int result = 0;
            ItemStack[] var3 = inv.getContents();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ItemStack item = var3[var5];
                if (item != null && item.getType() == type) {
                    result += item.getAmount();
                }
            }

            return result;
        } else {
            return 0;
        }
    }

    public static boolean removeItem(Inventory inv, Material type, int amount) {
        if (inv != null && type != null) {
            if (type != Material.AIR && amount > 0) {
                int rest = amount;
                ItemStack[] items = inv.getContents();

                for(int i = 0; i < items.length; ++i) {
                    ItemStack item = items[i];
                    if (item != null && item.getType() == type) {
                        if (rest >= item.getAmount()) {
                            rest -= item.getAmount();
                            inv.setItem(i, (ItemStack)null);
                        } else {
                            item.setAmount(item.getAmount() - rest);
                            rest = 0;
                        }
                    }

                    if (rest == 0) {
                        return true;
                    }
                }

                return rest == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean canAddItem(Inventory inv, ItemStack item) {
        return item != null && canAddItem(inv, item.getType(), item.getAmount());
    }

    public static boolean canAddItem(Inventory inv, Material type, int amount) {
        if (inv != null && type != null && amount > 0) {
            ItemStack[] content = inv.getContents();
            ItemStack[] var4 = content;
            int var5 = content.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                ItemStack item = var4[var6];
                if (item == null || item.getType() == type && item.getAmount() + amount <= inv.getMaxStackSize()) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
