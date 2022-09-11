package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GamePlayerReconnectEvent extends GamePlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private String message;
    private boolean isCancelled;

    public GamePlayerReconnectEvent(GamePlayer player, String message) {
        super(player);
        this.message = message;
    }

    public String getReconnectMessage() {
        return this.message;
    }

    public void setReconnectMessage(String msg) {
        if (msg != null) {
            this.message = msg;
        }

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
