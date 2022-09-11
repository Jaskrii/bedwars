package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GamePlayerBlockPlaceEvent extends GamePlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private Block block;
    private boolean isCancelled;

    public GamePlayerBlockPlaceEvent(GamePlayer player, Block block) {
        super(player);
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public Material getType() {
        return this.block.getType();
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
