package me.jaskri.bedwars.API.PACKAGE.Team;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import org.bukkit.scoreboard.Team;

public interface GameTeam {

    Game getGame();

    Team getTeam();

    UpgradeManager getUpgradeManager();

    TrapManager getTrapManager();
}
