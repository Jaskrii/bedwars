package me.jaskri.API.Entityy;

import me.jaskri.API.Entity.GameEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameEntityDamageByGamePlayerEvent extends GameEntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private GamePlayer damager;
    private boolean isCancelled;

    public GameEntityDamageByGamePlayerEvent(GameEntity damaged, GamePlayer damager) {
        super(damaged);
        this.damager = damager;
    }

    public GamePlayer getDamager() {
        return this.damager;
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
