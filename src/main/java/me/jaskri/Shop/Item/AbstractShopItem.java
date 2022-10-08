package me.jaskri.Shop.Item;

import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Shop.Item.Item;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;

public abstract class AbstractShopItem implements Item {

    protected String name;
    protected ItemCost cost;
    protected ItemDescription desc;

    protected AbstractShopItem(String name, ItemCost cost, ItemDescription desc) {
        this.name = name;
        this.cost = cost != null ? cost : new ItemCost(Resource.FREE, 0);
        this.desc = desc != null ? desc : new ItemDescription();
    }

    public String getName() {
        return this.name;
    }

    public ItemDescription getDescription() {
        return this.desc.clone();
    }

    public void setDescription(ItemDescription desc) {
        if (desc != null) {
            this.desc = desc.clone();
        }

    }

    public ItemCost getCost() {
        return this.cost.clone();
    }

    public void setCost(ItemCost cost) {
        if (cost != null) {
            this.cost = cost.clone();
        }

    }
}
