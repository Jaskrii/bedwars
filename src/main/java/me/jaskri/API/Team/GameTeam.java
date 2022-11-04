package me.jaskri.API.Team;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Trap.TrapManger;
import me.jaskri.API.Upgrade.UpgradeManager;
import org.bukkit.scoreboard.Team;

public interface GameTeam {

    Game getGame();

    Team getTeam();

    UpgradeManager getUpgradeManager();

    TrapManger getTrapManager();
}
