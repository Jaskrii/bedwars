package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetMapNameCommand implements SubCommand {

    public SetMapNameCommand() {
    }

    public String getName() {
        return "setMapName";
    }

    public String getDescription() {
        return "Sets map name of the arena!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setMapName <Arena> <Name>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    arena.setMapName(args[2]);
                    player.sendMessage(ChatUtils.success("Arena map name has been set to §e" + args[2] + "§a!"));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
