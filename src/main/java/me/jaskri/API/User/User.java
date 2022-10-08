package me.jaskri.API.User;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.Shop.QuickBuy;
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
