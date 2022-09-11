package me.jaskri.bedwars.API.PACKAGE.bed;

import me.jaskri.bedwars.API.PACKAGE.arena.BedwarsBed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BedBreakEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final GamePlayer gamePlayer;
    private final BedwarsBed bed;
    private String message;
    private boolean isCancelled;

    public BedBreakEvent(GamePlayer player, BedwarsBed bed, String breakMessage) {
        this.gamePlayer = player;
        this.bed = bed;
        this.message = breakMessage;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public BedwarsBed getBed() {
        return this.bed;
    }

    public String getBreakMessage() {
        return this.message;
    }

    public void setBreakMessage(String message) {
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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
