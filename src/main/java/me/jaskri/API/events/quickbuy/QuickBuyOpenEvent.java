package me.jaskri.API.events.quickbuy;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class QuickBuyOpenEvent extends QuickBuyEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;

    public QuickBuyOpenEvent(GamePlayer player, QuickBuy quickbuy) {
        super(player, quickbuy);
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
