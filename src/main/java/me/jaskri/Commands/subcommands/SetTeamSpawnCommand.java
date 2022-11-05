package me.jaskri.Commands.subcommands;

import me.jaskri.API.Team.Team;
import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetTeamSpawnCommand implements SubCommand {

    public SetTeamSpawnCommand() {
    }

    public String getName() {
        return "setTeamSpawn";
    }

    public String getDescription() {
        return "Sets team's spawn location!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTeamSpawn <Arena> <Team>";
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
                    Team team = Team.getByName(args[2]);
                    if (team == null) {
                        player.sendMessage(ChatUtils.error("Invalid Team!"));
                        player.sendMessage(ChatUtils.info("/bw teams"));
                    } else {
                        arena.setTeamSpawnPoint(team, player.getLocation());
                        player.sendMessage(ChatUtils.success(team.getColoredString() + " team §aspawn point has been set!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
