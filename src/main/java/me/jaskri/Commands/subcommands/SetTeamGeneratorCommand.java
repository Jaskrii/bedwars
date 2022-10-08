package me.jaskri.Commands.subcommands;

import me.jaskri.API.Team.Team;
import me.jaskri.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetTeamGeneratorCommand implements SubCommand{

    public SetTeamGeneratorCommand() {
    }

    public String getName() {
        return "setTeamGen";
    }

    public String getDescription() {
        return "Sets team's generator location!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTeamGen <Arena> <Team>";
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
                        arena.setTeamGenLocation(team, player.getLocation());
                        player.sendMessage(ChatUtils.success(team.getColoredString() + " team §agenerator has been set!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
