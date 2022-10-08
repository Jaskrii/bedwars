package me.jaskri.API.events.Player;

public class GamePlayerEvent extends GamePlayerEvent{

    protected final GamePlayerEvent player;

    public GamePlayerEvent(GamePlayerEvent player) {
        this.player = player;
    }

    public GamePlayerEvent(GamePlayerEvent player, boolean async) {
        super(async);
        this.player = player;
    }

    public final GamePlayerEvent getGamePlayer() {
        return this.player;
    }
}
