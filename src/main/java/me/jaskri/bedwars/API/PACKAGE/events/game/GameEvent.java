package me.jaskri.bedwars.API.PACKAGE.events.game;

import org.bukkit.event.Event;

public abstract class GameEvent extends Event {

    protected Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    public GameEvent(Game game, boolean isAsync) {
        super(isAsync);
        this.game = game;
    }

    public final Game getGame() {
        return this.game;
    }
}
