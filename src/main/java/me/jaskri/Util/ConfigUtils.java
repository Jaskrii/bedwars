package me.jaskri.Util;

import com.google.common.base.Preconditions;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.arena.Region;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ConfigUtils {

    private final FileConfiguration config;

    public ConfigUtils(FileConfiguration config) {
        Preconditions.checkNotNull(config, "Config cannot be null!");
        this.config = config;
    }

    public XMaterial getXMaterial(String path, XMaterial def) {
        String type = this.config.getString(path);
        if (type == null) {
            return def;
        } else {
            Optional<XMaterial> result = XMaterial.matchXMaterial(type);
            return result.isPresent() ? (XMaterial)result.get() : def;
        }
    }

    public XMaterial getXMaterial(String path) {
        return this.getXMaterial(path, (XMaterial)null);
    }

    public Material getMaterial(String path, Material def) {
        XMaterial material = this.getXMaterial(path);
        return material != null ? material.parseMaterial() : def;
    }

    public Material getMaterial(String path) {
        return this.getMaterial(path, (Material)null);
    }

    public Location getLocation(String path, Location def) {
        String location = this.config.getString(path);
        if (location != null) {
            Location result = LocationUtils.deserialize(location);
            return result != null ? result : def;
        } else {
            return null;
        }
    }

    public Location getLocation(String path) {
        return this.getLocation(path, (Location)null);
    }

    public List<Location> getLocationList(String path) {
        List<Location> result = new ArrayList();
        Iterator var3 = this.config.getStringList(path).iterator();

        while(var3.hasNext()) {
            String location = (String)var3.next();
            Location loc = LocationUtils.deserialize(location);
            if (loc != null) {
                result.add(loc);
            }
        }

        return result;
    }

    public Region getRegion(String path, Region def) {
        Location pos1 = this.getLocation(path + ".pos-1");
        Location pos2 = this.getLocation(path + ".pos-2");
        return pos1 != null && pos2 != null ? new Region(pos1, pos2) : def;
    }

    public Region getRegion(String path) {
        return this.getRegion(path, (Region)null);
    }

    public ItemCost getCost(String path, GameMode mode) {
        if (path == null) {
            return null;
        } else {
            ConfigurationSection section = this.config.getConfigurationSection(path);
            if (mode != null && section != null) {
                Iterator var4 = section.getKeys(false).iterator();

                String key;
                do {
                    if (!var4.hasNext()) {
                        return null;
                    }

                    key = (String)var4.next();
                } while(!key.equalsIgnoreCase(mode.getName()));

                return this.getCostByString(this.config.getString(path + "." + key));
            } else {
                return this.getCostByString(this.config.getString(path));
            }
        }
    }

    public ItemCost getCost(String path) {
        return this.getCost(path, (GameMode)null);
    }

    private ItemCost getCostByString(String string) {
        if (string == null) {
            return null;
        } else {
            String[] values = string.split(" ");
            return values.length < 2 ? null : new ItemCost(Resource.getByName(values[1]), NumberConversions.toInt(values[0]));
        }
    }

    public List<ItemCost> getItemCostList(String path) {
        List<ItemCost> result = new ArrayList();
        if (!this.config.isList(path)) {
            return result;
        } else {
            Iterator var3 = this.config.getStringList(path).iterator();

            while(var3.hasNext()) {
                String line = (String)var3.next();
                ItemCost cost = this.getCostByString(line);
                if (cost != null) {
                    result.add(cost);
                }
            }

            return result;
        }
    }

    public ItemDescription getDescription(String path) {
        return path != null ? new ItemDescription(this.config.getStringList(path)) : null;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
