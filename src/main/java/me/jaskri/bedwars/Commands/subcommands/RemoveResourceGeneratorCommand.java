package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveResourceGeneratorCommand implements SubCommand{

    public RemoveResourceGeneratorCommand() {
    }

    public String getName() {
        return "removeResourceGen";
    }

    public String getDescription() {
        return "Removes resource generator at the given index!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/Bw setResourceGen <Arena> <Resource> <index>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 4) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    Resource resource = Resource.getByName(args[2]);
                    if (resource == null) {
                        player.sendMessage(ChatUtils.error("Invalid Resource!"));
                        player.sendMessage(ChatUtils.info("/Bw resources"));
                    } else {
                        int index = NumberConversions.toInt(args[3]);
                        if (!arena.removeResourceGenerator(resource, index)) {
                            player.sendMessage(ChatUtils.error("Could not remove resource generator at index §e" + index + "§c!"));
                        } else {
                            player.sendMessage(ChatUtils.success(resource.getColoredName() + " §agenerator at index §e" + index + " §ahas been removed!"));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
