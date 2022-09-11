package me.jaskri.bedwars.API.PACKAGE.events.game;

import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends GameEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final GameStateChangeEvent oldState;
    private final GameStateChangeEvent newState;

    public GameStateChangeEvent(Game game, GameStateChangeEvent oldState, GameStateChangeEvent newState) {
        super(game);
        this.oldState = oldState;
        this.newState = newState;
    }

    public GameStateChangeEvent getOldState() {
        return this.oldState;
    }

    public GameStateChangeEvent getNewState() {
        return this.newState;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
