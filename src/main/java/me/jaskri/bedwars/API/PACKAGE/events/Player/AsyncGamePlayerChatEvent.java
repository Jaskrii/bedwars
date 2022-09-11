package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class AsyncGamePlayerChatEvent extends GamePlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private String format = "%1$s%2$s §r%3$s§r: %4$s";
    private String message;
    private boolean isCancelled;

    public AsyncGamePlayerChatEvent(GamePlayer player, String message) {
        super(player, true);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
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
