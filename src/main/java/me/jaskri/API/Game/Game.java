package me.jaskri.API.Game;

import me.jaskri.API.arena.Arena;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Predicate;

public interface Game {

    Arena getArena();

    boolean startGame();

    boolean stopGame();

    boolean addPlayer(Player var1);

    boolean canAddPlayer(Player var1);

    boolean removePlayer(Player var1);

    boolean addGroup(Group var1);

    boolean canAddGroup(Group var1);

    boolean removeGroup(Group var1);

    boolean killPlayer(Player var1, String var2, int var3);

    boolean killPlayer(Player var1, String var2);

    boolean killPlayer(Player var1);

    boolean eliminatePlayer(Player var1);

    boolean isEliminated(Player var1);

    boolean eliminateTeam(Team var1);

    boolean isEliminated(Team var1);

    boolean breakTeamBed(Team var1, Player var2);

    boolean breakTeamBed(Team var1);

    boolean hasBed(Team var1);

    boolean disconnect(Player var1);

    boolean isDisconnected(Player var1);

    boolean reconnect(Player var1);

    boolean broadcastMessage(String var1);

    boolean broadcastMessage(String var1, Predicate<Player> var2);

    GameState getGameState();

    void setGameState(GameState var1);

    GameMode getMode();

    GameManager getManager();

    GamePlayer getGamePlayer(Player var1);

    boolean isInvincible(Player var1);

    void setInvincible(Player var1, boolean var2);

    boolean isSpectator(Player var1);

    TeamGenerator getTeamGenerator(Team var1);

    Collection<TieredGenerator> getMapResourceGenerator(Resource var1);

    Collection<Player> getPlayers();

    Collection<GamePlayer> getGamePlayers();

    Collection<GamePlayer> getTeamPlayers(Team var1);

    Collection<GameTeam> getTeams();

    GameTeam getGameTeam(Team var1);

    boolean hasStarted();

    boolean isFull();

    boolean contains(Player var1);

    int size();
}
