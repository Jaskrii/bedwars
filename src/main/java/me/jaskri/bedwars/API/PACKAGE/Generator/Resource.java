package me.jaskri.bedwars.API.PACKAGE.Generator;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Resource {

    public static final Resource EMERALD;
    public static final Resource DIAMOND;
    public static final Resource GOLD;
    public static final Resource IRON;
    public static final Resource FREE;
    private static final Map<String, Resource> BY_NAME;
    private static final Map<Material, Resource> BY_TYPE;
    private static final Map<Material, Resource> BY_BLOCK;
    private final String name;
    private final Material resource;
    private final Material block;
    private final ChatColor color;

    public Resource(String name, Material resource, Material block, ChatColor color) {
        Preconditions.checkNotNull(name, "Resource name cannot be null!");
        Preconditions.checkNotNull(resource, "Resource type cannot be null!");
        Preconditions.checkNotNull(block, "Resource block cannot be null!");
        Preconditions.checkArgument(block.isBlock(), "Resource block type is not a block!");
        Preconditions.checkNotNull(color, "Resource color cannot be null!");
        this.name = name;
        this.resource = resource;
        this.block = block;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.resource;
    }

    public Material getBlock() {
        return this.block;
    }

    public ChatColor getChatColor() {
        return this.color;
    }

    public String getColoredName() {
        return this.color + this.name;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.block, this.color, this.name.toLowerCase(), this.resource});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Resource)) {
            return false;
        } else {
            Resource other = (Resource)obj;
            return this.name.equalsIgnoreCase(other.name) && this.resource == other.resource && this.block == other.block && this.color == other.color;
        }
    }

    public static Resource[] values() {
        return (Resource[])BY_NAME.values().toArray(new Resource[BY_NAME.size()]);
    }

    public static Resource getByName(String name) {
        return name != null ? (Resource)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static Resource getByMaterial(Material type) {
        return type != null ? (Resource)BY_TYPE.get(type) : null;
    }

    public static Resource getByBlockMaterial(Material type) {
        return type != null && type.isBlock() ? (Resource)BY_BLOCK.get(type) : null;
    }

    public static void registerResource(Resource resource) {
        if (resource != null) {
            String name = resource.name.toLowerCase();
            if (BY_NAME.get(name) != null) {
                throw new IllegalArgumentException("Cannot register resource with the same name!");
            } else if (BY_TYPE.get(resource.resource) != null) {
                throw new IllegalArgumentException("Cannot register resource with the same drop type!");
            } else if (BY_BLOCK.get(resource.block) != null) {
                throw new IllegalArgumentException("Cannot register resource with the same block type!");
            } else {
                BY_NAME.put(name, resource);
                BY_TYPE.put(resource.resource, resource);
                BY_BLOCK.put(resource.block, resource);
            }
        }
    }

    public static boolean canRegisterResource(Resource resource) {
        return BY_NAME.get(resource.name.toLowerCase()) == null && BY_TYPE.get(resource.resource) == null && BY_BLOCK.get(resource.block) == null;
    }

    static {
        EMERALD = new Resource("Emerald", Material.EMERALD, Material.EMERALD_BLOCK, ChatColor.DARK_GREEN);
        DIAMOND = new Resource("Diamond", Material.DIAMOND, Material.DIAMOND_BLOCK, ChatColor.AQUA);
        GOLD = new Resource("Gold", Material.GOLD_INGOT, Material.GOLD_BLOCK, ChatColor.GOLD);
        IRON = new Resource("Iron", Material.IRON_INGOT, Material.IRON_BLOCK, ChatColor.WHITE);
        FREE = new Resource("Free", Material.AIR, Material.AIR, ChatColor.GREEN);
        BY_NAME = new LinkedHashMap();
        BY_TYPE = new EnumMap(Material.class);
        BY_BLOCK = new EnumMap(Material.class);
        registerResource(EMERALD);
        registerResource(DIAMOND);
        registerResource(GOLD);
        registerResource(IRON);
        registerResource(FREE);
    }
}
