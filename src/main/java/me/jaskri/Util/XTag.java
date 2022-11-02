package me.jaskri.Util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

public final class XTag<@Nonnull T extends Enum<T>> {

    public static final @NonNull XTag<XMaterial> AIR;
    public static final @NonNull XTag<XMaterial> INVENTORY_NOT_DISPLAYABLE;
    public static final @NonNull XTag<XMaterial> ACACIA_LOGS;
    public static final @NonNull XTag<XMaterial> ALIVE_CORAL_BLOCKS;
    public static final @NonNull XTag<XMaterial> ALIVE_CORAL_FANS;
    public static final @NonNull XTag<XMaterial> ALIVE_CORAL_PLANTS;
    public static final @NonNull XTag<XMaterial> ALIVE_CORAL_WALL_FANS;
    public static final @NonNull XTag<XMaterial> ANIMALS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> ANVIL;
    public static final @NonNull XTag<XMaterial> AXOLOTL_TEMPT_ITEMS;
    public static final @NonNull XTag<XMaterial> AXOLOTLS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> AZALEA_GROWS_ON;
    public static final @NonNull XTag<XMaterial> AZALEA_ROOT_REPLACEABLE;
    public static final @NonNull XTag<XMaterial> BAMBOO_PLANTABLE_ON;
    public static final @NonNull XTag<XMaterial> BANNERS;
    public static final @NonNull XTag<XMaterial> BASE_STONE_NETHER;
    public static final @NonNull XTag<XMaterial> BASE_STONE_OVERWORLD;
    public static final @NonNull XTag<XMaterial> BEACON_BASE_BLOCKS;
    public static final @NonNull XTag<XMaterial> BEDS;
    public static final @NonNull XTag<XMaterial> BEE_GROWABLES;
    public static final @NonNull XTag<XMaterial> BIG_DRIPLEAF_PLACEABLE;
    public static final @NonNull XTag<XMaterial> BIRCH_LOGS;
    public static final @NonNull XTag<XMaterial> BUTTONS;
    public static final @NonNull XTag<XMaterial> CAMPFIRES;
    public static final @NonNull XTag<XMaterial> CANDLE_CAKES;
    public static final @NonNull XTag<XMaterial> CANDLES;
    public static final @NonNull XTag<XMaterial> CARPETS;
    public static final @NonNull XTag<XMaterial> CAULDRONS;
    public static final @NonNull XTag<XMaterial> CAVE_VINES;
    public static final @NonNull XTag<XMaterial> CLIMBABLE;
    public static final @NonNull XTag<XMaterial> CLUSTER_MAX_HARVESTABLES;
    public static final @NonNull XTag<XMaterial> COAL_ORES;
    public static final @NonNull XTag<XMaterial> CONCRETE;
    public static final @NonNull XTag<XMaterial> CONCRETE_POWDER;
    public static final @NonNull XTag<XMaterial> COPPER_ORES;
    public static final @NonNull XTag<XMaterial> CORALS;
    public static final @NonNull XTag<XMaterial> CRIMSON_STEMS;
    public static final @NonNull XTag<XMaterial> CROPS;
    public static final @NonNull XTag<XMaterial> CRYSTAL_SOUND_BLOCKS;
    public static final @NonNull XTag<XMaterial> DARK_OAK_LOGS;
    public static final @NonNull XTag<XMaterial> DEAD_CORAL_BLOCKS;
    public static final @NonNull XTag<XMaterial> DEAD_CORAL_FANS;
    public static final @NonNull XTag<XMaterial> DEAD_CORAL_PLANTS;
    public static final @NonNull XTag<XMaterial> DEAD_CORAL_WALL_FANS;
    public static final @NonNull XTag<XMaterial> DEEPSLATE_ORE_REPLACEABLES;
    public static final @NonNull XTag<XMaterial> DIAMOND_ORES;
    public static final @NonNull XTag<XMaterial> DIRT;
    public static final @NonNull XTag<XMaterial> DOORS;
    public static final @NonNull XTag<XMaterial> DRAGON_IMMUNE;
    public static final @NonNull XTag<XMaterial> DRIPSTONE_REPLACEABLE;
    public static final @NonNull XTag<XMaterial> EMERALD_ORES;
    public static final @NonNull XTag<XMaterial> ENDERMAN_HOLDABLE;
    public static final @NonNull XTag<XMaterial> FEATURES_CANNOT_REPLACE;
    public static final @NonNull XTag<XMaterial> FENCE_GATES;
    public static final @NonNull XTag<XMaterial> FENCES;
    public static final @NonNull XTag<XMaterial> FIRE;
    public static final @NonNull XTag<XMaterial> FLOWER_POTS;
    public static final @NonNull XTag<XMaterial> FLOWERS;
    public static final @NonNull XTag<XMaterial> FOX_FOOD;
    public static final @NonNull XTag<XMaterial> FOXES_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> FREEZE_IMMUNE_WEARABLES;
    public static final @NonNull XTag<XMaterial> GEODE_INVALID_BLOCKS;
    public static final @NonNull XTag<XMaterial> GLASS;
    public static final @NonNull XTag<XMaterial> GLAZED_TERRACOTTA;
    public static final @NonNull XTag<XMaterial> GOATS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> GOLD_ORES;
    public static final @NonNull XTag<XMaterial> GUARDED_BY_PIGLINS;
    public static final @NonNull XTag<XMaterial> HOGLIN_REPELLENTS;
    public static final @NonNull XTag<XMaterial> ICE;
    public static final @NonNull XTag<XMaterial> IGNORED_BY_PIGLIN_BABIES;
    public static final @NonNull XTag<XMaterial> IMPERMEABLE;
    public static final @NonNull XTag<XMaterial> INFINIBURN_END;
    public static final @NonNull XTag<XMaterial> INFINIBURN_NETHER;
    public static final @NonNull XTag<XMaterial> INFINIBURN_OVERWORLD;
    public static final @NonNull XTag<XMaterial> INSIDE_STEP_SOUND_BLOCKS;
    public static final @NonNull XTag<XMaterial> IRON_ORES;
    public static final @NonNull XTag<XMaterial> ITEMS_ARROWS;
    public static final @NonNull XTag<XMaterial> ITEMS_BANNERS;
    public static final @NonNull XTag<XMaterial> ITEMS_BEACON_PAYMENT_ITEMS;
    public static final @NonNull XTag<XMaterial> ITEMS_BOATS;
    public static final @NonNull XTag<XMaterial> ITEMS_COALS;
    public static final @NonNull XTag<XMaterial> ITEMS_CREEPER_DROP_MUSIC_DISCS;
    public static final @NonNull XTag<XMaterial> ITEMS_FISHES;
    public static final @NonNull XTag<XMaterial> ITEMS_FURNACE_MATERIALS;
    public static final @NonNull XTag<XMaterial> ITEMS_LECTERN_BOOKS;
    public static final @NonNull XTag<XMaterial> ITEMS_MUSIC_DISCS;
    public static final @NonNull XTag<XMaterial> ITEMS_PIGLIN_LOVED;
    public static final @NonNull XTag<XMaterial> ITEMS_STONE_TOOL_MATERIALS;
    public static final @NonNull XTag<XMaterial> WALL_BANNERS;
    public static final @NonNull XTag<XMaterial> JUNGLE_LOGS;
    public static final @NonNull XTag<XMaterial> LAPIS_ORES;
    public static final @NonNull XTag<XMaterial> LAVA_POOL_STONE_CANNOT_REPLACE;
    public static final @NonNull XTag<XMaterial> LEAVES;
    public static final @NonNull XTag<XMaterial> LOGS;
    public static final @NonNull XTag<XMaterial> LOGS_THAT_BURN;
    public static final @NonNull XTag<XMaterial> LUSH_GROUND_REPLACEABLE;
    public static final @NonNull XTag<XMaterial> MINEABLE_AXE;
    public static final @NonNull XTag<XMaterial> MINEABLE_HOE;
    public static final @NonNull XTag<XMaterial> MINEABLE_PICKAXE;
    public static final @NonNull XTag<XMaterial> MINEABLE_SHOVEL;
    public static final @NonNull XTag<XMaterial> MOOSHROOMS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> MOSS_REPLACEABLE;
    public static final @NonNull XTag<XMaterial> MUSHROOM_GROW_BLOCK;
    public static final @NonNull XTag<XMaterial> NEEDS_DIAMOND_TOOL;
    public static final @NonNull XTag<XMaterial> NEEDS_IRON_TOOL;
    public static final @NonNull XTag<XMaterial> NEEDS_STONE_TOOL;
    public static final @NonNull XTag<XMaterial> NON_FLAMMABLE_WOOD;
    public static final @NonNull XTag<XMaterial> NON_WOODEN_STAIRS;
    public static final @NonNull XTag<XMaterial> NON_WOODEN_SLABS;
    public static final @NonNull XTag<XMaterial> NYLIUM;
    public static final @NonNull XTag<XMaterial> OAK_LOGS;
    public static final @NonNull XTag<XMaterial> OCCLUDES_VIBRATION_SIGNALS;
    public static final @NonNull XTag<XMaterial> ORES;
    public static final @NonNull XTag<XMaterial> PARROTS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> PIGLIN_FOOD;
    public static final @NonNull XTag<XMaterial> PIGLIN_REPELLENTS;
    public static final @NonNull XTag<XMaterial> PLANKS;
    public static final @NonNull XTag<XMaterial> POLAR_BEARS_SPAWNABLE_ON_IN_FROZEN_OCEAN;
    public static final @NonNull XTag<XMaterial> PORTALS;
    public static final @NonNull XTag<XMaterial> PRESSURE_PLATES;
    public static final @NonNull XTag<XMaterial> PREVENT_MOB_SPAWNING_INSIDE;
    public static final @NonNull XTag<XMaterial> RABBITS_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> RAILS;
    public static final @NonNull XTag<XMaterial> REDSTONE_ORES;
    public static final @NonNull XTag<XMaterial> REPLACEABLE_PLANTS;
    public static final @NonNull XTag<XMaterial> SAND;
    public static final @NonNull XTag<XMaterial> SAPLINGS;
    public static final @NonNull XTag<XMaterial> SHULKER_BOXES;
    public static final @NonNull XTag<XMaterial> SIGNS;
    public static final @NonNull XTag<XMaterial> SMALL_DRIPLEAF_PLACEABLE;
    public static final @NonNull XTag<XMaterial> SMALL_FLOWERS;
    public static final @NonNull XTag<XMaterial> SNOW;
    public static final @NonNull XTag<XMaterial> SOUL_FIRE_BASE_BLOCKS;
    public static final @NonNull XTag<XMaterial> SOUL_SPEED_BLOCKS;
    public static final @NonNull XTag<XMaterial> SPRUCE_LOGS;
    public static final @NonNull XTag<XMaterial> STAIRS;
    public static final @NonNull XTag<XMaterial> STANDING_SIGNS;
    public static final @NonNull XTag<XMaterial> STONE_BRICKS;
    public static final @NonNull XTag<XMaterial> STONE_ORE_REPLACEABLES;
    public static final @NonNull XTag<XMaterial> STONE_PRESSURE_PLATES;
    public static final @NonNull XTag<XMaterial> STRIDER_WARM_BLOCKS;
    public static final @NonNull XTag<XMaterial> TALL_FLOWERS;
    public static final @NonNull XTag<XMaterial> TERRACOTTA;
    public static final @NonNull XTag<XMaterial> TRAPDOORS;
    public static final @NonNull XTag<XMaterial> UNDERWATER_BONEMEALS;
    public static final @NonNull XTag<XMaterial> UNSTABLE_BOTTOM_CENTER;
    public static final @NonNull XTag<XMaterial> VALID_SPAWN;
    public static final @NonNull XTag<XMaterial> WALL_POST_OVERRIDE;
    public static final @NonNull XTag<XMaterial> WALL_SIGNS;
    public static final @NonNull XTag<XMaterial> WALLS;
    public static final @NonNull XTag<XMaterial> WARPED_STEMS;
    public static final @NonNull XTag<XMaterial> WITHER_IMMUNE;
    public static final @NonNull XTag<XMaterial> WITHER_SUMMON_BASE_BLOCKS;
    public static final @NonNull XTag<XMaterial> WOLVES_SPAWNABLE_ON;
    public static final @NonNull XTag<XMaterial> WOODEN_BUTTONS;
    public static final @NonNull XTag<XMaterial> WOODEN_DOORS;
    public static final @NonNull XTag<XMaterial> WOODEN_FENCE_GATES;
    public static final @NonNull XTag<XMaterial> WOODEN_FENCES;
    public static final @NonNull XTag<XMaterial> WOODEN_PRESSURE_PLATES;
    public static final @NonNull XTag<XMaterial> WOODEN_SLABS;
    public static final @NonNull XTag<XMaterial> WOODEN_STAIRS;
    public static final @NonNull XTag<XMaterial> WOODEN_TRAPDOORS;
    public static final @NonNull XTag<XMaterial> WOOL;
    public static final @NonNull XTag<XMaterial> LEATHER_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> IRON_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> CHAINMAIL_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> GOLDEN_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> DIAMOND_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> NETHERITE_ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> ARMOR_PIECES;
    public static final @NonNull XTag<XMaterial> WOODEN_TOOLS;
    public static final @NonNull XTag<XMaterial> STONE_TOOLS;
    public static final @NonNull XTag<XMaterial> IRON_TOOLS;
    public static final @NonNull XTag<XMaterial> DIAMOND_TOOLS;
    public static final @NonNull XTag<XMaterial> NETHERITE_TOOLS;
    public static final @NonNull XTag<XEnchantment> ARMOR_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> HELEMT_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> CHESTPLATE_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> LEGGINGS_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> BOOTS_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> ELYTRA_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> SWORD_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> AXE_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> HOE_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> PICKAXE_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> SHOVEL_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> SHEARS_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> BOW_ENCHANTS;
    public static final @NonNull XTag<XEnchantment> CROSSBOW_ENCHANTS;
    private @NonNull Set<@NonNull T> values;

