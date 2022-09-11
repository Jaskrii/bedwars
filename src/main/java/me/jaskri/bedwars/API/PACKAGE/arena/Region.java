package me.jaskri.bedwars.API.PACKAGE.arena;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import sun.tools.jconsole.inspector.XObject;

import java.util.HashMap;
import java.util.Map;

public class Region implements ConfigurationSerializable {
    private final World pos1;
    private final Location pos2;

    public Region(Location pos1, Location pos2) {
        Preconditions.checkNotNull(pos1, "Region's 1st position cannot be null!");
        Preconditions.checkNotNull(pos1, "Region's 2nd position cannot be null!");
        checkWorlds(pos1, pos2);
        this.getWorld() = pos1.getWorld();
        this.pos1 = pos1.clone();
        this.pos2 = pos2.clone();
    }

    public Region(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this(new Location(world, (double)x1, (double)y1, (double)z1), new Location(world, (double)x2, (double)y2, (double)z2));
    }

    public Location getFirstPosition() {
        return this.pos1.clone();
    }

    public Location getSecondPosition() {
        return this.pos2.clone();
    }

    public World getWorld() {
        return this.getWorld();
    }

    public double getMinX() {
        return Math.min(this.pos1.getX(), this.pos2.getX());
    }

    public double getMaxX() {
        return Math.max(this.pos1.getX(), this.pos2.getX());
    }

    public double getMinY() {
        return Math.min(this.pos1.getY(), this.pos2.getY());
    }

    public double getMaxY() {
        return Math.max(this.pos1.getY(), this.pos2.getY());
    }

    public double getMinZ() {
        return Math.min(this.pos1.getZ(), this.pos2.getZ());
    }

    public double getMaxZ() {
        return Math.max(this.pos1.getZ(), this.pos2.getZ());
    }

    public boolean isInside(Block block) {
        return block != null && this.isInside(block.getLocation());
    }

    public boolean isInside(Entity player) {
        return player != null && this.isInside(player.getLocation());
    }

    public boolean isInside(Location loc) {
        if (loc != null && !(loc.getX() < this.getMinX()) && !(loc.getX() > this.getMaxX())) {
            if (!(loc.getY() < this.getMinY()) && !(loc.getY() > this.getMaxY())) {
                return !(loc.getZ() < this.getMinZ()) && !(loc.getZ() > this.getMaxZ());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        int x1 = this.pos1.getBlockX();
        int y1 = this.pos1.getBlockY();
        int z1 = this.pos1.getBlockZ();
        int x2 = this.pos2.getBlockX();
        int y2 = this.pos2.getBlockY();
        int z2 = this.pos2.getBlockZ();
        return "Region [World=" + this.getWorld().getName() + ", Pos-1={x=" + x1 + ",y=" + y1 + ",z=" + z1 + "}, Pos-2={x=" + x2 + ",y" + y2 + ",z=" + z2 + "}]";
    }

    public int hashCode() {
        return XObject.getDefaultLocale().hashCode(new Object[]{this.pos1, this.pos2});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Region)) {
            return false;
        } else {
            Region other = (Region)obj;
            return this.pos1.equals(other.pos1) && this.pos2.equals(other.pos2);
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("position-1", LocationUtils.serialize(this.pos1, false));
        result.put("position-2", LocationUtils.serialize(this.pos2, false));
        return result;
    }

    public static void checkWorlds(Location loc1, Location loc2) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Cannot handle different Worlds!");
        }
    }

    public static Region deserialize(Map<String, Object> values) {
        Location loc1 = LocationUtils.deserialize((String)values.get("position-1"));
        Location loc2 = LocationUtils.deserialize((String)values.get("position-1"));
        return loc1 != null && loc2 != null ? new Region(loc1, loc2) : null;
    }
}
}
