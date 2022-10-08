package me.jaskri.API.events.tnt;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class TNTPlaceEvent extends TntEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;

    public TNTPlaceEvent(GamePlayer gp, TNTPrimed tnt) {
        super(gp, tnt);
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
