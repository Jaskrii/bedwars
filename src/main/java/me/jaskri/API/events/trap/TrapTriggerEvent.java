package me.jaskri.API.events.trap;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class TrapTriggerEvent extends TrapEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private GamePlayer who;
    private boolean isCancelled;

    public TrapTriggerEvent(Trap trap, GamePlayer who) {
        super(trap);
        this.who = who;
    }

    public GamePlayer getPlayer() {
        return this.who;
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
