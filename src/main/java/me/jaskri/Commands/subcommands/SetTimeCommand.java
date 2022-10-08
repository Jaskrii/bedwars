package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetTimeCommand implements SubCommand{

    public SetTimeCommand() {
    }

    public String getName() {
        return "setTime";
    }

    public String getDescription() {
        return "Set time of the arena!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTime <Arena> <Time>";
    }

    public void perform(Player player, String[] args) {
        if (args.length > 3) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    int time = NumberConversions.toInt(args[2]);
                    if (time < 0) {
                        player.sendMessage(ChatUtils.error("Please enter a valid number!"));
                    } else {
                        arena.setArenaTime(time);
                        player.sendMessage(ChatUtils.success("Arena time has been set to §e" + time + "§a!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
