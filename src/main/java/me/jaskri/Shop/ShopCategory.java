package me.jaskri.Shop;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.Manager.ItemManager;
import me.jaskri.Util.ShopUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.Map;

public class ShopCategory extends AbstractShopCategory{

    public ShopCategory(String name, ItemStack item, Map<Integer, Buyable> items) {
        super(name, item, items);
        this.initDisplayItem(item);
        this.setItems(items);
    }

    public ShopCategory(String name, ItemStack item) {
        this(name, item, (Map)null);
    }

    private void initDisplayItem(ItemStack item) {
        ItemManager manager = new ItemManager(item);
        manager.addItemFlags(ItemFlag.values());
        manager.setName(ChatColor.GREEN + this.name);
        manager.addToLore("§eClick for more!");
        this.display = manager.getItem();
    }

    public void applyItems(Inventory inv, GamePlayer gp) {
        if (inv != null && gp != null) {
            Iterator var3 = this.items.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<Integer, Buyable> entry = (Map.Entry)var3.next();
                Buyable buyable = (Buyable)entry.getValue();
                if (buyable != null) {
                    int slot = (Integer)entry.getKey();
                    if (ShopUtils.isValidIndex(slot)) {
                        inv.setItem(slot, buyable.getDisplayItem(gp));
                    } else {
                        inv.setItem(ShopUtils.getValidIndex(slot), buyable.getDisplayItem(gp));
                    }
                }
            }

        }
    }

    public String toString() {
        return "ShopCategory [Name=" + this.getName() + ", Items=" + this.items + "]";
    }
}
