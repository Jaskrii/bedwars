package me.jaskri.API.events.game;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GameJoinEvent extends GameEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private String message;
    private boolean isCancelled;

    public GameJoinEvent(Game game, Player player, String message) {
        super(game);
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getJoinMessage() {
        return this.message;
    }

    public void setJoinMessage(String message) {
        this.message = message;
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
}
