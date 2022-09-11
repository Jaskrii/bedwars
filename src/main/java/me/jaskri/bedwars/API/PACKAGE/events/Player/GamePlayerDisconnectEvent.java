package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.HandlerList;

public class GamePlayerDisconnectEvent extends GamePlayerEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private String message;

    public GamePlayerDisconnectEvent(GamePlayer player, String message) {
        super(player);
        this.message = message;
    }

    public String getDisconnectMessage() {
        return this.message;
    }

    public void setDisconnectMessage(String msg) {
        if (msg != null) {
            this.message = msg;
        }

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}


