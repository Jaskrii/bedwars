package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.arena.Region;
import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class SetRegionCommand implements SubCommand{

    public SetRegionCommand() {
    }

    public String getName() {
        return "setRegion";
    }

    public String getDescription() {
        return "Sets arena's region!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setRegion <Arena> <X-radius> <Z-radius> <Y-max> <Y-min>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 6) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    int x_radius = NumberConversions.toInt(args[2]);
                    if (this.checkMinValue(x_radius, 20, "X radius must be atleast 20", player)) {
                        int z_radius = NumberConversions.toInt(args[3]);
                        if (this.checkMinValue(z_radius, 20, "Z radius must be atleast 20", player)) {
                            int y_max = NumberConversions.toInt(args[4]);
                            int y_min = NumberConversions.toInt(args[5]);
                            if (y_max < y_min) {
                                player.sendMessage(ChatUtils.error("Y-max must be bigger than Y-min"));
                            } else {
                                Location loc = player.getLocation();
                                loc.setY(0.0);
                                Location pos1 = this.expand(loc, (double)x_radius, (double)y_max, (double)z_radius);
                                Location pos2 = this.expand(loc, (double)(-x_radius), (double)y_min, (double)(-z_radius));
                                arena.setArenaRegion(new Region(pos1, pos2));
                                player.sendMessage(ChatUtils.success("Arena region has been set!"));
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
    }

    private Location expand(Location loc, double x, double y, double z) {
        return loc.clone().add(loc.getX() >= 0.0 ? x : -x, loc.getY() >= 0.0 ? y : -y, loc.getZ() >= 0.0 ? z : -z);
    }

    private boolean checkMinValue(int value, int min, String message, Player player) {
        if (value >= min) {
            return true;
        } else {
            player.sendMessage(ChatUtils.error(message));
            return false;
        }
    }
}
