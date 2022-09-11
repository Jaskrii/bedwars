package me.jaskri.bedwars.API.PACKAGE.Game.player;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.player.Stats.GameStatisticManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface GamePlayer {

    Player getPlayer();

    Game getGame();

    Team getTeam();

    ArmorType getArmorType();

    void setArmorType(ArmorType var1);

    GameInventory getInventory();

    void setInventory(GameInventory var1);

    GameStatisticManager getStatisticManager();

    void setStatistics(GameStatisticManager var1);
}
