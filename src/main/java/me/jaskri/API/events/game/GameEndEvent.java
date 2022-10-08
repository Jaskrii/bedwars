package me.jaskri.API.events.game;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.event.HandlerList;

import java.util.List;

public class GameEndEvent extends GameEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private List<GamePlayer> winners;
    private List<GamePlayer> losers;

    public GameEndEvent(Game game, List<GamePlayer> winners, List<GamePlayer> losers) {
        super(game);
        this.winners = winners;
        this.losers = losers;
    }

    public List<GamePlayer> getWinners() {
        return this.winners;
    }

    public List<GamePlayer> getLosers() {
        return this.losers;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
