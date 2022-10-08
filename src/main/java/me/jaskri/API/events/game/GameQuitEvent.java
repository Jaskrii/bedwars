package me.jaskri.API.events.game;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GameQuitEvent extends GameEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private String message;

    public GameQuitEvent(Game game, Player player, String message) {
        super(game);
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getQuitMessage() {
        return this.message;
    }

    public void setQuitMessage(String message) {
        this.message = message;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
