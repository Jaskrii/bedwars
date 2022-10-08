package me.jaskri.API.events.Player;

import org.bukkit.event.HandlerList;

public class GamePlayerRespawnEvent extends GamePlayerEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private String message;

    public GamePlayerRespawnEvent(GamePlayer player, String respawnMessage) {
        super(player);
        this.message = respawnMessage;
    }

    public String getRespawnMessage() {
        return this.message;
    }

    public void setRespawnMessage(String message) {
        this.message = message;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
