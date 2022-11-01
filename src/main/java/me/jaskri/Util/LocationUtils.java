package me.jaskri.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

public class LocationUtils {

    public LocationUtils() {
    }

    public static String serialize(Location loc, boolean yawpitch) {
        if (loc == null) {
            return null;
        } else {
            StringBuilder builder = (new StringBuilder(loc.getWorld().getName())).append(':').append(loc.getX()).append(':').append(loc.getY()).append(':').append(loc.getZ());
            if (yawpitch) {
                builder.append(':').append(loc.getYaw()).append(':').append(loc.getPitch());
            }

            return builder.toString();
        }
    }

    public static Location deserialize(String location) {
        if (location == null) {
            return null;
        } else {
            String[] values = location.split(":");
            if (values.length < 4) {
                return null;
            } else {
                World world = Bukkit.getWorld(values[0]);
                if (world == null) {
                    return null;
                } else {
                    double x = NumberConversions.toDouble(values[1]);
                    double y = NumberConversions.toDouble(values[2]);
                    double z = NumberConversions.toDouble(values[3]);
                    float yaw = values.length > 4 ? NumberConversions.toFloat(values[4]) : 0.0F;
                    float pitch = values.length > 5 ? NumberConversions.toFloat(values[5]) : 0.0F;
                    return new Location(world, x, y, z, yaw, pitch);
                }
            }
        }
    }
}
