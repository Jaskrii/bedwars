package me.jaskri.API.Game.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum ArmorType {

    LEATHER("Leather", Material.LEATHER, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
    GOLD("Gold", Material.GOLD_INGOT, getGoldenLeggings(), getGoldenBoots()),
    CHAIN("Chain", Material.FIRE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS),
    IRON("Iron", Material.IRON_INGOT, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
    DIAMOND("Diamond", Material.DIAMOND, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

    private static final Map<Material, ArmorType> BY_CRAFT_TYPE = new HashMap<>(5);
    private static final Map<Material, ArmorType> BY_LEGGINGS = new HashMap(5);
    private static final Map<Material, ArmorType> BY_BOOTS = new HashMap(5);
    private final String type;
    private final Material material;
    private final Material leggings;
    private final Material boots;

    private static Material getGoldenLeggings() {
        return Version.getVersion().isNewAPI() ? Material.GOLDEN_LEGGINGS : Material.getMaterial("GOLD_LEGGINGS");
    }

    private static Material getGoldenBoots() {
        return Version.getVersion().isNewAPI() ? Material.GOLDEN_LEGGINGS : Material.getMaterial("GOLD_LEGGINGS");
    }

    private ArmorType(String type, Material material, Material leggings, Material boots) {
        this.type = type;
        this.material = material;
        this.leggings = leggings;
        this.boots = boots;
    }

    public Material getLeggings() {
        return this.leggings;
    }

    public Material getBoots() {
        return this.boots;
    }

    public String toString() {
        return this.type;
    }

    public static ArmorType getPlayerArmorType(Player player) {
        EntityEquipment equip = player.getEquipment();
        ItemStack item1 = equip.getLeggings();
        ItemStack item2 = equip.getBoots();
        if (item1 != null && item2 != null) {
            Material leggings = item1.getType();
            Material boots = item2.getType();
            return getByArmor(leggings, boots);
        } else {
            return null;
        }
    }

    public static ArmorType getByArmor(Material leggings, Material boots) {
        if (leggings != null && boots != null) {
            ArmorType result = (ArmorType)BY_LEGGINGS.get(leggings);
            if (result == null) {
                result = (ArmorType)BY_BOOTS.get(boots);
            }

            return result;
        } else {
            return null;
        }
    }

    public static ArmorType getByCraftMaterial(Material type) {
        return type != null ? (ArmorType)BY_CRAFT_TYPE.get(type) : null;
    }

    public static ArmorType getByName(String s) {
        if (s == null) {
            return null;
        } else {
            switch (s.toUpperCase()) {
                case "LEATHER":
                    return LEATHER;
                case "GOLD":
                    return GOLD;
                case "CHAIN":
                    return CHAIN;
                case "IRON":
                    return IRON;
                case "DIAMOND":
                    return DIAMOND;
                default:
                    return null;
            }
        }
    }

    static {
        ArmorType[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ArmorType type = var0[var2];
            BY_CRAFT_TYPE.put(type.material, type);
            BY_LEGGINGS.put(type.leggings, type);
            BY_BOOTS.put(type.boots, type);
        }

    }
}
