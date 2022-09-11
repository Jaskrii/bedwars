package me.jaskri.bedwars.Game;

import me.jaskri.bedwars.API.PACKAGE.ScoreBoard.ScoreBoard;
import me.jaskri.bedwars.Bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameCountdown {

    private static final WaitingScoreboard BOARD = Bedwars.getInstance().getWaitingScoreboard();
    private static final String NOT_ENOUGH_PLAYERS = "§cWe don't have enough players! Start cancelled.";
    private Set<Player> players = new HashSet();
    private Game game;
    private BukkitTask task;
    private BukkitTask title;
    private boolean isLocked = false;
    private int minimum;

    public GameCountdown(Game game) {
        this.game = game;
        this.minimum = Bedwars.getInstance().getSettings().getMinimumPlayers(game.getMode());
    }

    public void start() {
        if (!this.isLocked) {
            final int countdown = Bedwars.getInstance().getSettings().getGameCountdown();
            this.game.setGameState(GameState.COUNTDOWN);
            if (BOARD == null) {
                this.task = Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), () -> {
                    this.game.startGame();
                }, (long)(20 * countdown));
            } else {
                this.task = (new BukkitRunnable() {
                    private int timeLeft = countdown;

                    public void run() {
                        if (this.timeLeft == 0) {
                            GameCountdown.this.game.startGame();
                            GameCountdown.this.stop();
                        } else {
                            GameCountdown.BOARD.setCountdownText(GameCountdown.this.game, GameCountdown.this.formatStartingCountdown(this.timeLeft));
                            GameCountdown.BOARD.update(GameCountdown.this.game, GameCountdown.this.players);
                            switch (this.timeLeft) {
                                case 1:
                                case 2:
                                case 3:
                                    GameCountdown.this.sendMessageAndTitle(GameCountdown.this.formatCountdown(this.timeLeft), ChatColor.RED + "" + this.timeLeft, "");
                                    break;
                                case 4:
                                case 5:
                                    GameCountdown.this.sendMessageAndTitle(GameCountdown.this.formatCountdown(this.timeLeft), ChatColor.YELLOW + "" + this.timeLeft, "");
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                case 16:
                                case 17:
                                case 18:
                                case 19:
                                default:
                                    break;
                                case 10:
                                    GameCountdown.this.sendMessageAndTitle(GameCountdown.this.formatCountdown(this.timeLeft), ChatColor.GOLD + "10", "");
                                    break;
                                case 20:
                                    GameCountdown.this.sendMessageAndTitle(GameCountdown.this.formatCountdown(this.timeLeft), ChatColor.GREEN + "20", "");
                            }

                            --this.timeLeft;
                        }
                    }
                }).runTaskTimer(Bedwars.getInstance(), 0L, 20L);
                final ScoreBoard.AnimatedTitle title = BOARD.getTitle();
                if (title.getUpdateTicks() > 0L) {
                    this.title = (new BukkitRunnable() {
                        public void run() {
                            String next = title.next();
                            Iterator var2 = GameCountdown.this.players.iterator();

                            while(var2.hashNext()) {
                                Player player = (Player)var2.next();
                                org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
                                Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
                                if (obj != null) {
                                    obj.setDisplayName(next);
                                }
                            }

                        }
                    }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, title.getUpdateTicks());
                }

                this.isLocked = true;
            }
        }
    }

    private void sendMessageAndTitle(String message, String title, String subTitle) {
        Iterator var4 = this.players.iterator();

        while(var4.hasNext()) {
            Player player = (Player)var4.next();
            Titles.sendTitle(player, 10, 20, 10, title, subTitle);
            player.sendMessage(message);
        }

    }

    private String formatStartingCountdown(int time) {
        return "Starting in " + ChatColor.GREEN + time + "s";
    }

    private String formatCountdown(int time) {
        StringBuilder builder = (new StringBuilder()).append(ChatColor.YELLOW).append("The game starts in ").append(ChatColor.RED).append(time).append(ChatColor.YELLOW).append(" seconds!");
        return builder.toString();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        BOARD.setCountdownText(this.game, "Waiting...");
        BOARD.update(this.game, this.players);
        if (this.players.size() >= this.minimum) {
            this.start();
        }

    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        if (this.players.size() < this.minimum) {
            Iterator var2 = this.players.iterator();

            while(var2.hasNext()) {
                Player p = (Player)var2.next();
                Titles.sendTitle(p, 10, 20, 10, "", "§cWe don't have enough players! Start cancelled.");
            }

            this.cancel();
        }
    }

    public void cancel() {
        if (this.isLocked) {
            this.game.setGameState(GameState.WAITING);
            if (BOARD != null) {
                BOARD.setCountdownText(this.game, "Waiting...");
                BOARD.update(this.game, this.players);
            }

            if (this.title != null) {
                this.title.cancel();
            }

            this.task.cancel();
            this.isLocked = false;
        }
    }

    public void stop() {
        if (this.isLocked) {
            this.players.clear();
            this.cancel();
        }
    }
}
