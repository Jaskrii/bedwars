package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.arena.Region;
import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class SetWaitingRoomRegionCommand implements SubCommand{

    public SetWaitingRoomRegionCommand() {
    }

    public String getName() {
        return "setWaitingRegion";
    }

    public String getDescription() {
        return "Sets arena's waiting-room region!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setWaitingRegion <Arena> <X-radius> <Y-radius> <Z-radius>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 5) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error(ChatColor.YELLOW + args[1] + ChatColor.RED + " is already in use and cannot be edited!"));
                } else {
                    int x_radius = NumberConversions.toInt(args[2]);
                    if (this.checkMinValue(x_radius, 0, "X radius must be greater than 0!", player)) {
                        int y_radius = NumberConversions.toInt(args[3]);
                        if (this.checkMinValue(y_radius, 0, "Y radius must be atleast 0!", player)) {
                            int z_radius = NumberConversions.toInt(args[4]);
                            if (this.checkMinValue(z_radius, 0, "Z radius must be atleast 0!", player)) {
                                Location loc = player.getLocation();
                                loc.setY(0.0);
                                Location pos1 = loc.clone().add((double)x_radius, (double)y_radius, (double)z_radius);
                                Location pos2 = loc.clone().subtract((double)x_radius, (double)y_radius, (double)z_radius);
                                arena.setWaitingRoomRegion(new Region(pos1, pos2));
                                player.sendMessage(ChatUtils.success("Waiting-room region has been set!"));
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " doesn't exist!"));
            }
        }
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
