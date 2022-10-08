package me.jaskri.API.events.Player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDamageEvent  extends GamePlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private EntityDamageEvent.DamageCause cause;

    public GamePlayerDamageEvent(GamePlayer player, EntityDamageEvent.DamageCause cause) {
        super(player);
        this.cause = cause;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return this.cause;
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
