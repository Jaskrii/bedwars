package me.jaskri.Listener;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Team.Team;
import me.jaskri.API.User.User;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.events.Player.AsyncGamePlayerChatEvent;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GamePlayerChatListener implements Listener {

    private static final String SPECTATOR_PREFIX;

    public GamePlayerChatListener() {
    }

    @EventHandler
    public void onGamePlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null) {
            event.setCancelled(true);
            if (!game.hasStarted()) {
                this.onWaitingChat(game, player, event.getMessage());
            } else if (game.isSpectator(player)) {
                this.onSpectatorChat(game, player, event.getMessage());
            } else {
                this.onPlayerChat(game, game.getGamePlayer(player), event.getMessage());
            }
        }
    }

    private void onWaitingChat(Game game, Player player, String message) {
        StringBuilder builder = (new StringBuilder()).append(ChatColor.GRAY).append(player.getDisplayName()).append(": ").append(message);
        game.broadcastMessage(builder.toString());
    }

    private void onSpectatorChat(Game game, Player player, String message) {
        StringBuilder builder = (new StringBuilder()).append(SPECTATOR_PREFIX).append(player.getDisplayName()).append(ChatColor.GRAY).append(": ").append(message);
        game.broadcastMessage(builder.toString(), (p) -> {
            return game.isSpectator(p);
        });
    }

    private void onPlayerChat(Game game, GamePlayer gp, String message) {
        AsyncGamePlayerChatEvent event = new AsyncGamePlayerChatEvent(gp, message);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            String format = event.getFormat();
            if (format != null) {
                User user = Bedwars.getInstance().getUser(gp.getPlayer());
                BedwarsLevel level = user.getDisplayLevel();
                if (level == null) {
                    level = user.getLevel();
                }

                Prestige prestige = user.getDisplayPrestige();
                if (prestige == null) {
                    prestige = user.getPrestige();
                }

                Team team = gp.getTeam();
                String text = String.format(format, prestige.formatToChat(level), team.getPrefix(), gp.getPlayer().getDisplayName(), message);
                game.broadcastMessage(text, (p) -> {
                    if (game.isSpectator(p)) {
                        return false;
                    } else {
                        GamePlayer gp1 = game.getGamePlayer(p);
                        return gp1 != null && gp1.getTeam() == team;
                    }
                });
            }
        }
    }

    static {
        SPECTATOR_PREFIX = ChatColor.GRAY + "[Spectator]";
    }
}
