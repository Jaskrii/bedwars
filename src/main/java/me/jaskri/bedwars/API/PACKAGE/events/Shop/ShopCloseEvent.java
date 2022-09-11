package me.jaskri.bedwars.API.PACKAGE.events.Shop;

import org.bukkit.event.HandlerList;

public class ShopCloseEvent extends ShopEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private final GamePlayer player;

    public ShopCloseEvent(GamePlayer player, Shop shop, Category category) {
        super(shop, category);
        this.player = player;
    }

    public GamePlayer getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
