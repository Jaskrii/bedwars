package me.jaskri.bedwars.API.PACKAGE.events.Player;

import org.bukkit.event.HandlerList;

public class GamePlayerEliminateEvent extends GamePlayerEvent{

    private static final HandlerList HANDLERS = new HandlerList();

    public GamePlayerEliminateEvent(GamePlayer player) {
        super(player);
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
