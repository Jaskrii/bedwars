package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetTeamShopCommand implements SubCommand{

    public SetTeamShopCommand() {
    }

    public String getName() {
        return "setTeamShop";
    }

    public String getDescription() {
        return "Sets team's shop location!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTeamShop <Arena> <Team>";
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
                        arena.setTeamShop(team, player.getLocation());
                        player.sendMessage(ChatUtils.success(team.getColoredString() + " team §ashop has been set!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
