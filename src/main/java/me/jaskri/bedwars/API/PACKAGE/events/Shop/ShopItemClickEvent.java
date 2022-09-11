package me.jaskri.bedwars.API.PACKAGE.events.Shop;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ShopItemClickEvent extends ShopEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private final GamePlayer gp;
    private final Buyable item;

    public ShopItemClickEvent(GamePlayer player, Shop shop, Category category, Buyable item) {
        super(shop, category);
        this.gp = player;
        this.item = item;
    }

    public GamePlayer getWhoClicked() {
        return this.gp;
    }

    public Buyable getClickedItem() {
        return this.item;
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
