package me.jaskri.API.events.trap;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class TrapBuyEvent extends TrapEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private TrapItem item;
    private String message;
    private boolean isCancelled;

    public TrapBuyEvent(TrapItem item, GamePlayer buyer, String message) {
        super(item.getTrap());
        this.item = item;
        this.message = message;
    }

    public TrapItem getItem() {
        return this.item;
    }

    public String getBuyMessage() {
        return this.message;
    }

    public void setBuyMessage(String message) {
        this.message = message;
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
