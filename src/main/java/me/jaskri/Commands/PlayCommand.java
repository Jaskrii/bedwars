package me.jaskri.Commands;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameMode;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class PlayCommand implements CommandExecutor {

    public PlayCommand() {
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (sender instanceof Player && args.length != 0) {
            (new BukkitRunnable() {
                private Game game;

                public void run() {
                    Player player = (Player)sender;
                    if (!player.hasPermission("bw.admin") && !player.hasPermission("bw.play")) {
                        player.sendMessage(ChatUtils.error("You don't have the permission to execute this command!"));
                    } else {
                        GameMode mode = null;
                        if (args[0].equalsIgnoreCase("bedwars_eight_one")) {
                            mode = GameMode.SOLO;
                        } else if (args[0].equalsIgnoreCase("bedwars_eight_two")) {
                            mode = GameMode.DUO;
                        } else if (args[0].equalsIgnoreCase("bedwars_four_three")) {
                            mode = GameMode.TRIO;
                        } else if (args[0].equalsIgnoreCase("bedwars_four_four")) {
                            mode = GameMode.QUATUOR;
                        }

                        if (mode != null) {
                            Iterator var3 = AbstractGame.getGames().values().iterator();

                            while(var3.hasNext()) {
                                Game existing = (Game)var3.next();
                                if (!existing.hasStarted() && existing.getMode() == mode && existing.canAddPlayer(player)) {
                                    this.game = existing;
                                }
                            }

                            if (this.game == null) {
                                this.game = BedwarsGame.randomGame(mode);
                            }

                            if (this.game == null) {
                                player.sendMessage(ChatColor.RED + "Could not find a game! Try again later.");
                            } else {
                                Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                                    this.game.addPlayer(player);
                                });
                            }
                        }
                    }
                }
            }).runTaskAsynchronously(Bedwars.getInstance());
            return true;
        } else {
            return true;
        }
    }
}
