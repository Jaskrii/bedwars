package me.jaskri.bedwars.API.PACKAGE.Upgrade.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Shop.Item.ItemCost;

public class TieredUpgradeItemTier {

    private String name;
    private ItemCost cost;

    public TieredUpgradeItemTier(String name, ItemCost cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public ItemCost getCost() {
        return this.cost;
    }
}
