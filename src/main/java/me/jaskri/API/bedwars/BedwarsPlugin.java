package me.jaskri.API.bedwars;

import me.jaskri.API.arena.Arena;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface BedwarsPlugin extends Plugin {
    String getPluginPrefix();

    Prestige getDefaultPrestige();

    LobbyScoreboard getLobbyScoreboard();

    GamePhas getGamePhase(String var1);

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
