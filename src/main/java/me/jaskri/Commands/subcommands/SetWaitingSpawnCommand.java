package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetWaitingSpawnCommand implements SubCommand {

    public SetWaitingSpawnCommand() {
    }

    public String getName() {
        return "setWaitingSpawn";
    }

    public String getDescription() {
        return "Sets arena's waiting-room spawn point!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setWaitingRoomSpawn <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    arena.setWaitingRoomLocation(player.getLocation());
                    player.sendMessage(ChatUtils.success("Waiting-room spawn has been set!"));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
