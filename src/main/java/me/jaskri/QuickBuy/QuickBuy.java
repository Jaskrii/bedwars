package me.jaskri.QuickBuy;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.Manager.ItemManager;
import me.jaskri.Shop.AbstractShopCategory;
import me.jaskri.Util.ShopUtils;
import me.jaskri.Util.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuickBuy extends AbstractShopCategory implements me.jaskri.API.Shop.QuickBuy {

    private static final ItemStack DEFAULT_DISPLAY_ITEM;
    private static final ItemStack EMPTY_SLOT;

    public QuickBuy(ItemStack display, Map<Integer, Buyable> items) {
        super("Quick Buy", display != null ? display : DEFAULT_DISPLAY_ITEM, items);
        this.initDisplayItem();
    }

    public QuickBuy(ItemStack display) {
        this(display, (Map)null);
    }

    public QuickBuy() {
        this((ItemStack)null);
    }

    private void initDisplayItem() {
        if (this.getDisplayItem() != DEFAULT_DISPLAY_ITEM) {
            ItemManager manager = new ItemManager(this.getDisplayItem());
            manager.addItemFlags(ItemFlag.values());
            manager.setName(ChatColor.AQUA + this.getName());
            this.getDisplayItem() = manager.getItem();
        }
    }

    public void applyItems(Inventory inv, GamePlayer gp) {
        if (inv != null && gp != null) {
            for(int i = 0; i < 21; ++i) {
                int slot = ShopUtils.getValidIndex(i);
                Buyable buyable = (Buyable)this.getItems().get(slot);
                if (buyable != null) {
                    inv.setItem(slot, buyable.getDisplayItem(gp));
                } else {
                    inv.setItem(slot, EMPTY_SLOT);
                }
            }

        }
    }

    public String toString() {
        return "QuickBuy [" + this.getItems() + "]";
    }

    static {
        ItemManager display = new ItemManager(Material.NETHER_STAR);
        display.addItemFlags(ItemFlag.values());
        display.setName("§bQuick Buy");
        DEFAULT_DISPLAY_ITEM = display.getItem();
        ItemManager manager = new ItemManager(XMaterial.RED_STAINED_GLASS_PANE.parseItem());
        manager.addItemFlags(ItemFlag.values());
        manager.setName("§cEmpty Slot!");
        List<String> lore = new ArrayList<>(4);
        lore.add("§7This is a Quick Buy Slot!");
        lore.add("§bSneak Click §7any item in the");
        lore.add("§7shop to add it here.");
        manager.setLore(lore);
        EMPTY_SLOT = manager.getItem();
    }
}
