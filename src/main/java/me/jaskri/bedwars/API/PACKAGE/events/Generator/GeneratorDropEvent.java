package me.jaskri.bedwars.API.PACKAGE.events.Generator;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GeneratorDropEvent extends GeneratorEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;

    public GeneratorDropEvent(ItemGenerator gen) {
        super(gen);
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
