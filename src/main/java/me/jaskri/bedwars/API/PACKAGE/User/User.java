package me.jaskri.bedwars.API.PACKAGE.User;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GameMode;
import me.jaskri.bedwars.API.PACKAGE.Level.BedwarsLevel;
import me.jaskri.bedwars.API.PACKAGE.Prestige.Prestige;
import me.jaskri.bedwars.API.PACKAGE.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.bedwars.API.PACKAGE.Shop.QuickBuy;
import org.bukkit.entity.Player;

public interface User {

    Player getPlayer();

    Game getGame();

    BedwarsLevel getLevel();

    void setLevel(BedwarsLevel var1);

    BedwarsLevel getDisplayLevel();

    void setDisplayLevel(BedwarsLevel var1);

    Prestige getPrestige();

    void setPrestige(Prestige var1);

    Prestige getDisplayPrestige();

    void setDisplayPrestige(Prestige var1);

    UserStatistics getStatistics(GameMode var1);

    UserStatistics getOverallStatistics();

    int getCoinsBalance();

    void setCoinsBalance(int var1);

    LobbyScoreBoard getScoreboard();

    QuickBuy getQuickBuy(GameMode var1);

    void setScoreboard(LobbyScoreBoard var1);

    void updateScoreboard();

    void loadData();

    void saveData();
}
