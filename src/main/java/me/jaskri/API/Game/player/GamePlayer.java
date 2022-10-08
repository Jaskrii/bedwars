package me.jaskri.API.Game.player;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
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
