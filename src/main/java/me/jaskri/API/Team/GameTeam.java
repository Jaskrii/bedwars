package me.jaskri.API.Team;

import me.jaskri.API.Game.Game;
import org.bukkit.scoreboard.Team;

public interface GameTeam {

    Game getGame();

    Team getTeam();

    UpgradeManager getUpgradeManager();

    TrapManager getTrapManager();
}
