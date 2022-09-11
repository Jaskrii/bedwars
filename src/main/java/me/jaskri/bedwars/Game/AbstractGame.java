package me.jaskri.bedwars.Game;

import com.google.common.base.Preconditions;
import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GameMode;
import me.jaskri.bedwars.API.PACKAGE.Game.GameState;
import me.jaskri.bedwars.API.PACKAGE.arena.Arena;
import me.jaskri.bedwars.API.PACKAGE.events.game.GameStateChangeEvent;
import me.jaskri.bedwars.Arena.BedwarsArena;
import me.jaskri.bedwars.exception.ArenaNotFoundException;
import me.jaskri.bedwars.exception.ArenaNotReadyException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class AbstractGame implements Game {

    protected static final Map<UUID, Game> PLAYERS_GAME = new HashMap();
    protected static final Map<UUID, Game> DISCONNECTED = new HashMap();
    private static final Map<Arena, Game> GAMES = new HashMap();
    private static final Random RANDOM = new Random();
    protected Arena arena;
    protected GameMode mode;
    protected GameState state;
    protected boolean hasStarted;

    public AbstractGame(Arena arena) {
        Preconditions.checkNotNull(arena, "Arena cannot be null!");
        if (!arena.exists()) {
            throw new ArenaNotFoundException("Arena doesn't exist!");
        } else if (!arena.isReady()) {
            throw new ArenaNotReadyException("Arena is not ready and cannot be used!");
        } else if (isArenaOccuped(arena)) {
            throw new IllegalStateException("Arena can only be used once per game!");
        } else {
            this.arena = arena;
            this.mode = arena.getMode();
            this.state = GameState.WAITING;
            GAMES.put(arena, this);
        }
    }

    public Arena getArena() {
        return this.arena;
    }

    public boolean stopGame() {
        return GAMES.remove(this.arena) != null;
    }

    public GameState getGameState() {
        return this.state;
    }

    public void setGameState(GameState state) {
        if (!this.hasStarted && state != null) {
            switch (state) {
                case COUNTDOWN:
                case WAITING:
                    return;
                case RUNNING:
                    this.startGame();
                    break;
                case ENDED:
                case RESETTING:
                    this.stopGame();
            }

            this.setState(state);
        }
    }

    protected void setState(GameState state) {
        GameStateChangeEvent bwEvent = new GameStateChangeEvent(this, this.state, state);
        Bukkit.getPluginManager().callEvent(bwEvent);
        this.state = state;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public static Map<Arena, Game> getGames() {
        return new HashMap(GAMES);
    }

    public static Game getPlayerGame(Player player) {
        return player != null ? (Game)PLAYERS_GAME.get(player.getUniqueId()) : null;
    }

    public static Game getDisconnectedPlayerGame(Player player) {
        return player != null ? (Game)DISCONNECTED.get(player.getUniqueId()) : null;
    }

    public static boolean inGame(Player player) {
        return getPlayerGame(player) != null;
    }

    public static boolean inRunningGame(Player player) {
        Game game = getPlayerGame(player);
        return game != null && game.hasStarted();
    }

    public static boolean isArenaOccuped(Arena arena) {
        return arena != null ? GAMES.containsKey(arena) : false;
    }

    public static BedwarsArena randomArena(GameMode mode) {
        List<BedwarsArena> arenas = new ArrayList();
        Iterator var2 = BedwarsArena.getArenas().iterator();

        while(var2.hasNext()) {
            BedwarsArena arena = (BedwarsArena)var2.next();
            if (!isArenaOccuped(arena)) {
                GameMode arenaMode = arena.getMode();
                if (arenaMode != null && arenaMode.equals(mode) && arena.isReady()) {
                    arenas.add(arena);
                }
            }
        }

        return !arenas.isEmpty() ? (BedwarsArena)arenas.get(RANDOM.nextInt(arenas.size())) : null;
    }
}
