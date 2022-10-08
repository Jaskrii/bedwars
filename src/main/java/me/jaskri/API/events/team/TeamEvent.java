package me.jaskri.API.events.team;

import org.bukkit.event.Event;

public class TeamEvent extends Event {

    protected Game game;
    protected Team team;
    protected Collection<GamePlayer> teamPlayers;

    public TeamEvent(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public final Game getGame() {
        return this.game;
    }

    public final Team getTeam() {
        return this.team;
    }
}
