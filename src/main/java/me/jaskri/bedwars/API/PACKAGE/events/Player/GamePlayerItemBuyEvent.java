package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GamePlayerItemBuyEvent extends GamePlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private Buyable item;
    private String message;
    private boolean isCancelled;

    public GamePlayerItemBuyEvent(GamePlayer player, Buyable item, String message) {
        super(player);
        this.item = item;
        this.message = message;
    }

    public Buyable getItem() {
        return this.item;
    }

    public String getBuyMessage() {
        return this.message;
    }

    public void setBuyMessage(String message) {
        this.message = message;
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
