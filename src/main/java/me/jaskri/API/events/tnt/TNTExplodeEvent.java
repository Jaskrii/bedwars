package me.jaskri.API.events.tnt;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class TNTExplodeEvent extends TntEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Collection<Entity> entities;
    private boolean isCancelled;

    public TNTExplodeEvent(GamePlayer gp, TNTPrimed tnt, Collection<Entity> entities) {
        super(gp, tnt);
        this.entities = entities;
    }

    public Collection<Entity> entities() {
        return this.entities;
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
