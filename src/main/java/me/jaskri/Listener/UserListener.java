package me.jaskri.Listener;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.ScoreBoard.ScoreBoard;
import me.jaskri.API.User.User;
import me.jaskri.Game.AbstractGame;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class UserListener implements Listener {

    private static final Set<UUID> PLAYERS = new HashSet();
    private static BukkitTask lobbyUpdate;
    private static BukkitTask titleUpdate;

    public UserListener() {
    }

    @EventHandler
    public void onUserJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
            Player player = event.getPlayer();
            User user = Bedwars.getInstance().getUser(player);
            user.setScoreboard(Bedwars.getInstance().getLobbyScoreboard());
            BedwarsLevel.setForPlayer(player, user.getLevel());
            addPlayerToUpdatingBoard(player);
        }, 1L);
    }

    @EventHandler
    public void onUserQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
            User user = Bedwars.getInstance().getUser(event.getPlayer());
            user.saveData();
            removePlayerFromUpdatingBoard(event.getPlayer());
        });
    }

    @EventHandler
    public void onUserChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!AbstractGame.inGame(player)) {
            User user = Bedwars.getInstance().getUser(player);
            BedwarsLevel level = user.getDisplayLevel();
            if (level == null) {
                level = user.getLevel();
            }

            Prestige prestige = user.getDisplayPrestige();
            if (prestige == null) {
                prestige = Prestige.DEFAULT;
            }

            StringBuilder message = (new StringBuilder()).append(prestige.formatToChat(level)).append(" §r§7").append(player.getDisplayName()).append("§r: ").append(event.getMessage());
            event.setFormat(message.toString());
        }
    }

    public static void addPlayerToUpdatingBoard(Player player) {
        PLAYERS.add(player.getUniqueId());
        User user = Bedwars.getInstance().getUser(player);
        user.updateScoreboard();
        if (lobbyUpdate == null) {
            lobbyUpdate = (new BukkitRunnable() {
                public void run() {
                    Iterator var1 = UserListener.PLAYERS.iterator();

                    while(var1.hasNext()) {
                        UUID uuid = (UUID)var1.next();
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null && !AbstractGame.inGame(player)) {
                            User user = Bedwars.getInstance().getUser(player);
                            user.updateScoreboard();
                        }
                    }

                }
            }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 100L);
        }

        if (titleUpdate == null) {
            LobbyScoreBoard board = Bedwars.getInstance().getLobbyScoreboard();
            if (board == null) {
                return;
            }

            final ScoreBoard.AnimatedTitle title = board.getTitle();
            if (title.getClass() > 0L) {
                titleUpdate = (new BukkitRunnable() {
                    public void run() {
                        String next = board.getDisplayTitle();
                        Iterator var2 = UserListener.PLAYERS.iterator();

                        while(var2.hasNext()) {
                            UUID uuid = (UUID)var2.next();
                            Player player = Bukkit.getPlayer(uuid);
                            if (player != null && !AbstractGame.inGame(player)) {
                                org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
                                if (board == null) {
                                    return;
                                }

                                Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
                                if (obj == null) {
                                    return;
                                }

                                obj.setDisplayName(next);
                            }
                        }

                    }
                }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, title.getClass());
            }
        }

    }

    public static void removePlayerFromUpdatingBoard(Player player) {
        if (player != null) {
            PLAYERS.remove(player.getUniqueId());
            if (PLAYERS.isEmpty()) {
                if (lobbyUpdate != null) {
                    lobbyUpdate.cancel();
                }

                if (titleUpdate != null) {
                    titleUpdate.cancel();
                }

            }
        }
    }
}
