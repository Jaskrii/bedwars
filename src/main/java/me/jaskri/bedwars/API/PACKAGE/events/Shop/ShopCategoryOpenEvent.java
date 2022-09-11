package me.jaskri.bedwars.API.PACKAGE.events.Shop;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ShopCategoryOpenEvent extends ShopEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private final GamePlayer gp;

    public ShopCategoryOpenEvent(GamePlayer player, Shop shop, Category category) {
        super(shop, category);
        this.gp = player;
    }

    public GamePlayer getPlayer() {
        return this.gp;
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
