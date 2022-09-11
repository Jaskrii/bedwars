package me.jaskri.bedwars.API.PACKAGE.events.Shop;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopOpenEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private final Shop shop;
    private final GamePlayer gp;

    public ShopOpenEvent(GamePlayer player, Shop shop) {
        this.gp = player;
        this.shop = shop;
    }

    public GamePlayer getPlayer() {
        return this.gp;
    }

    public Shop getShop() {
        return this.shop;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
