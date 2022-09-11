package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetSpectatorSpawnCommand implements SubCommand{

    public SetSpectatorSpawnCommand() {
    }

    public String getName() {
        return "setSpectatorSpawn";
    }

    public String getDescription() {
        return "Sets spectator's spawn point!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setSpectatorSpawn <Arena>";
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
                    arena.setSpectatorSpawnPoint(player.getLocation());
                    player.sendMessage(ChatUtils.success(this.getSuccessMessage(args[1])));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }

    private String getSuccessMessage(String name) {
        StringBuilder builder = (new StringBuilder("Spectator spawn point has been set for ")).append(ChatColor.YELLOW).append(name).append(ChatColor.GREEN).append("!");
        return builder.toString();
    }
}
