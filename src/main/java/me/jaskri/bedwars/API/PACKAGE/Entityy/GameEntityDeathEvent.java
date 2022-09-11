package me.jaskri.bedwars.API.PACKAGE.Entityy;

import me.jaskri.bedwars.API.PACKAGE.Entity.GameEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameEntityDeathEvent extends GameEntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private GamePlayer killer;
    private boolean isCancelled;

    public GameEntityDeathEvent(GameEntity entity, GamePlayer killer) {
        super(entity);
        this.killer = killer;
    }

    public GamePlayer getKiller() {
        return this.killer;
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