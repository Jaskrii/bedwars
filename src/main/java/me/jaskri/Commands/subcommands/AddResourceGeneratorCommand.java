package me.jaskri.Commands.subcommands;

import me.jaskri.API.Generator.Resource;
import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AddResourceGeneratorCommand implements SubCommand {

    public AddResourceGeneratorCommand() {
    }

    public String getName() {
        return "addResourceGenerator";
    }

    public String getDescription() {
        return "Adds resource generator!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/Bw addResourceGen <Arena> <Resource>";
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
                    Resource resource = Resource.getByName(args[2]);
                    if (resource == null) {
                        player.sendMessage(ChatUtils.error("Invalid Resource!"));
                        player.sendMessage(ChatUtils.info("/Bw resources"));
                    } else {
                        arena.addResourceGenerator(resource, player.getLocation());
                        player.sendMessage(ChatUtils.success(resource.getColoredName() + " ┬žagenerator has been added!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
