package me.jaskri.Commands.subcommands;

import me.jaskri.API.Team.Team;
import me.jaskri.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class SetTeamChestCommand implements SubCommand{

    public SetTeamChestCommand() {
    }

    public String getName() {
        return "setTeamChest";
    }

    public String getDescription() {
        return "Sets team's chest!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTeamChest <Arena> <Team>";
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
                        Block block = player.getLocation().subtract(0.0, 1.0, 0.0).getBlock();
                        if (block.getType() != Material.CHEST) {
                            player.sendMessage(ChatUtils.info("You must be on top of a chest to use this command!"));
                        } else {
                            arena.setTeamChest(team, (Chest)block.getState());
                            player.sendMessage(ChatUtils.success(team.getColoredString() + " team §achest has been set!"));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
