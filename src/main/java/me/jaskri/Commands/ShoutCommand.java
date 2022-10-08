package me.jaskri.Commands;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.events.Player.AsyncGamePlayerShoutEvent;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShoutCommand implements CommandExecutor {

    private static final Map<UUID, Integer> COUNTDOWN = new HashMap();

    public ShoutCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players who can execute this command!");
            return false;
        } else if (args.length == 0) {
            return true;
        } else {
            Player player = (Player)sender;
            Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
                Game game = AbstractGame.getPlayerGame(player);
                if (game != null) {
                    if (game.getMode().getTeamMax() == 1) {
                        return;
                    }

                    UUID id = player.getUniqueId();
                    Integer time = (Integer)COUNTDOWN.get(id);
                    if (time != null) {
                        player.sendMessage(ChatUtils.format("&cYou have to wait &e" + time + "&c to use this command again!"));
                        return;
                    }

                    GamePlayer gp = game.getGamePlayer(player);
                    if (game.isSpectator(player)) {
                        return;
                    }

                    AsyncGamePlayerShoutEvent bwEvent = new AsyncGamePlayerShoutEvent(gp, Arrays.toString(args));
                    Bukkit.getPluginManager().callEvent(bwEvent);
                    String result = String.format(bwEvent.getFormat(), gp.getTeam().getPrefix(), "&r " + player.getDisplayName(), ": &r" + bwEvent.getMessage());
                    game.broadcastMessage(ChatUtils.format("&6[SHOUT] " + result));
                    this.startCountdown(player);
                }

            });
            return true;
        }
    }

    private void startCountdown(final Player player) {
        COUNTDOWN.put(player.getUniqueId(), 60);
        (new BukkitRunnable() {
            public void run() {
                int time = (Integer)ShoutCommand.COUNTDOWN.get(player.getUniqueId());
                if (time == 0) {
                    ShoutCommand.COUNTDOWN.remove(player.getUniqueId());
                    this.cancel();
                } else {
                    ShoutCommand.COUNTDOWN.put(player.getUniqueId(), time - 1);
                }
            }
        }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 20L);
    }
}