    @SafeVarargs
    private XTag(T... values) {
        this.values = Collections.unmodifiableSet(EnumSet.copyOf(Arrays.asList(values)));
    }

    @SafeVarargs
    private XTag(@NonNull Class<T> clazz, XTag<T>... values) {
        this.values = EnumSet.noneOf(clazz);
        this.inheritFrom(values);
    }

    private static XMaterial[] findAllColors(String material) {
        String[] colorPrefixes = new String[]{"ORANGE", "LIGHT_BLUE", "GRAY", "BLACK", "MAGENTA", "PINK", "BLUE", "GREEN", "CYAN", "PURPLE", "YELLOW", "LIME", "LIGHT_GRAY", "WHITE", "BROWN", "RED"};
        List<XMaterial> list = new ArrayList();
        Optional var10000 = XMaterial.matchXMaterial(material);
        Objects.requireNonNull(list);
        var10000.ifPresent(list::add);
        String[] var3 = colorPrefixes;
        int var4 = colorPrefixes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String color = var3[var5];
            var10000 = XMaterial.matchXMaterial(color + '_' + material);
            Objects.requireNonNull(list);
            var10000.ifPresent(list::add);
        }

        return (XMaterial[])list.toArray(new XMaterial[0]);
    }

    private static XMaterial[] findAllWoodTypes(String material) {
        String[] woodPrefixes = new String[]{"ACACIA", "DARK_OAK", "JUNGLE", "BIRCH", "WARPED", "OAK", "SPRUCE", "CRIMSON"};
        List<XMaterial> list = new ArrayList();
        String[] var3 = woodPrefixes;
        int var4 = woodPrefixes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String wood = var3[var5];
            Optional var10000 = XMaterial.matchXMaterial(wood + '_' + material);
            Objects.requireNonNull(list);
            var10000.ifPresent(list::add);
        }

        return (XMaterial[])list.toArray(new XMaterial[0]);
    }

    private static XMaterial[] findAllCorals(boolean alive, boolean block, boolean fan, boolean wall) {
        String[] materials = new String[]{"FIRE", "TUBE", "BRAIN", "HORN", "BUBBLE"};
        List<XMaterial> list = new ArrayList();
        String[] var6 = materials;
        int var7 = materials.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String material = var6[var8];
            StringBuilder builder = new StringBuilder();
            if (!alive) {
                builder.append("DEAD_");
            }

            builder.append(material).append("_CORAL");
            if (block) {
                builder.append("_BLOCK");
            }

            if (fan) {
                if (wall) {
                    builder.append("_WALL");
                }

                builder.append("_FAN");
            }

            Optional var10000 = XMaterial.matchXMaterial(builder.toString());
            Objects.requireNonNull(list);
            var10000.ifPresent(list::add);
        }

        return (XMaterial[])list.toArray(new XMaterial[0]);
    }

    public static boolean isItem(XMaterial material) {
        if (!XMaterial.supports(13)) {
            switch (material) {
                case ATTACHED_MELON_STEM:
                case ATTACHED_PUMPKIN_STEM:
                case BEETROOTS:
                case BLACK_WALL_BANNER:
                case BLUE_WALL_BANNER:
                case BROWN_WALL_BANNER:
                case CARROTS:
                case COCOA:
                case CREEPER_WALL_HEAD:
                case CYAN_WALL_BANNER:
                case DRAGON_WALL_HEAD:
                case END_GATEWAY:
                case END_PORTAL:
                case FIRE:
                case FIRE_CORAL_WALL_FAN:
                case FROSTED_ICE:
                case GRAY_WALL_BANNER:
                case GREEN_WALL_BANNER:
                case HORN_CORAL_WALL_FAN:
                case LAVA:
                case LIGHT_BLUE_WALL_BANNER:
                case LIGHT_GRAY_WALL_BANNER:
                case LIME_WALL_BANNER:
                case MAGENTA_WALL_BANNER:
                case MELON_STEM:
                case MOVING_PISTON:
                case NETHER_PORTAL:
                case ORANGE_WALL_BANNER:
                case PINK_WALL_BANNER:
                case PISTON_HEAD:
                case PLAYER_WALL_HEAD:
                case POTATOES:
                case POTTED_ACACIA_SAPLING:
                case POTTED_ALLIUM:
                case POTTED_AZURE_BLUET:
                case POTTED_BIRCH_SAPLING:
                case POTTED_BLUE_ORCHID:
                case POTTED_BROWN_MUSHROOM:
                case POTTED_CACTUS:
                case POTTED_DANDELION:
                case POTTED_DARK_OAK_SAPLING:
                case POTTED_DEAD_BUSH:
                case POTTED_FERN:
                case POTTED_JUNGLE_SAPLING:
                case POTTED_OAK_SAPLING:
                case POTTED_ORANGE_TULIP:
                case POTTED_OXEYE_DAISY:
                case POTTED_PINK_TULIP:
                case POTTED_POPPY:
                case POTTED_RED_MUSHROOM:
                case POTTED_RED_TULIP:
                case POTTED_SPRUCE_SAPLING:
                case POTTED_WHITE_TULIP:
                case PUMPKIN_STEM:
                case PURPLE_WALL_BANNER:
                case REDSTONE_WALL_TORCH:
                case REDSTONE_WIRE:
                case RED_WALL_BANNER:
                case SKELETON_WALL_SKULL:
                case TRIPWIRE:
                case ACACIA_WALL_SIGN:
                case OAK_WALL_SIGN:
                case BIRCH_WALL_SIGN:
                case JUNGLE_WALL_SIGN:
                case SPRUCE_WALL_SIGN:
                case DARK_OAK_WALL_SIGN:
                case WALL_TORCH:
                case WATER:
                case WHITE_WALL_BANNER:
                case WITHER_SKELETON_WALL_SKULL:
                case YELLOW_WALL_BANNER:
                case ZOMBIE_WALL_HEAD:
                    return false;
                default:
                    return true;
            }
        } else {
            Material mat = material.parseMaterial();
            return mat != null && mat.isItem();
        }
    }

    public static boolean isInteractable(XMaterial material) {
        if (XMaterial.supports(13)) {
            return material.parseMaterial().isInteractable();
        } else {
            switch (material) {
                case MOVING_PISTON:
                case POTTED_ACACIA_SAPLING:
                case POTTED_ALLIUM:
                case POTTED_AZURE_BLUET:
                case POTTED_BIRCH_SAPLING:
                case POTTED_BLUE_ORCHID:
                case POTTED_BROWN_MUSHROOM:
                case POTTED_CACTUS:
                case POTTED_DANDELION:
                case POTTED_DARK_OAK_SAPLING:
                case POTTED_DEAD_BUSH:
                case POTTED_FERN:
                case POTTED_JUNGLE_SAPLING:
                case POTTED_OAK_SAPLING:
                case POTTED_ORANGE_TULIP:
                case POTTED_OXEYE_DAISY:
                case POTTED_PINK_TULIP:
                case POTTED_POPPY:
                case POTTED_RED_MUSHROOM:
                case POTTED_RED_TULIP:
                case POTTED_SPRUCE_SAPLING:
                case POTTED_WHITE_TULIP:
                case ACACIA_WALL_SIGN:
                case OAK_WALL_SIGN:
                case BIRCH_WALL_SIGN:
                case JUNGLE_WALL_SIGN:
                case SPRUCE_WALL_SIGN:
                case DARK_OAK_WALL_SIGN:
                case ACACIA_BUTTON:
                case ACACIA_DOOR:
                case ACACIA_FENCE:
                case ACACIA_FENCE_GATE:
                case ACACIA_STAIRS:
                case ACACIA_TRAPDOOR:
                case ANVIL:
                case BEACON:
                case BIRCH_BUTTON:
                case BIRCH_DOOR:
                case BIRCH_FENCE:
                case BIRCH_FENCE_GATE:
                case BIRCH_STAIRS:
                case BIRCH_TRAPDOOR:
                case BLACK_BED:
                case BLACK_SHULKER_BOX:
                case BLUE_BED:
                case BLUE_SHULKER_BOX:
                case BREWING_STAND:
                case BRICK_STAIRS:
                case BROWN_BED:
                case BROWN_SHULKER_BOX:
                case CAKE:
                case CAULDRON:
                case CHAIN_COMMAND_BLOCK:
                case CHEST:
                case CHIPPED_ANVIL:
                case COBBLESTONE_STAIRS:
                case COMMAND_BLOCK:
                case COMPARATOR:
                case CRAFTING_TABLE:
                case CYAN_BED:
                case CYAN_SHULKER_BOX:
                case DAMAGED_ANVIL:
                case DARK_OAK_BUTTON:
                case DARK_OAK_DOOR:
                case DARK_OAK_FENCE:
                case DARK_OAK_FENCE_GATE:
                case DARK_OAK_STAIRS:
                case DARK_OAK_TRAPDOOR:
                case DARK_PRISMARINE_STAIRS:
                case DAYLIGHT_DETECTOR:
                case DISPENSER:
                case DRAGON_EGG:
                case DROPPER:
                case ENCHANTING_TABLE:
                case ENDER_CHEST:
                case FLOWER_POT:
                case FURNACE:
                case GRAY_BED:
                case GRAY_SHULKER_BOX:
                case GREEN_BED:
                case GREEN_SHULKER_BOX:
                case HOPPER:
                case IRON_DOOR:
                case IRON_TRAPDOOR:
                case JUKEBOX:
                case JUNGLE_BUTTON:
                case JUNGLE_DOOR:
                case JUNGLE_FENCE:
                case JUNGLE_FENCE_GATE:
                case JUNGLE_STAIRS:
                case JUNGLE_TRAPDOOR:
                case LEVER:
                case LIGHT_BLUE_BED:
                case LIGHT_BLUE_SHULKER_BOX:
                case LIGHT_GRAY_BED:
                case LIGHT_GRAY_SHULKER_BOX:
                case LIME_BED:
                case LIME_SHULKER_BOX:
                case MAGENTA_BED:
                case MAGENTA_SHULKER_BOX:
                case NETHER_BRICK_FENCE:
                case NETHER_BRICK_STAIRS:
                case NOTE_BLOCK:
                case OAK_BUTTON:
                case OAK_DOOR:
                case OAK_FENCE:
                case OAK_FENCE_GATE:
                case OAK_STAIRS:
                case OAK_TRAPDOOR:
                case ORANGE_BED:
                case ORANGE_SHULKER_BOX:
                case PINK_BED:
                case PINK_SHULKER_BOX:
                case PRISMARINE_BRICK_STAIRS:
                case PRISMARINE_STAIRS:
                case PUMPKIN:
                case PURPLE_BED:
                case PURPLE_SHULKER_BOX:
                case PURPUR_STAIRS:
                case QUARTZ_STAIRS:
                case REDSTONE_ORE:
                case RED_BED:
                case RED_SANDSTONE_STAIRS:
                case RED_SHULKER_BOX:
                case REPEATER:
                case REPEATING_COMMAND_BLOCK:
                case SANDSTONE_STAIRS:
                case SHULKER_BOX:
                case ACACIA_SIGN:
                case BIRCH_SIGN:
                case DARK_OAK_SIGN:
                case JUNGLE_SIGN:
                case OAK_SIGN:
                case SPRUCE_SIGN:
                case SPRUCE_BUTTON:
                case SPRUCE_DOOR:
                case SPRUCE_FENCE:
                case SPRUCE_FENCE_GATE:
                case SPRUCE_STAIRS:
                case SPRUCE_TRAPDOOR:
                case STONE_BRICK_STAIRS:
                case STONE_BUTTON:
                case STRUCTURE_BLOCK:
                case TNT:
                case TRAPPED_CHEST:
                case WHITE_BED:
                case WHITE_SHULKER_BOX:
                case YELLOW_BED:
                case YELLOW_SHULKER_BOX:
                    return true;
                case NETHER_PORTAL:
                case ORANGE_WALL_BANNER:
                case PINK_WALL_BANNER:
                case PISTON_HEAD:
                case PLAYER_WALL_HEAD:
                case POTATOES:
                case PUMPKIN_STEM:
                case PURPLE_WALL_BANNER:
                case REDSTONE_WALL_TORCH:
                case REDSTONE_WIRE:
                case RED_WALL_BANNER:
                case SKELETON_WALL_SKULL:
                case TRIPWIRE:
                case WALL_TORCH:
                case WATER:
                case WHITE_WALL_BANNER:
                case WITHER_SKELETON_WALL_SKULL:
                case YELLOW_WALL_BANNER:
                case ZOMBIE_WALL_HEAD:
                default:
                    return false;
            }
        }
    }

    public @NonNull Set<@NonNull T> getValues() {
        return this.values;
    }

    public boolean isTagged(@Nullable T value) {
        return value != null && this.values.contains(value);
    }

    @SafeVarargs
    private final XTag<T> inheritFrom(XTag<T>... values) {
        EnumSet newValues;
        if (this.values.isEmpty()) {
            newValues = EnumSet.copyOf((EnumSet)this.values);
        } else {
            newValues = EnumSet.copyOf(this.values);
        }

        XTag[] var3 = values;
        int var4 = values.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            XTag<T> value = var3[var5];
            newValues.addAll(value.values);
        }

        this.values = Collections.unmodifiableSet(newValues);
        return this;
    }

    static {
        ACACIA_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_ACACIA_LOG, XMaterial.ACACIA_LOG, XMaterial.ACACIA_WOOD, XMaterial.STRIPPED_ACACIA_WOOD});
        BIRCH_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_BIRCH_LOG, XMaterial.BIRCH_LOG, XMaterial.BIRCH_WOOD, XMaterial.STRIPPED_BIRCH_WOOD});
        DARK_OAK_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_DARK_OAK_LOG, XMaterial.DARK_OAK_LOG, XMaterial.DARK_OAK_WOOD, XMaterial.STRIPPED_DARK_OAK_WOOD});
        JUNGLE_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_JUNGLE_LOG, XMaterial.JUNGLE_LOG, XMaterial.JUNGLE_WOOD, XMaterial.STRIPPED_JUNGLE_WOOD});
        OAK_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_OAK_LOG, XMaterial.OAK_LOG, XMaterial.OAK_WOOD, XMaterial.STRIPPED_OAK_WOOD});
        SPRUCE_LOGS = new XTag(new XMaterial[]{XMaterial.STRIPPED_SPRUCE_LOG, XMaterial.SPRUCE_LOG, XMaterial.SPRUCE_WOOD, XMaterial.STRIPPED_SPRUCE_WOOD});
        CANDLE_CAKES = new XTag(findAllColors("CANDLE_CAKE"));
        CANDLES = new XTag(findAllColors("CANDLE"));
        TERRACOTTA = new XTag(findAllColors("TERRACOTTA"));
        GLAZED_TERRACOTTA = new XTag(findAllColors("GLAZED_TERRACOTTA"));
        SHULKER_BOXES = new XTag(findAllColors("SHULKER_BOX"));
        CARPETS = new XTag(findAllColors("CARPET"));
        WOOL = new XTag(findAllColors("WOOL"));
        GLASS = new XTag(findAllColors("GLASS"));
        GLASS.inheritFrom(new XTag(new XMaterial[]{XMaterial.TINTED_GLASS}));
        ITEMS_BANNERS = new XTag(findAllColors("BANNER"));
        WALL_BANNERS = new XTag(findAllColors("WALL_BANNER"));
        BANNERS = new XTag(XMaterial.class, new XTag[]{ITEMS_BANNERS, WALL_BANNERS});
        BEDS = new XTag(findAllColors("BED"));
        CONCRETE = new XTag(findAllColors("CONCRETE"));
        CONCRETE_POWDER = new XTag(findAllColors("CONCRETE_POWDER"));
        STANDING_SIGNS = new XTag(findAllWoodTypes("SIGN"));
        WALL_SIGNS = new XTag(findAllWoodTypes("WALL_SIGN"));
        WOODEN_PRESSURE_PLATES = new XTag(findAllWoodTypes("PRESSURE_PLATE"));
        WOODEN_DOORS = new XTag(findAllWoodTypes("DOOR"));
        WOODEN_FENCE_GATES = new XTag(findAllWoodTypes("FENCE_GATE"));
        WOODEN_FENCES = new XTag(findAllWoodTypes("FENCE"));
        WOODEN_SLABS = new XTag(findAllWoodTypes("SLAB"));
        WOODEN_STAIRS = new XTag(findAllWoodTypes("STAIRS"));
        WOODEN_TRAPDOORS = new XTag(findAllWoodTypes("TRAPDOOR"));
        PLANKS = new XTag(findAllWoodTypes("PLANKS"));
        WOODEN_BUTTONS = new XTag(findAllWoodTypes("BUTTON"));
        COAL_ORES = new XTag(new XMaterial[]{XMaterial.COAL_ORE, XMaterial.DEEPSLATE_COAL_ORE});
        IRON_ORES = new XTag(new XMaterial[]{XMaterial.IRON_ORE, XMaterial.DEEPSLATE_IRON_ORE});
        COPPER_ORES = new XTag(new XMaterial[]{XMaterial.COPPER_ORE, XMaterial.DEEPSLATE_COPPER_ORE});
        REDSTONE_ORES = new XTag(new XMaterial[]{XMaterial.REDSTONE_ORE, XMaterial.DEEPSLATE_REDSTONE_ORE});
        LAPIS_ORES = new XTag(new XMaterial[]{XMaterial.LAPIS_ORE, XMaterial.DEEPSLATE_LAPIS_ORE});
        GOLD_ORES = new XTag(new XMaterial[]{XMaterial.GOLD_ORE, XMaterial.DEEPSLATE_GOLD_ORE, XMaterial.NETHER_GOLD_ORE});
        ORES = new XTag(new XMaterial[]{XMaterial.ANCIENT_DEBRIS, XMaterial.NETHER_QUARTZ_ORE});
        ORES.inheritFrom(COAL_ORES, IRON_ORES, COPPER_ORES, REDSTONE_ORES, LAPIS_ORES, GOLD_ORES);
        ALIVE_CORAL_WALL_FANS = new XTag(findAllCorals(true, false, true, true));
        ALIVE_CORAL_FANS = new XTag(findAllCorals(true, false, true, false));
        ALIVE_CORAL_BLOCKS = new XTag(findAllCorals(true, true, false, false));
        ALIVE_CORAL_PLANTS = new XTag(findAllCorals(true, false, false, false));
        DEAD_CORAL_WALL_FANS = new XTag(findAllCorals(false, false, true, true));
        DEAD_CORAL_FANS = new XTag(findAllCorals(false, false, true, false));
        DEAD_CORAL_BLOCKS = new XTag(findAllCorals(false, true, false, false));
        DEAD_CORAL_PLANTS = new XTag(findAllCorals(false, false, false, false));
        CORALS = new XTag(XMaterial.class, new XTag[]{ALIVE_CORAL_WALL_FANS, ALIVE_CORAL_FANS, ALIVE_CORAL_BLOCKS, ALIVE_CORAL_PLANTS, DEAD_CORAL_WALL_FANS, DEAD_CORAL_FANS, DEAD_CORAL_BLOCKS, DEAD_CORAL_PLANTS});
        AIR = new XTag(new XMaterial[]{XMaterial.AIR, XMaterial.CAVE_AIR, XMaterial.VOID_AIR});
        PORTALS = new XTag(new XMaterial[]{XMaterial.END_GATEWAY, XMaterial.END_PORTAL, XMaterial.NETHER_PORTAL});
        INVENTORY_NOT_DISPLAYABLE = new XTag(XMaterial.class, new XTag[]{AIR, PORTALS});
        WALLS = new XTag(new XMaterial[]{XMaterial.POLISHED_DEEPSLATE_WALL, XMaterial.NETHER_BRICK_WALL, XMaterial.POLISHED_BLACKSTONE_WALL, XMaterial.DEEPSLATE_BRICK_WALL, XMaterial.RED_SANDSTONE_WALL, XMaterial.BRICK_WALL, XMaterial.COBBLESTONE_WALL, XMaterial.POLISHED_BLACKSTONE_BRICK_WALL, XMaterial.PRISMARINE_WALL, XMaterial.SANDSTONE_WALL, XMaterial.GRANITE_WALL, XMaterial.DEEPSLATE_TILE_WALL, XMaterial.BLACKSTONE_WALL, XMaterial.STONE_BRICK_WALL, XMaterial.RED_NETHER_BRICK_WALL, XMaterial.DIORITE_WALL, XMaterial.MOSSY_COBBLESTONE_WALL, XMaterial.ANDESITE_WALL, XMaterial.MOSSY_STONE_BRICK_WALL, XMaterial.END_STONE_BRICK_WALL, XMaterial.COBBLED_DEEPSLATE_WALL});
        STONE_PRESSURE_PLATES = new XTag(new XMaterial[]{XMaterial.STONE_PRESSURE_PLATE, XMaterial.POLISHED_BLACKSTONE_PRESSURE_PLATE});
        RAILS = new XTag(new XMaterial[]{XMaterial.RAIL, XMaterial.ACTIVATOR_RAIL, XMaterial.DETECTOR_RAIL, XMaterial.POWERED_RAIL});
        ANIMALS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.GRASS_BLOCK});
        ANVIL = new XTag(new XMaterial[]{XMaterial.ANVIL, XMaterial.CHIPPED_ANVIL, XMaterial.DAMAGED_ANVIL});
        AXOLOTL_TEMPT_ITEMS = new XTag(new XMaterial[]{XMaterial.TROPICAL_FISH_BUCKET});
        AXOLOTLS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.CLAY});
        SNOW = new XTag(new XMaterial[]{XMaterial.SNOW_BLOCK, XMaterial.SNOW, XMaterial.POWDER_SNOW});
        SAND = new XTag(new XMaterial[]{XMaterial.SAND, XMaterial.RED_SAND});
        DIRT = new XTag(new XMaterial[]{XMaterial.MOSS_BLOCK, XMaterial.COARSE_DIRT, XMaterial.PODZOL, XMaterial.DIRT, XMaterial.ROOTED_DIRT, XMaterial.MYCELIUM, XMaterial.GRASS_BLOCK});
        CAVE_VINES = new XTag(new XMaterial[]{XMaterial.CAVE_VINES, XMaterial.CAVE_VINES_PLANT});
        BASE_STONE_NETHER = new XTag(new XMaterial[]{XMaterial.NETHERRACK, XMaterial.BASALT, XMaterial.BLACKSTONE});
        BASE_STONE_OVERWORLD = new XTag(new XMaterial[]{XMaterial.TUFF, XMaterial.DIORITE, XMaterial.DEEPSLATE, XMaterial.ANDESITE, XMaterial.GRANITE, XMaterial.STONE});
        BEACON_BASE_BLOCKS = new XTag(new XMaterial[]{XMaterial.NETHERITE_BLOCK, XMaterial.GOLD_BLOCK, XMaterial.IRON_BLOCK, XMaterial.EMERALD_BLOCK, XMaterial.DIAMOND_BLOCK});
        CROPS = new XTag(new XMaterial[]{XMaterial.CARROTS, XMaterial.POTATOES, XMaterial.WHEAT, XMaterial.MELON_STEM, XMaterial.BEETROOTS, XMaterial.PUMPKIN_STEM});
        CAMPFIRES = new XTag(new XMaterial[]{XMaterial.CAMPFIRE, XMaterial.SOUL_CAMPFIRE});
        CAULDRONS = new XTag(new XMaterial[]{XMaterial.CAULDRON, XMaterial.LAVA_CAULDRON, XMaterial.POWDER_SNOW_CAULDRON, XMaterial.WATER_CAULDRON});
        CLIMBABLE = new XTag(new XMaterial[]{XMaterial.SCAFFOLDING, XMaterial.WEEPING_VINES_PLANT, XMaterial.WEEPING_VINES, XMaterial.TWISTING_VINES, XMaterial.TWISTING_VINES_PLANT, XMaterial.VINE, XMaterial.LADDER});
        CLIMBABLE.inheritFrom(CAVE_VINES);
        CLUSTER_MAX_HARVESTABLES = new XTag(new XMaterial[]{XMaterial.DIAMOND_PICKAXE, XMaterial.GOLDEN_PICKAXE, XMaterial.STONE_PICKAXE, XMaterial.NETHERITE_PICKAXE, XMaterial.WOODEN_PICKAXE, XMaterial.IRON_PICKAXE});
        CRIMSON_STEMS = new XTag(new XMaterial[]{XMaterial.CRIMSON_HYPHAE, XMaterial.STRIPPED_CRIMSON_STEM, XMaterial.CRIMSON_STEM, XMaterial.STRIPPED_CRIMSON_HYPHAE});
        WARPED_STEMS = new XTag(new XMaterial[]{XMaterial.WARPED_HYPHAE, XMaterial.STRIPPED_WARPED_STEM, XMaterial.WARPED_STEM, XMaterial.STRIPPED_WARPED_HYPHAE});
        CRYSTAL_SOUND_BLOCKS = new XTag(new XMaterial[]{XMaterial.AMETHYST_BLOCK, XMaterial.BUDDING_AMETHYST});
        DEEPSLATE_ORE_REPLACEABLES = new XTag(new XMaterial[]{XMaterial.TUFF, XMaterial.DEEPSLATE});
        DIAMOND_ORES = new XTag(new XMaterial[]{XMaterial.DIAMOND_ORE, XMaterial.DEEPSLATE_DIAMOND_ORE});
        DOORS = new XTag(new XMaterial[]{XMaterial.IRON_DOOR});
        DOORS.inheritFrom(WOODEN_DOORS);
        WITHER_IMMUNE = new XTag(new XMaterial[]{XMaterial.STRUCTURE_BLOCK, XMaterial.END_GATEWAY, XMaterial.BEDROCK, XMaterial.END_PORTAL, XMaterial.COMMAND_BLOCK, XMaterial.REPEATING_COMMAND_BLOCK, XMaterial.MOVING_PISTON, XMaterial.CHAIN_COMMAND_BLOCK, XMaterial.BARRIER, XMaterial.END_PORTAL_FRAME, XMaterial.JIGSAW});
        WITHER_SUMMON_BASE_BLOCKS = new XTag(new XMaterial[]{XMaterial.SOUL_SOIL, XMaterial.SOUL_SAND});
        EMERALD_ORES = new XTag(new XMaterial[]{XMaterial.EMERALD_ORE, XMaterial.DEEPSLATE_EMERALD_ORE});
        NYLIUM = new XTag(new XMaterial[]{XMaterial.CRIMSON_NYLIUM, XMaterial.WARPED_NYLIUM});
        SMALL_FLOWERS = new XTag(new XMaterial[]{XMaterial.RED_TULIP, XMaterial.AZURE_BLUET, XMaterial.OXEYE_DAISY, XMaterial.BLUE_ORCHID, XMaterial.PINK_TULIP, XMaterial.POPPY, XMaterial.WHITE_TULIP, XMaterial.DANDELION, XMaterial.ALLIUM, XMaterial.CORNFLOWER, XMaterial.ORANGE_TULIP, XMaterial.LILY_OF_THE_VALLEY, XMaterial.WITHER_ROSE});
        TALL_FLOWERS = new XTag(new XMaterial[]{XMaterial.PEONY, XMaterial.SUNFLOWER, XMaterial.LILAC, XMaterial.ROSE_BUSH});
        FEATURES_CANNOT_REPLACE = new XTag(new XMaterial[]{XMaterial.SPAWNER, XMaterial.END_PORTAL_FRAME, XMaterial.BEDROCK, XMaterial.CHEST});
        FENCE_GATES = new XTag(XMaterial.class, new XTag[]{WOODEN_FENCE_GATES});
        FENCES = new XTag(new XMaterial[]{XMaterial.NETHER_BRICK_FENCE});
        FENCES.inheritFrom(WOODEN_FENCES);
        FIRE = new XTag(new XMaterial[]{XMaterial.FIRE, XMaterial.SOUL_FIRE});
        FLOWER_POTS = new XTag(new XMaterial[]{XMaterial.POTTED_OAK_SAPLING, XMaterial.POTTED_WITHER_ROSE, XMaterial.POTTED_ACACIA_SAPLING, XMaterial.POTTED_LILY_OF_THE_VALLEY, XMaterial.POTTED_WARPED_FUNGUS, XMaterial.POTTED_WARPED_ROOTS, XMaterial.POTTED_ALLIUM, XMaterial.POTTED_BROWN_MUSHROOM, XMaterial.POTTED_WHITE_TULIP, XMaterial.POTTED_ORANGE_TULIP, XMaterial.POTTED_DANDELION, XMaterial.POTTED_AZURE_BLUET, XMaterial.POTTED_FLOWERING_AZALEA_BUSH, XMaterial.POTTED_PINK_TULIP, XMaterial.POTTED_CORNFLOWER, XMaterial.POTTED_CRIMSON_FUNGUS, XMaterial.POTTED_RED_MUSHROOM, XMaterial.POTTED_BLUE_ORCHID, XMaterial.POTTED_FERN, XMaterial.POTTED_POPPY, XMaterial.POTTED_CRIMSON_ROOTS, XMaterial.POTTED_RED_TULIP, XMaterial.POTTED_OXEYE_DAISY, XMaterial.POTTED_AZALEA_BUSH, XMaterial.POTTED_BAMBOO, XMaterial.POTTED_CACTUS, XMaterial.FLOWER_POT, XMaterial.POTTED_DEAD_BUSH, XMaterial.POTTED_DARK_OAK_SAPLING, XMaterial.POTTED_SPRUCE_SAPLING, XMaterial.POTTED_JUNGLE_SAPLING, XMaterial.POTTED_BIRCH_SAPLING});
        FOX_FOOD = new XTag(new XMaterial[]{XMaterial.GLOW_BERRIES, XMaterial.SWEET_BERRIES});
        FOXES_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.SNOW, XMaterial.SNOW_BLOCK, XMaterial.PODZOL, XMaterial.GRASS_BLOCK, XMaterial.COARSE_DIRT});
        FREEZE_IMMUNE_WEARABLES = new XTag(new XMaterial[]{XMaterial.LEATHER_BOOTS, XMaterial.LEATHER_CHESTPLATE, XMaterial.LEATHER_HELMET, XMaterial.LEATHER_LEGGINGS, XMaterial.LEATHER_HORSE_ARMOR});
        ICE = new XTag(new XMaterial[]{XMaterial.ICE, XMaterial.PACKED_ICE, XMaterial.BLUE_ICE, XMaterial.FROSTED_ICE});
        GEODE_INVALID_BLOCKS = new XTag(new XMaterial[]{XMaterial.BEDROCK, XMaterial.WATER, XMaterial.LAVA, XMaterial.ICE, XMaterial.PACKED_ICE, XMaterial.BLUE_ICE});
        HOGLIN_REPELLENTS = new XTag(new XMaterial[]{XMaterial.WARPED_FUNGUS, XMaterial.NETHER_PORTAL, XMaterial.POTTED_WARPED_FUNGUS, XMaterial.RESPAWN_ANCHOR});
        IGNORED_BY_PIGLIN_BABIES = new XTag(new XMaterial[]{XMaterial.LEATHER});
        IMPERMEABLE = new XTag(XMaterial.class, new XTag[]{GLASS});
        INFINIBURN_END = new XTag(new XMaterial[]{XMaterial.BEDROCK, XMaterial.NETHERRACK, XMaterial.MAGMA_BLOCK});
        INFINIBURN_NETHER = new XTag(new XMaterial[]{XMaterial.NETHERRACK, XMaterial.MAGMA_BLOCK});
        INFINIBURN_OVERWORLD = new XTag(new XMaterial[]{XMaterial.NETHERRACK, XMaterial.MAGMA_BLOCK});
        INSIDE_STEP_SOUND_BLOCKS = new XTag(new XMaterial[]{XMaterial.SNOW, XMaterial.POWDER_SNOW});
        ITEMS_ARROWS = new XTag(new XMaterial[]{XMaterial.ARROW, XMaterial.SPECTRAL_ARROW, XMaterial.TIPPED_ARROW});
        ITEMS_BEACON_PAYMENT_ITEMS = new XTag(new XMaterial[]{XMaterial.EMERALD, XMaterial.DIAMOND, XMaterial.NETHERITE_INGOT, XMaterial.IRON_INGOT, XMaterial.GOLD_INGOT});
        ITEMS_BOATS = new XTag(new XMaterial[]{XMaterial.OAK_BOAT, XMaterial.ACACIA_BOAT, XMaterial.DARK_OAK_BOAT, XMaterial.BIRCH_BOAT, XMaterial.SPRUCE_BOAT, XMaterial.JUNGLE_BOAT});
        ITEMS_COALS = new XTag(new XMaterial[]{XMaterial.COAL, XMaterial.CHARCOAL});
        ITEMS_CREEPER_DROP_MUSIC_DISCS = new XTag(new XMaterial[]{XMaterial.MUSIC_DISC_BLOCKS, XMaterial.MUSIC_DISC_11, XMaterial.MUSIC_DISC_WAIT, XMaterial.MUSIC_DISC_MELLOHI, XMaterial.MUSIC_DISC_STAL, XMaterial.MUSIC_DISC_WARD, XMaterial.MUSIC_DISC_13, XMaterial.MUSIC_DISC_CAT, XMaterial.MUSIC_DISC_CHIRP, XMaterial.MUSIC_DISC_MALL, XMaterial.MUSIC_DISC_FAR, XMaterial.MUSIC_DISC_STRAD});
        ITEMS_FISHES = new XTag(new XMaterial[]{XMaterial.TROPICAL_FISH, XMaterial.SALMON, XMaterial.PUFFERFISH, XMaterial.COOKED_COD, XMaterial.COD, XMaterial.COOKED_SALMON});
        ITEMS_FURNACE_MATERIALS = new XTag(XMaterial.class, new XTag[0]);
        ITEMS_LECTERN_BOOKS = new XTag(new XMaterial[]{XMaterial.WRITABLE_BOOK, XMaterial.WRITTEN_BOOK});
        ITEMS_STONE_TOOL_MATERIALS = new XTag(new XMaterial[]{XMaterial.COBBLED_DEEPSLATE, XMaterial.BLACKSTONE, XMaterial.COBBLESTONE});
        LEAVES = new XTag(new XMaterial[]{XMaterial.SPRUCE_LEAVES, XMaterial.ACACIA_LEAVES, XMaterial.DARK_OAK_LEAVES, XMaterial.AZALEA_LEAVES, XMaterial.JUNGLE_LEAVES, XMaterial.FLOWERING_AZALEA_LEAVES, XMaterial.BIRCH_LEAVES, XMaterial.OAK_LEAVES});
        NON_WOODEN_STAIRS = new XTag(new XMaterial[]{XMaterial.STONE_BRICK_STAIRS, XMaterial.STONE_STAIRS, XMaterial.POLISHED_BLACKSTONE_BRICK_STAIRS, XMaterial.RED_SANDSTONE_STAIRS, XMaterial.PRISMARINE_STAIRS, XMaterial.GRANITE_STAIRS, XMaterial.WAXED_WEATHERED_CUT_COPPER_STAIRS, XMaterial.POLISHED_DIORITE_STAIRS, XMaterial.WEATHERED_CUT_COPPER_STAIRS, XMaterial.NETHER_BRICK_STAIRS, XMaterial.RED_NETHER_BRICK_STAIRS, XMaterial.PRISMARINE_BRICK_STAIRS, XMaterial.WAXED_CUT_COPPER_STAIRS, XMaterial.DEEPSLATE_TILE_STAIRS, XMaterial.POLISHED_ANDESITE_STAIRS, XMaterial.SMOOTH_RED_SANDSTONE_STAIRS, XMaterial.PURPUR_STAIRS, XMaterial.POLISHED_DEEPSLATE_STAIRS, XMaterial.QUARTZ_STAIRS, XMaterial.MOSSY_COBBLESTONE_STAIRS, XMaterial.BRICK_STAIRS, XMaterial.CUT_COPPER_STAIRS, XMaterial.SANDSTONE_STAIRS, XMaterial.ANDESITE_STAIRS, XMaterial.WAXED_EXPOSED_CUT_COPPER_STAIRS, XMaterial.COBBLED_DEEPSLATE_STAIRS, XMaterial.COBBLESTONE_STAIRS, XMaterial.DEEPSLATE_BRICK_STAIRS, XMaterial.DIORITE_STAIRS, XMaterial.SMOOTH_QUARTZ_STAIRS, XMaterial.EXPOSED_CUT_COPPER_STAIRS, XMaterial.DARK_PRISMARINE_STAIRS, XMaterial.OXIDIZED_CUT_COPPER_STAIRS, XMaterial.POLISHED_BLACKSTONE_STAIRS, XMaterial.POLISHED_GRANITE_STAIRS, XMaterial.MOSSY_STONE_BRICK_STAIRS, XMaterial.END_STONE_BRICK_STAIRS, XMaterial.WAXED_OXIDIZED_CUT_COPPER_STAIRS, XMaterial.SMOOTH_SANDSTONE_STAIRS, XMaterial.BLACKSTONE_STAIRS});
        STAIRS = new XTag(XMaterial.class, new XTag[]{NON_WOODEN_STAIRS, WOODEN_STAIRS});
        NON_WOODEN_SLABS = new XTag(new XMaterial[]{XMaterial.MOSSY_COBBLESTONE_SLAB, XMaterial.EXPOSED_CUT_COPPER_SLAB, XMaterial.SMOOTH_QUARTZ_SLAB, XMaterial.COBBLESTONE_SLAB, XMaterial.POLISHED_BLACKSTONE_SLAB, XMaterial.OXIDIZED_CUT_COPPER_SLAB, XMaterial.POLISHED_ANDESITE_SLAB, XMaterial.RED_SANDSTONE_SLAB, XMaterial.BLACKSTONE_SLAB, XMaterial.STONE_SLAB, XMaterial.SMOOTH_SANDSTONE_SLAB, XMaterial.COBBLED_DEEPSLATE_SLAB, XMaterial.SMOOTH_RED_SANDSTONE_SLAB, XMaterial.POLISHED_DIORITE_SLAB, XMaterial.PRISMARINE_BRICK_SLAB, XMaterial.QUARTZ_SLAB, XMaterial.DIORITE_SLAB, XMaterial.NETHER_BRICK_SLAB, XMaterial.PRISMARINE_SLAB, XMaterial.WAXED_EXPOSED_CUT_COPPER_SLAB, XMaterial.RED_NETHER_BRICK_SLAB, XMaterial.POLISHED_BLACKSTONE_BRICK_SLAB, XMaterial.MOSSY_STONE_BRICK_SLAB, XMaterial.SMOOTH_STONE_SLAB, XMaterial.SANDSTONE_SLAB, XMaterial.WEATHERED_CUT_COPPER_SLAB, XMaterial.DEEPSLATE_BRICK_SLAB, XMaterial.POLISHED_DEEPSLATE_SLAB, XMaterial.GRANITE_SLAB, XMaterial.ANDESITE_SLAB, XMaterial.CUT_COPPER_SLAB, XMaterial.CUT_SANDSTONE_SLAB, XMaterial.END_STONE_BRICK_SLAB, XMaterial.WAXED_OXIDIZED_CUT_COPPER_SLAB, XMaterial.CUT_RED_SANDSTONE_SLAB, XMaterial.PURPUR_SLAB, XMaterial.STONE_BRICK_SLAB, XMaterial.WAXED_CUT_COPPER_SLAB, XMaterial.DEEPSLATE_TILE_SLAB, XMaterial.DARK_PRISMARINE_SLAB, XMaterial.PETRIFIED_OAK_SLAB, XMaterial.WAXED_WEATHERED_CUT_COPPER_SLAB, XMaterial.BRICK_SLAB, XMaterial.POLISHED_GRANITE_SLAB});
        SOUL_FIRE_BASE_BLOCKS = new XTag(new XMaterial[]{XMaterial.SOUL_SOIL, XMaterial.SOUL_SAND});
        SOUL_SPEED_BLOCKS = new XTag(new XMaterial[]{XMaterial.SOUL_SOIL, XMaterial.SOUL_SAND});
        STONE_ORE_REPLACEABLES = new XTag(new XMaterial[]{XMaterial.STONE, XMaterial.DIORITE, XMaterial.ANDESITE, XMaterial.GRANITE});
        STRIDER_WARM_BLOCKS = new XTag(new XMaterial[]{XMaterial.LAVA});
        VALID_SPAWN = new XTag(new XMaterial[]{XMaterial.PODZOL, XMaterial.GRASS_BLOCK});
        STONE_BRICKS = new XTag(new XMaterial[]{XMaterial.CHISELED_STONE_BRICKS, XMaterial.CRACKED_STONE_BRICKS, XMaterial.MOSSY_STONE_BRICKS, XMaterial.STONE_BRICKS});
        SAPLINGS = new XTag(new XMaterial[]{XMaterial.ACACIA_SAPLING, XMaterial.JUNGLE_SAPLING, XMaterial.SPRUCE_SAPLING, XMaterial.DARK_OAK_SAPLING, XMaterial.AZALEA, XMaterial.OAK_SAPLING, XMaterial.FLOWERING_AZALEA, XMaterial.BIRCH_SAPLING});
        WOLVES_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.GRASS_BLOCK, XMaterial.SNOW, XMaterial.SNOW_BLOCK});
        POLAR_BEARS_SPAWNABLE_ON_IN_FROZEN_OCEAN = new XTag(new XMaterial[]{XMaterial.ICE});
        RABBITS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.GRASS_BLOCK, XMaterial.SNOW, XMaterial.SNOW_BLOCK, XMaterial.SAND});
        PIGLIN_FOOD = new XTag(new XMaterial[]{XMaterial.COOKED_PORKCHOP, XMaterial.PORKCHOP});
        PIGLIN_REPELLENTS = new XTag(new XMaterial[]{XMaterial.SOUL_WALL_TORCH, XMaterial.SOUL_TORCH, XMaterial.SOUL_CAMPFIRE, XMaterial.SOUL_LANTERN, XMaterial.SOUL_FIRE});
        REPLACEABLE_PLANTS = new XTag(new XMaterial[]{XMaterial.FERN, XMaterial.GLOW_LICHEN, XMaterial.DEAD_BUSH, XMaterial.PEONY, XMaterial.TALL_GRASS, XMaterial.HANGING_ROOTS, XMaterial.VINE, XMaterial.SUNFLOWER, XMaterial.LARGE_FERN, XMaterial.LILAC, XMaterial.ROSE_BUSH, XMaterial.GRASS});
        SMALL_DRIPLEAF_PLACEABLE = new XTag(new XMaterial[]{XMaterial.CLAY, XMaterial.MOSS_BLOCK});
        NON_FLAMMABLE_WOOD = new XTag(new XMaterial[]{XMaterial.CRIMSON_PLANKS, XMaterial.WARPED_WALL_SIGN, XMaterial.CRIMSON_FENCE_GATE, XMaterial.WARPED_HYPHAE, XMaterial.CRIMSON_HYPHAE, XMaterial.WARPED_STEM, XMaterial.WARPED_TRAPDOOR, XMaterial.STRIPPED_CRIMSON_HYPHAE, XMaterial.CRIMSON_PRESSURE_PLATE, XMaterial.WARPED_STAIRS, XMaterial.CRIMSON_SIGN, XMaterial.CRIMSON_STAIRS, XMaterial.STRIPPED_WARPED_STEM, XMaterial.CRIMSON_FENCE, XMaterial.WARPED_FENCE, XMaterial.CRIMSON_TRAPDOOR, XMaterial.STRIPPED_WARPED_HYPHAE, XMaterial.WARPED_DOOR, XMaterial.WARPED_PRESSURE_PLATE, XMaterial.WARPED_PLANKS, XMaterial.STRIPPED_CRIMSON_STEM, XMaterial.CRIMSON_STEM, XMaterial.CRIMSON_SLAB, XMaterial.CRIMSON_WALL_SIGN, XMaterial.WARPED_FENCE_GATE, XMaterial.WARPED_BUTTON, XMaterial.WARPED_SLAB, XMaterial.CRIMSON_DOOR, XMaterial.CRIMSON_BUTTON, XMaterial.WARPED_SIGN});
        MOOSHROOMS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.MYCELIUM});
        NEEDS_STONE_TOOL = new XTag(new XMaterial[]{XMaterial.OXIDIZED_CUT_COPPER, XMaterial.DEEPSLATE_COPPER_ORE, XMaterial.EXPOSED_CUT_COPPER_SLAB, XMaterial.WAXED_OXIDIZED_CUT_COPPER_SLAB, XMaterial.WAXED_OXIDIZED_CUT_COPPER, XMaterial.OXIDIZED_CUT_COPPER_SLAB, XMaterial.WAXED_WEATHERED_CUT_COPPER, XMaterial.WAXED_WEATHERED_CUT_COPPER_STAIRS, XMaterial.WEATHERED_COPPER, XMaterial.WEATHERED_CUT_COPPER_STAIRS, XMaterial.EXPOSED_CUT_COPPER, XMaterial.DEEPSLATE_LAPIS_ORE, XMaterial.COPPER_ORE, XMaterial.WEATHERED_CUT_COPPER, XMaterial.WAXED_CUT_COPPER_STAIRS, XMaterial.WAXED_EXPOSED_CUT_COPPER, XMaterial.OXIDIZED_COPPER, XMaterial.WAXED_COPPER_BLOCK, XMaterial.RAW_IRON_BLOCK, XMaterial.LAPIS_BLOCK, XMaterial.DEEPSLATE_IRON_ORE, XMaterial.CUT_COPPER_STAIRS, XMaterial.COPPER_BLOCK, XMaterial.WAXED_WEATHERED_CUT_COPPER_SLAB, XMaterial.IRON_BLOCK, XMaterial.WAXED_EXPOSED_CUT_COPPER_STAIRS, XMaterial.RAW_COPPER_BLOCK, XMaterial.LAPIS_ORE, XMaterial.WEATHERED_CUT_COPPER_SLAB, XMaterial.CUT_COPPER_SLAB, XMaterial.IRON_ORE, XMaterial.EXPOSED_COPPER, XMaterial.WAXED_EXPOSED_COPPER, XMaterial.EXPOSED_CUT_COPPER_STAIRS, XMaterial.WAXED_CUT_COPPER_SLAB, XMaterial.WAXED_EXPOSED_CUT_COPPER_SLAB, XMaterial.OXIDIZED_CUT_COPPER_STAIRS, XMaterial.WAXED_OXIDIZED_COPPER, XMaterial.WAXED_CUT_COPPER, XMaterial.WAXED_WEATHERED_COPPER, XMaterial.LIGHTNING_ROD, XMaterial.WAXED_OXIDIZED_CUT_COPPER_STAIRS, XMaterial.CUT_COPPER});
        NEEDS_IRON_TOOL = new XTag(new XMaterial[]{XMaterial.GOLD_ORE, XMaterial.GOLD_BLOCK, XMaterial.REDSTONE_ORE, XMaterial.RAW_GOLD_BLOCK, XMaterial.EMERALD_BLOCK, XMaterial.DIAMOND_BLOCK, XMaterial.DIAMOND_ORE, XMaterial.DEEPSLATE_EMERALD_ORE, XMaterial.DEEPSLATE_GOLD_ORE, XMaterial.EMERALD_ORE, XMaterial.DEEPSLATE_REDSTONE_ORE, XMaterial.DEEPSLATE_DIAMOND_ORE});
        NEEDS_DIAMOND_TOOL = new XTag(new XMaterial[]{XMaterial.OBSIDIAN, XMaterial.NETHERITE_BLOCK, XMaterial.ANCIENT_DEBRIS, XMaterial.RESPAWN_ANCHOR, XMaterial.CRYING_OBSIDIAN});
        MINEABLE_PICKAXE = new XTag(new XMaterial[]{XMaterial.OXIDIZED_CUT_COPPER, XMaterial.GOLD_BLOCK, XMaterial.SMOOTH_SANDSTONE, XMaterial.IRON_DOOR, XMaterial.COBBLESTONE, XMaterial.DRIPSTONE_BLOCK, XMaterial.CHISELED_SANDSTONE, XMaterial.INFESTED_STONE_BRICKS, XMaterial.QUARTZ_BLOCK, XMaterial.COPPER_BLOCK, XMaterial.STONE_BRICKS, XMaterial.CHISELED_POLISHED_BLACKSTONE, XMaterial.DISPENSER, XMaterial.DEEPSLATE_BRICKS, XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE, XMaterial.OBSIDIAN, XMaterial.EXPOSED_CUT_COPPER, XMaterial.SMOOTH_QUARTZ, XMaterial.SMOOTH_RED_SANDSTONE, XMaterial.STONE, XMaterial.INFESTED_COBBLESTONE, XMaterial.WAXED_CUT_COPPER, XMaterial.PRISMARINE, XMaterial.PISTON, XMaterial.CUT_COPPER, XMaterial.CHISELED_QUARTZ_BLOCK, XMaterial.MOSSY_STONE_BRICKS, XMaterial.EMERALD_BLOCK, XMaterial.BELL, XMaterial.AMETHYST_BLOCK, XMaterial.GILDED_BLACKSTONE, XMaterial.CHISELED_NETHER_BRICKS, XMaterial.WAXED_COPPER_BLOCK, XMaterial.IRON_BLOCK, XMaterial.BUDDING_AMETHYST, XMaterial.POLISHED_DEEPSLATE, XMaterial.HOPPER, XMaterial.CUT_RED_SANDSTONE, XMaterial.QUARTZ_BRICKS, XMaterial.CHISELED_STONE_BRICKS, XMaterial.ENDER_CHEST, XMaterial.END_STONE_BRICKS, XMaterial.NETHERRACK, XMaterial.REDSTONE_BLOCK, XMaterial.WAXED_OXIDIZED_CUT_COPPER, XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, XMaterial.WAXED_WEATHERED_CUT_COPPER, XMaterial.CHAIN, XMaterial.MAGMA_BLOCK, XMaterial.STONE_PRESSURE_PLATE, XMaterial.DARK_PRISMARINE, XMaterial.MEDIUM_AMETHYST_BUD, XMaterial.LANTERN, XMaterial.ICE, XMaterial.DIORITE, XMaterial.DROPPER, XMaterial.CRACKED_NETHER_BRICKS, XMaterial.BREWING_STAND, XMaterial.CHISELED_RED_SANDSTONE, XMaterial.CALCITE, XMaterial.CUT_SANDSTONE, XMaterial.POLISHED_BASALT, XMaterial.DEEPSLATE_TILES, XMaterial.QUARTZ_PILLAR, XMaterial.LODESTONE, XMaterial.POLISHED_GRANITE, XMaterial.POLISHED_ANDESITE, XMaterial.OBSERVER, XMaterial.CHISELED_DEEPSLATE, XMaterial.RAW_GOLD_BLOCK, XMaterial.CRACKED_POLISHED_BLACKSTONE_BRICKS, XMaterial.WAXED_EXPOSED_CUT_COPPER, XMaterial.SMALL_AMETHYST_BUD, XMaterial.OXIDIZED_COPPER, XMaterial.POLISHED_BLACKSTONE, XMaterial.RAW_IRON_BLOCK, XMaterial.POLISHED_BLACKSTONE_BRICKS, XMaterial.INFESTED_DEEPSLATE, XMaterial.RAW_COPPER_BLOCK, XMaterial.BLACKSTONE, XMaterial.AMETHYST_CLUSTER, XMaterial.GRINDSTONE, XMaterial.WAXED_EXPOSED_COPPER, XMaterial.RED_SANDSTONE, XMaterial.LIGHTNING_ROD, XMaterial.SOUL_LANTERN, XMaterial.POLISHED_BLACKSTONE_PRESSURE_PLATE, XMaterial.IRON_BARS, XMaterial.PURPUR_BLOCK, XMaterial.FURNACE, XMaterial.CONDUIT, XMaterial.SPAWNER, XMaterial.COAL_BLOCK, XMaterial.BONE_BLOCK, XMaterial.WARPED_NYLIUM, XMaterial.WEATHERED_COPPER, XMaterial.WEATHERED_CUT_COPPER, XMaterial.MOSSY_COBBLESTONE, XMaterial.SMOKER, XMaterial.COBBLED_DEEPSLATE, XMaterial.SMOOTH_BASALT, XMaterial.STONE_BUTTON, XMaterial.NETHER_BRICKS, XMaterial.BRICKS, XMaterial.RED_NETHER_BRICKS, XMaterial.SMOOTH_STONE, XMaterial.ANDESITE, XMaterial.BASALT, XMaterial.TUFF, XMaterial.END_STONE, XMaterial.WAXED_OXIDIZED_COPPER, XMaterial.INFESTED_CHISELED_STONE_BRICKS, XMaterial.PRISMARINE_BRICKS, XMaterial.CRYING_OBSIDIAN, XMaterial.CRACKED_DEEPSLATE_TILES, XMaterial.INFESTED_STONE, XMaterial.IRON_TRAPDOOR, XMaterial.INFESTED_MOSSY_STONE_BRICKS, XMaterial.RESPAWN_ANCHOR, XMaterial.BLUE_ICE, XMaterial.POLISHED_DIORITE, XMaterial.NETHER_BRICK_FENCE, XMaterial.INFESTED_CRACKED_STONE_BRICKS, XMaterial.SANDSTONE, XMaterial.EXPOSED_COPPER, XMaterial.WAXED_WEATHERED_COPPER, XMaterial.CRACKED_DEEPSLATE_BRICKS, XMaterial.LARGE_AMETHYST_BUD, XMaterial.PISTON_HEAD, XMaterial.NETHERITE_BLOCK, XMaterial.PURPUR_PILLAR, XMaterial.GRANITE, XMaterial.STONECUTTER, XMaterial.BLAST_FURNACE, XMaterial.ENCHANTING_TABLE, XMaterial.LAPIS_BLOCK, XMaterial.PACKED_ICE, XMaterial.CRACKED_STONE_BRICKS, XMaterial.DEEPSLATE, XMaterial.CRIMSON_NYLIUM, XMaterial.STICKY_PISTON, XMaterial.DIAMOND_BLOCK, XMaterial.POINTED_DRIPSTONE});
        MINEABLE_PICKAXE.inheritFrom(TERRACOTTA, GLAZED_TERRACOTTA, WALLS, CORALS, SHULKER_BOXES, RAILS, DIAMOND_ORES, GOLD_ORES, IRON_ORES, EMERALD_ORES, COPPER_ORES, ANVIL, CONCRETE, NON_WOODEN_STAIRS, NON_WOODEN_SLABS, CAULDRONS);
        MINEABLE_SHOVEL = new XTag(new XMaterial[]{XMaterial.FARMLAND, XMaterial.DIRT_PATH, XMaterial.SNOW, XMaterial.SNOW_BLOCK, XMaterial.RED_SAND, XMaterial.COARSE_DIRT, XMaterial.SOUL_SAND, XMaterial.GRAVEL, XMaterial.SAND, XMaterial.PODZOL, XMaterial.DIRT, XMaterial.CLAY, XMaterial.ROOTED_DIRT, XMaterial.MYCELIUM, XMaterial.SOUL_SOIL, XMaterial.GRASS_BLOCK});
        MINEABLE_SHOVEL.inheritFrom(CONCRETE_POWDER);
        MINEABLE_HOE = new XTag(new XMaterial[]{XMaterial.FLOWERING_AZALEA_LEAVES, XMaterial.DARK_OAK_LEAVES, XMaterial.SHROOMLIGHT, XMaterial.BIRCH_LEAVES, XMaterial.DRIED_KELP_BLOCK, XMaterial.JUNGLE_LEAVES, XMaterial.OAK_LEAVES, XMaterial.MOSS_CARPET, XMaterial.WET_SPONGE, XMaterial.AZALEA_LEAVES, XMaterial.NETHER_WART_BLOCK, XMaterial.WARPED_WART_BLOCK, XMaterial.SPONGE, XMaterial.SPRUCE_LEAVES, XMaterial.SCULK_SENSOR, XMaterial.HAY_BLOCK, XMaterial.TARGET, XMaterial.ACACIA_LEAVES, XMaterial.MOSS_BLOCK});
        LAVA_POOL_STONE_CANNOT_REPLACE = new XTag(new XMaterial[]{XMaterial.DARK_OAK_LEAVES, XMaterial.STRIPPED_DARK_OAK_WOOD, XMaterial.OAK_WOOD, XMaterial.CRIMSON_HYPHAE, XMaterial.JUNGLE_LEAVES, XMaterial.DARK_OAK_WOOD, XMaterial.STRIPPED_ACACIA_LOG, XMaterial.DARK_OAK_LOG, XMaterial.STRIPPED_DARK_OAK_LOG, XMaterial.AZALEA_LEAVES, XMaterial.SPAWNER, XMaterial.JUNGLE_LOG, XMaterial.SPRUCE_LOG, XMaterial.STRIPPED_CRIMSON_HYPHAE, XMaterial.SPRUCE_LEAVES, XMaterial.STRIPPED_BIRCH_LOG, XMaterial.ACACIA_LOG, XMaterial.STRIPPED_ACACIA_WOOD, XMaterial.CRIMSON_STEM, XMaterial.BIRCH_WOOD, XMaterial.STRIPPED_JUNGLE_WOOD, XMaterial.WARPED_HYPHAE, XMaterial.CHEST, XMaterial.FLOWERING_AZALEA_LEAVES, XMaterial.STRIPPED_OAK_LOG, XMaterial.ACACIA_WOOD, XMaterial.BEDROCK, XMaterial.BIRCH_LEAVES, XMaterial.STRIPPED_CRIMSON_STEM, XMaterial.OAK_LEAVES, XMaterial.STRIPPED_BIRCH_WOOD, XMaterial.STRIPPED_JUNGLE_LOG, XMaterial.WARPED_STEM, XMaterial.END_PORTAL_FRAME, XMaterial.SPRUCE_WOOD, XMaterial.STRIPPED_SPRUCE_LOG, XMaterial.STRIPPED_SPRUCE_WOOD, XMaterial.JUNGLE_WOOD, XMaterial.STRIPPED_OAK_WOOD, XMaterial.STRIPPED_WARPED_STEM, XMaterial.OAK_LOG, XMaterial.ACACIA_LEAVES, XMaterial.STRIPPED_WARPED_HYPHAE, XMaterial.BIRCH_LOG});
        LEATHER_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.LEATHER_HELMET, XMaterial.LEATHER_CHESTPLATE, XMaterial.LEATHER_LEGGINGS, XMaterial.LEATHER_BOOTS});
        IRON_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.IRON_HELMET, XMaterial.IRON_CHESTPLATE, XMaterial.IRON_LEGGINGS, XMaterial.IRON_BOOTS});
        CHAINMAIL_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.CHAINMAIL_HELMET, XMaterial.CHAINMAIL_CHESTPLATE, XMaterial.CHAINMAIL_LEGGINGS, XMaterial.CHAINMAIL_BOOTS});
        GOLDEN_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.GOLDEN_HELMET, XMaterial.GOLDEN_CHESTPLATE, XMaterial.GOLDEN_LEGGINGS, XMaterial.GOLDEN_BOOTS});
        DIAMOND_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.DIAMOND_HELMET, XMaterial.DIAMOND_CHESTPLATE, XMaterial.DIAMOND_LEGGINGS, XMaterial.DIAMOND_BOOTS});
        NETHERITE_ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.NETHERITE_HELMET, XMaterial.NETHERITE_CHESTPLATE, XMaterial.NETHERITE_LEGGINGS, XMaterial.NETHERITE_BOOTS});
        WOODEN_TOOLS = new XTag(new XMaterial[]{XMaterial.WOODEN_PICKAXE, XMaterial.WOODEN_AXE, XMaterial.WOODEN_HOE, XMaterial.WOODEN_SHOVEL, XMaterial.WOODEN_SWORD});
        STONE_TOOLS = new XTag(new XMaterial[]{XMaterial.STONE_PICKAXE, XMaterial.STONE_AXE, XMaterial.STONE_HOE, XMaterial.STONE_SHOVEL, XMaterial.STONE_SWORD});
        IRON_TOOLS = new XTag(new XMaterial[]{XMaterial.IRON_PICKAXE, XMaterial.IRON_AXE, XMaterial.IRON_HOE, XMaterial.IRON_SHOVEL, XMaterial.IRON_SWORD});
        DIAMOND_TOOLS = new XTag(new XMaterial[]{XMaterial.DIAMOND_PICKAXE, XMaterial.DIAMOND_AXE, XMaterial.DIAMOND_HOE, XMaterial.DIAMOND_SHOVEL, XMaterial.DIAMOND_SHOVEL});
        NETHERITE_TOOLS = new XTag(new XMaterial[]{XMaterial.NETHERITE_PICKAXE, XMaterial.NETHERITE_AXE, XMaterial.NETHERITE_HOE, XMaterial.NETHERITE_SHOVEL, XMaterial.NETHERITE_SHOVEL});
        ARMOR_PIECES = new XTag(new XMaterial[]{XMaterial.TURTLE_HELMET});
        ARMOR_PIECES.inheritFrom(LEATHER_ARMOR_PIECES, CHAINMAIL_ARMOR_PIECES, IRON_ARMOR_PIECES, GOLDEN_ARMOR_PIECES, DIAMOND_ARMOR_PIECES, NETHERITE_ARMOR_PIECES);
        AZALEA_GROWS_ON = new XTag(new XMaterial[]{XMaterial.SNOW_BLOCK, XMaterial.POWDER_SNOW});
        AZALEA_GROWS_ON.inheritFrom(TERRACOTTA, SAND, DIRT);
        AZALEA_ROOT_REPLACEABLE = new XTag(new XMaterial[]{XMaterial.CLAY, XMaterial.GRAVEL});
        AZALEA_ROOT_REPLACEABLE.inheritFrom(AZALEA_GROWS_ON, CAVE_VINES, BASE_STONE_OVERWORLD);
        BAMBOO_PLANTABLE_ON = new XTag(new XMaterial[]{XMaterial.GRAVEL, XMaterial.BAMBOO_SAPLING, XMaterial.BAMBOO});
        BAMBOO_PLANTABLE_ON.inheritFrom(DIRT, SAND);
        BEE_GROWABLES = new XTag(new XMaterial[]{XMaterial.SWEET_BERRY_BUSH});
        BEE_GROWABLES.inheritFrom(CROPS, CAVE_VINES);
        BIG_DRIPLEAF_PLACEABLE = new XTag(new XMaterial[]{XMaterial.CLAY, XMaterial.FARMLAND});
        BIG_DRIPLEAF_PLACEABLE.inheritFrom(DIRT);
        BUTTONS = new XTag(new XMaterial[]{XMaterial.STONE_BUTTON, XMaterial.POLISHED_BLACKSTONE_BUTTON});
        BUTTONS.inheritFrom(WOODEN_BUTTONS);
        DRIPSTONE_REPLACEABLE = new XTag(new XMaterial[]{XMaterial.DIRT});
        DRIPSTONE_REPLACEABLE.inheritFrom(BASE_STONE_OVERWORLD);
        ENDERMAN_HOLDABLE = new XTag(new XMaterial[]{XMaterial.TNT, XMaterial.PUMPKIN, XMaterial.CARVED_PUMPKIN, XMaterial.MELON, XMaterial.CRIMSON_FUNGUS, XMaterial.WARPED_FUNGUS, XMaterial.WARPED_ROOTS, XMaterial.CRIMSON_ROOTS, XMaterial.RED_MUSHROOM, XMaterial.BROWN_MUSHROOM, XMaterial.CACTUS, XMaterial.GRAVEL, XMaterial.CLAY});
        ENDERMAN_HOLDABLE.inheritFrom(DIRT, NYLIUM, SAND, SMALL_FLOWERS);
        FLOWERS = new XTag(new XMaterial[]{XMaterial.FLOWERING_AZALEA, XMaterial.FLOWERING_AZALEA_LEAVES});
        FLOWERS.inheritFrom(SMALL_FLOWERS, TALL_FLOWERS);
        GOATS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.GRAVEL, XMaterial.STONE, XMaterial.PACKED_ICE});
        GOATS_SPAWNABLE_ON.inheritFrom(SNOW);
        GUARDED_BY_PIGLINS = new XTag(new XMaterial[]{XMaterial.GOLD_BLOCK, XMaterial.ENDER_CHEST, XMaterial.RAW_GOLD_BLOCK, XMaterial.GILDED_BLACKSTONE, XMaterial.CHEST, XMaterial.BARREL, XMaterial.TRAPPED_CHEST});
        GUARDED_BY_PIGLINS.inheritFrom(SHULKER_BOXES, GOLD_ORES);
        ITEMS_MUSIC_DISCS = new XTag(new XMaterial[]{XMaterial.MUSIC_DISC_OTHERSIDE, XMaterial.MUSIC_DISC_PIGSTEP});
        ITEMS_MUSIC_DISCS.inheritFrom(ITEMS_CREEPER_DROP_MUSIC_DISCS);
        ITEMS_PIGLIN_LOVED = new XTag(new XMaterial[]{XMaterial.GOLD_BLOCK, XMaterial.RAW_GOLD, XMaterial.GLISTERING_MELON_SLICE, XMaterial.GOLDEN_HORSE_ARMOR, XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, XMaterial.GOLDEN_SWORD, XMaterial.GOLDEN_AXE, XMaterial.BELL, XMaterial.ENCHANTED_GOLDEN_APPLE, XMaterial.RAW_GOLD_BLOCK, XMaterial.GILDED_BLACKSTONE, XMaterial.CLOCK, XMaterial.GOLDEN_CARROT, XMaterial.GOLDEN_APPLE, XMaterial.GOLDEN_SHOVEL, XMaterial.GOLDEN_PICKAXE, XMaterial.GOLDEN_HOE, XMaterial.GOLD_INGOT});
        ITEMS_PIGLIN_LOVED.inheritFrom(GOLD_ORES, GOLDEN_ARMOR_PIECES);
        SIGNS = new XTag(XMaterial.class, new XTag[]{WALL_SIGNS, STANDING_SIGNS});
        PRESSURE_PLATES = new XTag(new XMaterial[]{XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE});
        PRESSURE_PLATES.inheritFrom(STONE_PRESSURE_PLATES, WOODEN_PRESSURE_PLATES);
        DRAGON_IMMUNE = new XTag(new XMaterial[]{XMaterial.IRON_BARS, XMaterial.OBSIDIAN, XMaterial.RESPAWN_ANCHOR, XMaterial.END_STONE, XMaterial.CRYING_OBSIDIAN});
        DRAGON_IMMUNE.inheritFrom(WITHER_IMMUNE);
        WALL_POST_OVERRIDE = new XTag(new XMaterial[]{XMaterial.TORCH, XMaterial.TRIPWIRE, XMaterial.REDSTONE_TORCH, XMaterial.SOUL_TORCH});
        WALL_POST_OVERRIDE.inheritFrom(SIGNS, BANNERS, PRESSURE_PLATES);
        UNDERWATER_BONEMEALS = new XTag(new XMaterial[]{XMaterial.SEAGRASS});
        UNDERWATER_BONEMEALS.inheritFrom(CORALS, ALIVE_CORAL_WALL_FANS);
        UNSTABLE_BOTTOM_CENTER = new XTag(XMaterial.class, new XTag[]{FENCE_GATES});
        PREVENT_MOB_SPAWNING_INSIDE = new XTag(XMaterial.class, new XTag[]{RAILS});
        PARROTS_SPAWNABLE_ON = new XTag(new XMaterial[]{XMaterial.AIR, XMaterial.GRASS_BLOCK});
        OCCLUDES_VIBRATION_SIGNALS = new XTag(XMaterial.class, new XTag[]{WOOL});
        LOGS_THAT_BURN = new XTag(XMaterial.class, new XTag[]{ACACIA_LOGS, OAK_LOGS, DARK_OAK_LOGS, SPRUCE_LOGS, JUNGLE_LOGS, BIRCH_LOGS});
        LOGS = new XTag(XMaterial.class, new XTag[]{LOGS_THAT_BURN, CRIMSON_STEMS, WARPED_STEMS});
        PARROTS_SPAWNABLE_ON.inheritFrom(LEAVES, LOGS);
        LUSH_GROUND_REPLACEABLE = new XTag(new XMaterial[]{XMaterial.GRAVEL, XMaterial.SAND, XMaterial.CLAY});
        LUSH_GROUND_REPLACEABLE.inheritFrom(CAVE_VINES, DIRT, BASE_STONE_OVERWORLD);
        TRAPDOORS = new XTag(new XMaterial[]{XMaterial.IRON_TRAPDOOR});
        TRAPDOORS.inheritFrom(WOODEN_TRAPDOORS);
        MUSHROOM_GROW_BLOCK = new XTag(new XMaterial[]{XMaterial.PODZOL, XMaterial.MYCELIUM});
        MUSHROOM_GROW_BLOCK.inheritFrom(NYLIUM);
        MOSS_REPLACEABLE = new XTag(XMaterial.class, new XTag[]{CAVE_VINES, DIRT, BASE_STONE_OVERWORLD});
        ARMOR_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.PROTECTION_EXPLOSIONS, XEnchantment.BINDING_CURSE, XEnchantment.VANISHING_CURSE, XEnchantment.PROTECTION_FIRE, XEnchantment.MENDING, XEnchantment.PROTECTION_PROJECTILE, XEnchantment.PROTECTION_ENVIRONMENTAL, XEnchantment.THORNS, XEnchantment.DURABILITY});
        HELEMT_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.WATER_WORKER, XEnchantment.OXYGEN});
        HELEMT_ENCHANTS.inheritFrom(ARMOR_ENCHANTS);
        CHESTPLATE_ENCHANTS = new XTag(XEnchantment.class, new XTag[]{ARMOR_ENCHANTS});
        LEGGINGS_ENCHANTS = new XTag(XEnchantment.class, new XTag[]{ARMOR_ENCHANTS});
        BOOTS_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.DEPTH_STRIDER, XEnchantment.PROTECTION_FALL, XEnchantment.FROST_WALKER});
        BOOTS_ENCHANTS.inheritFrom(ARMOR_ENCHANTS);
        ELYTRA_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.BINDING_CURSE, XEnchantment.VANISHING_CURSE, XEnchantment.MENDING, XEnchantment.DURABILITY});
        SWORD_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.DAMAGE_ARTHROPODS, XEnchantment.VANISHING_CURSE, XEnchantment.FIRE_ASPECT, XEnchantment.KNOCKBACK, XEnchantment.LOOT_BONUS_MOBS, XEnchantment.MENDING, XEnchantment.DAMAGE_ALL, XEnchantment.DAMAGE_UNDEAD, XEnchantment.SWEEPING_EDGE, XEnchantment.DURABILITY});
        AXE_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.DAMAGE_ARTHROPODS, XEnchantment.VANISHING_CURSE, XEnchantment.DIG_SPEED, XEnchantment.LOOT_BONUS_BLOCKS, XEnchantment.MENDING, XEnchantment.DAMAGE_ALL, XEnchantment.SILK_TOUCH, XEnchantment.DAMAGE_UNDEAD, XEnchantment.DURABILITY});
        HOE_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.DIG_SPEED, XEnchantment.LOOT_BONUS_BLOCKS, XEnchantment.MENDING, XEnchantment.SILK_TOUCH, XEnchantment.DURABILITY});
        PICKAXE_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.DIG_SPEED, XEnchantment.LOOT_BONUS_BLOCKS, XEnchantment.MENDING, XEnchantment.SILK_TOUCH, XEnchantment.DURABILITY});
        SHOVEL_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.DIG_SPEED, XEnchantment.LOOT_BONUS_BLOCKS, XEnchantment.MENDING, XEnchantment.SILK_TOUCH, XEnchantment.DURABILITY});
        SHEARS_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.DIG_SPEED, XEnchantment.MENDING, XEnchantment.DURABILITY});
        BOW_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.ARROW_FIRE, XEnchantment.ARROW_INFINITE, XEnchantment.MENDING, XEnchantment.ARROW_KNOCKBACK, XEnchantment.DURABILITY});
        CROSSBOW_ENCHANTS = new XTag(new XEnchantment[]{XEnchantment.VANISHING_CURSE, XEnchantment.MENDING, XEnchantment.MULTISHOT, XEnchantment.PIERCING, XEnchantment.QUICK_CHARGE, XEnchantment.DURABILITY});
        MINEABLE_AXE = new XTag(new XMaterial[]{XMaterial.COMPOSTER, XMaterial.COCOA, XMaterial.RED_MUSHROOM_BLOCK, XMaterial.CRAFTING_TABLE, XMaterial.TALL_GRASS, XMaterial.BIG_DRIPLEAF_STEM, XMaterial.RED_MUSHROOM, XMaterial.JUKEBOX, XMaterial.WARPED_FUNGUS, XMaterial.DEAD_BUSH, XMaterial.NOTE_BLOCK, XMaterial.CRIMSON_FUNGUS, XMaterial.MUSHROOM_STEM, XMaterial.CHORUS_PLANT, XMaterial.BEE_NEST, XMaterial.BROWN_MUSHROOM_BLOCK, XMaterial.JACK_O_LANTERN, XMaterial.FERN, XMaterial.NETHER_WART, XMaterial.CARTOGRAPHY_TABLE, XMaterial.CHEST, XMaterial.SWEET_BERRY_BUSH, XMaterial.BROWN_MUSHROOM, XMaterial.CARVED_PUMPKIN, XMaterial.SMITHING_TABLE, XMaterial.GLOW_LICHEN, XMaterial.SMALL_DRIPLEAF, XMaterial.LOOM, XMaterial.BEEHIVE, XMaterial.GRASS, XMaterial.HANGING_ROOTS, XMaterial.CHORUS_FLOWER, XMaterial.ATTACHED_PUMPKIN_STEM, XMaterial.BIG_DRIPLEAF, XMaterial.DAYLIGHT_DETECTOR, XMaterial.SPORE_BLOSSOM, XMaterial.LILY_PAD, XMaterial.TRAPPED_CHEST, XMaterial.BARREL, XMaterial.LARGE_FERN, XMaterial.LECTERN, XMaterial.SUGAR_CANE, XMaterial.MELON, XMaterial.ATTACHED_MELON_STEM, XMaterial.PUMPKIN, XMaterial.BAMBOO, XMaterial.FLETCHING_TABLE, XMaterial.BOOKSHELF});
        MINEABLE_AXE.inheritFrom(BANNERS, SIGNS, CAVE_VINES, CROPS, LOGS, WOODEN_STAIRS, WOODEN_SLABS, WOODEN_PRESSURE_PLATES, WOODEN_FENCES, WOODEN_FENCE_GATES, WOODEN_TRAPDOORS, WOODEN_DOORS, WOODEN_BUTTONS, PLANKS, SAPLINGS, CLIMBABLE, CAMPFIRES);
    }
}
