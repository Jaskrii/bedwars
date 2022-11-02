package me.jaskri.API.bedwars;

import me.jaskri.API.Entity.GameEntityManager;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GamePhase;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.Shop.Shop;
import me.jaskri.API.Upgrade.Shop.UpgradeShop;
import me.jaskri.API.User.User;
import me.jaskri.API.arena.Arena;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface BedwarsPlugin extends Plugin {
    String getPluginPrefix();

    Prestige getDefaultPrestige();

    LobbyScoreBoard getLobbyScoreboard();

    GamePhase getGamePhase(String var1);

    void registerGamePhase(GamePhase var1);

    NPCmanager getNPCManager();

    GameEntityManager getEntityManager();

    Shop getTeamShop(GameMode var1);

    UpgradeShop getTeamUpgradeShop(GameMode var1);

    UpgradesManager getUpgradesManager();

    User loadUser(Player var1);

    User getUser(Player var1);

    Arena getArena(String var1);

    Game addPlayerToRandomGame(Player var1, GameMode var2);

    Game addPlayerToRandomGame(Player var1);

    Game getRandomGame(GameMode var1);

    Game getRandomGame();

    Game getPlayerGame(Player var1);

    boolean inGame(Player var1);

    boolean inRunningGame(Player var1);

    boolean sendPlayerToLobby(Game var1, Player var2, String var3);
}
