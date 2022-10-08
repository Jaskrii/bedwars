package me.jaskri.Team;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Team.Team;
import me.jaskri.API.Upgrade.UpgradeManager;

public class BedwarsTeam implements GameTeam {

    private final UpgradeManager upgradeManager = new TeamUpgradeManager();
    private final TrapManager trapsManager = new TeamTrapManager();
    private final Game game;
    private final Team team;

    public BedwarsTeam(Game game, Team team) {
        Preconditions.checkNotNull(game, "Game cannot be null");
        Preconditions.checkNotNull(team, "Team cannot be null");
        this.game = game;
        this.team = team;
    }

    public Game getGame() {
        return this.game;
    }

    public Team getTeam() {
        return this.team;
    }

    public UpgradeManager getUpgradeManager() {
        return this.upgradeManager;
    }

    public TrapManager getTrapManager() {
        return this.trapsManager;
    }
}
