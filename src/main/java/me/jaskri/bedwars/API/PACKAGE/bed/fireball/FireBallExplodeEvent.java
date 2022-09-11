package me.jaskri.bedwars.API.PACKAGE.bed.fireball;

import org.bukkit.entity.Fireball;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class FireBallExplodeEvent extends FireBallEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private Collection<Entity> entities;
    private boolean isCancelled;

    public FireballExplodeEvent(GamePlayer owner, Fireball fireball, Collection<Entity> entities) {
        Super(owner, fireball);
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
