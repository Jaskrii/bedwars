package me.jaskri.bedwars.API.PACKAGE.events.quickbuy;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class QuickBuyEditEvent extends QuickBuyEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;

    public QuickBuyEditEvent(GamePlayer owner, QuickBuy quickbuy, Buyable buyable, QuickBuyAction action) {
        super(owner, quickbuy);
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

    public static enum QuickBuyAction {
        REMOVE,
        REPLACE,
        SET;

        private QuickBuyAction() {
        }
    }
}
