package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.Generator.GeneratorSpeed;
import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetGenSpeedCommand implements SubCommand{

    public SetGenSpeedCommand() {
    }

    public String getName() {
        return "setGenSpeed";
    }

    public String getDescription() {
        return "Sets arena teams generator speed!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setGenSpeed <Arena> <Name>";
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
                    GeneratorSpeed speed = GeneratorSpeed.getByName(args[2]);
                    if (speed == null) {
                        player.sendMessage(ChatUtils.error("Invalid generator speed!"));
                        player.sendMessage(ChatUtils.info("/bw speeds"));
                    } else {
                        arena.setGeneratorSpeed(speed);
                        player.sendMessage(ChatUtils.success("Arena's generator speed has been set to §e" + speed.getName() + "§a!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
