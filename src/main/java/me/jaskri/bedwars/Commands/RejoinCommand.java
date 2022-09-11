package me.jaskri.bedwars.Commands;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RejoinCommand implements CommandExecutor {

    public RejoinCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players who can execute this command!");
            return true;
        } else {
            Player player = (Player)sender;
            if (!player.hasPermission("bw.admin") && !player.hasPermission("bw.rejoin")) {
                player.sendMessage("§cYou don't have the permission to execute this command!");
                return true;
            } else {
                Game game = AbstractGame.getDisconnectedPlayerGame(player);
                if (game == null) {
                    return true;
                } else {
                    game.reconnect(player);
                    return true;
                }
            }
        }
    }
}
