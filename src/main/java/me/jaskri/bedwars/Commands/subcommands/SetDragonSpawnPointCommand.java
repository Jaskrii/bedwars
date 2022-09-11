package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetDragonSpawnPointCommand implements SubCommand{

    public SetDragonSpawnPointCommand() {
    }

    public String getName() {
        return "setDragonSpawn";
    }

    public String getDescription() {
        return "Sets dragon spawn point!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/Bw setDragonSpawn <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length >= 2) {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    arena.setDragonSpawnPoint(player.getLocation());
                    player.sendMessage(ChatUtils.success("Dragon spawn point has been set!"));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
