package me.jaskri.bedwars.Game;

import com.google.common.base.Preconditions;
import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GameManager;
import me.jaskri.bedwars.API.PACKAGE.Game.GamePhase;
import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;
import me.jaskri.bedwars.API.PACKAGE.ScoreBoard.ScoreBoard;
import me.jaskri.bedwars.API.PACKAGE.Team.GameTeam;
import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.API.PACKAGE.Trap.Trap;
import me.jaskri.bedwars.API.PACKAGE.User.Statistics;
import me.jaskri.bedwars.API.PACKAGE.User.User;
import me.jaskri.bedwars.API.PACKAGE.User.UserStatistics;
import me.jaskri.bedwars.API.PACKAGE.arena.Arena;
import me.jaskri.bedwars.Bedwars.Bedwars;
import me.jaskri.bedwars.Bedwars.settings.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class BedwarsGameManager implements GameManager {

    private Set<UUID> TRAP_SAFE = new HashSet();
    private List<GamePhase> phases;
    private final Game game;
    private BukkitTask task;
    private BukkitTask titleTask;
    private GameScoreboard board;
    private GamePhase current;
    private long nextPhaseTime;
    private long currentTime;
    private long gameLength;
    private boolean isCancelled = true;

    public BedwarsGameManager(Game game) {
        Preconditions.checkNotNull(game, "Game cannot be null!");
        this.game = game;
        this.phases = Bedwars.getInstance().getGameSettings().getDefaultGamePhases();

        GamePhase phase;
        for(Iterator var2 = this.phases.iterator(); var2.hasNext(); this.gameLength += (long)phase.getDuration()) {
            phase = (GamePhase)var2.next();
        }

        this.board = Bedwars.getInstance().getGameScoreboard(game.getMode());
    }

    public Game getGame() {
        return this.game;
    }

    public boolean start() {
        if (!this.isCancelled) {
            return false;
        } else {
            final Iterator iterator = this.game.getPlayers().iterator();

            while(iterator.hasNext()) {
                Player player = (Player)iterator.next();
                User user = Bedwars.getInstance().getUser(player);
                UserStatistics stats = user.getStatistics(this.game.getMode());
                if (stats != null) {
                    stats.incrementStatistic(Statistics.GAME_PLAYED, 1);
                }
            }

            iterator = this.phases.iterator();
            this.task = (new BukkitRunnable() {
                public void run() {
                    if (BedwarsGameManager.this.nextPhaseTime == 0L) {
                        if (BedwarsGameManager.this.current != null) {
                            BedwarsGameManager.this.current.apply(BedwarsGameManager.this.game);
                        }

                        if (!iterator.hasNext()) {
                            BedwarsGameManager.this.game.stopGame();
                            return;
                        }

                        BedwarsGameManager.this.current = (GamePhase)iterator.next();
                        BedwarsGameManager.this.nextPhaseTime = (long)BedwarsGameManager.this.current.getDuration();
                    }

                    BedwarsGameManager.this.currentTime++;
                    BedwarsGameManager.this.nextPhaseTime--;
                    if (BedwarsGameManager.this.board != null) {
                        BedwarsGameManager.this.board.setPhaseText(BedwarsGameManager.this.current.getName() + " in " + ChatColor.GREEN + ScoreboardUtils.formatCountdown(BedwarsGameManager.this.nextPhaseTime));
                    }

                    GameSettings settings = Bedwars.getInstance().getGameSettings();
                    Collection<GamePlayer> game_players = BedwarsGameManager.this.game.getGamePlayers();
                    Iterator var3 = game_players.iterator();

                    while(var3.hasNext()) {
                        GamePlayer gpx = (GamePlayer)var3.next();
                        Player playerx = gpx.getPlayer();
                        if (BedwarsGameManager.this.board != null) {
                            Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                                BedwarsGameManager.this.board.update(gpx);
                            });
                        }

                        if (!BedwarsGameManager.this.game.isSpectator(playerx)) {
                            int coins;
                            if (BedwarsGameManager.this.currentTime % settings.timePlayedForExpReward() == 0L) {
                                coins = settings.getExpReward();
                                gpx.getStatisticManager().getExpReward().increment(coins);
                                playerx.sendMessage("§b+" + coins + " Bed Wars Experience (Time Played)");
                            }

                            if (BedwarsGameManager.this.currentTime % settings.timePlayedForCoinsReward() == 0L) {
                                coins = settings.getCoinsReward();
                                gpx.getStatisticManager().getCoinsReward().increment(coins);
                                playerx.sendMessage("§6+" + coins + " coins! (Time Played)");
                            }
                        }
                    }

                    Arena arena = BedwarsGameManager.this.game.getArena();
                    Collection<GameTeam> teams = BedwarsGameManager.this.game.getTeams();
                    Iterator var17 = teams.iterator();

                    label102:
                    while(true) {
                        Location spawn;
                        TrapManager manager;
                        List traps;
                        GameTeam team;
                        do {
                            do {
                                if (!var17.hasNext()) {
                                    return;
                                }

                                team = (GameTeam)var17.next();
                                spawn = arena.getTeamSpawnPoint(team.getTeam());
                            } while(spawn == null);

                            manager = team.getTrapManager();
                            traps = manager.getTraps();
                        } while(traps.isEmpty());

                        Iterator var10 = spawn.getWorld().getEntitiesByClass(Player.class).iterator();

                        while(true) {
                            while(true) {
                                GamePlayer gp;
                                do {
                                    do {
                                        Player player;
                                        do {
                                            do {
                                                do {
                                                    if (!var10.hasNext()) {
                                                        continue label102;
                                                    }

                                                    player = (Player)var10.next();
                                                } while(player.getLocation().distanceSquared(spawn) > 400.0);
                                            } while(BedwarsGameManager.this.TRAP_SAFE.contains(player.getUniqueId()));
                                        } while(BedwarsGameManager.this.game.isSpectator(player));

                                        gp = BedwarsGameManager.this.game.getGamePlayer(player);
                                    } while(gp == null);
                                } while(gp.getTeam() == team.getTeam());

                                Iterator var13 = traps.iterator();

                                while(var13.hasNext()) {
                                     Trap = (Trap)var13.next();
                                    if (BedwarsGameManager.this.onTrigger(Trap, team.getTeam(), gp)) {
                                        manager.removeTrap(Trap);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }).runTaskTimer(Bedwars.getInstance(), 0L, 20L);
            if (this.board != null) {
                final ScoreBoard.AnimatedTitle title = this.board.getTitle();
                if (title.getUpdateTicks() > 0L) {
                    this.titleTask = (new BukkitRunnable() {
                        public void run() {
                            Iterator var1 = BedwarsGameManager.this.game.getPlayers().iterator();

                            while(var1.hasNext()) {
                                Player player = (Player)var1.next();
                                org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
                                if (board != null) {
                                    Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
                                    if (obj != null) {
                                        obj.setDisplayName(title.next());
                                    }
                                }
                            }

                        }
                    }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, title.getUpdateTicks());
                }
            }

            this.isCancelled = false;
            return true;
        }
    }

    private boolean onTrigger(Trap trap, Team team, GamePlayer gp) {
        if (!trap.onTrigger(gp, team)) {
            return false;
        } else {
            this.TRAP_SAFE.add(gp.getPlayer().getUniqueId());
            Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
                this.TRAP_SAFE.remove(gp.getPlayer().getUniqueId());
            }, (long)(20 * trap.getDuration()));
            return true;
        }
    }

    public boolean stop() {
        if (!this.isCancelled && this.game.hasStarted()) {
            if (this.task != null) {
                this.task.cancel();
            }

            if (this.titleTask != null) {
                this.titleTask.cancel();
            }

            this.isCancelled = true;
            return true;
        } else {
            return false;
        }
    }

    public GamePhase getCurrentPhase() {
        return this.current;
    }

    public long timeLeftForNextPhase() {
        return this.nextPhaseTime;
    }

    public long gameLength() {
        return this.gameLength;
    }

    public long currentTime() {
        return this.currentTime;
    }

    public long timeLeft() {
        return this.gameLength - this.currentTime;
    }
}
