package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDeathEvent extends GamePlayerEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private EntityDamageEvent.DamageCause cause;
    private String message;

    public GamePlayerDeathEvent(GamePlayer player, EntityDamageEvent.DamageCause cause, String message) {
        super(player);
        this.cause = cause;
        this.message = message;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return this.cause;
    }

    public String getDeathMessage() {
        return this.message;
    }

    public void setDeathMessage(String message) {
        if (message != null) {
            this.message = message;
        }

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
