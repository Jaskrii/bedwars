package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.API.PACKAGE.arena.BedwarsBed;
import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SetTeamBedCommand implements SubCommand{

    public SetTeamBedCommand() {
    }

    public String getName() {
        return "setTeamBed";
    }

    public String getDescription() {
        return "Sets team's bed location!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setTeamBed <Arena> <Team>";
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
                    Block part1 = player.getLocation().getBlock();
                    if (!BedUtils.isBed(part1)) {
                        player.sendMessage(ChatUtils.info("You must be on top of a bed!"));
                    } else {
                        Team team = Team.getByName(args[2]);
                        if (team == null) {
                            player.sendMessage(ChatUtils.error("Invalid Team!"));
                            player.sendMessage(ChatUtils.info("/bw teams"));
                        } else {
                            Block part2 = BedUtils.getOtherBedPart(part1);
                            if (part2 == null) {
                                if (BedUtils.isBedHead(part1)) {
                                    player.sendMessage(ChatUtils.error("Could not find bed foot!"));
                                } else {
                                    player.sendMessage(ChatUtils.error("Cound not find bed head!"));
                                }

                            } else {
                                arena.setTeamBed(new BedwarsBed(team, part1, part2));
                                player.sendMessage(ChatUtils.success(team.getColoredString() + " team §abed has been set!"));
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }
}
