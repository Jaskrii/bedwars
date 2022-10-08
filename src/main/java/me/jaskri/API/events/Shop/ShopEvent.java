package me.jaskri.API.events.Shop;

import org.bukkit.event.Event;

public class ShopEvent extends Event {

    protected Shop shop;
    protected Category category;

    public ShopEvent(Shop shop, Category category) {
        this.shop = shop;
        this.category = category;
    }

    public final Shop getShop() {
        return this.shop;
    }

    public final Category getShopCategory() {
        return this.category;
    }
}
