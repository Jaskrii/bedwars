package me.jaskri.API.bed.fireball;

import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class FireBallLaunchEvent extends FireBallEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;

    public FireballLaunchEvent(GamePlayer owner, Fireball fireball) {
        super(owner, fireball);
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
