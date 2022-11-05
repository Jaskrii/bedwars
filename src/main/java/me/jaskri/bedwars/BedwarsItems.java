package me.jaskri.bedwars;

import me.jaskri.Manager.ItemManager;
import me.jaskri.Util.XMaterial;
import org.bukkit.inventory.ItemStack;

public class BedwarsItems {

    private static BedwarsItems instance;
    private ItemStack sword;

    private BedwarsItems() {
    }

    public ItemStack getSword() {
        if (this.sword != null) {
            return this.sword;
        } else {
            this.sword = (new ItemManager(XMaterial.WOODEN_SWORD.parseItem())).setUnbreakable(true).getItem();
            return this.sword;
        }
    }

    public static BedwarsItems getInstance() {
        if (instance == null) {
            instance = new BedwarsItems();
        }

        return instance;
    }
}
