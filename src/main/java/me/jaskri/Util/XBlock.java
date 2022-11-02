package me.jaskri.Util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wood;
import org.bukkit.material.Wool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class XBlock {

    public static final Set<XMaterial> CROPS;
    public static final Set<XMaterial> DANGEROUS;
    public static final byte CAKE_SLICES = 6;
    private static final boolean ISFLAT;
    private static final Map<XMaterial, XMaterial> ITEM_TO_BLOCK;

    private XBlock() {
    }

    public static boolean isLit(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Lightable)) {
                return false;
            } else {
                Lightable lightable = (Lightable)block.getBlockData();
                return lightable.isLit();
            }
        } else {
            return isMaterial(block, XBlock.BlockMaterial.REDSTONE_LAMP_ON, XBlock.BlockMaterial.REDSTONE_TORCH_ON, XBlock.BlockMaterial.BURNING_FURNACE);
        }
    }

    public static boolean isContainer(@Nullable Block block) {
        return block != null && block.getState() instanceof InventoryHolder;
    }

    public static void setLit(Block block, boolean lit) {
        if (ISFLAT) {
            if (block.getBlockData() instanceof Lightable) {
                BlockData data = block.getBlockData();
                Lightable lightable = (Lightable)data;
                lightable.setLit(lit);
                block.setBlockData(data, false);
            }
        } else {
            String name = block.getType().name();
            if (name.endsWith("FURNACE")) {
                block.setType(XBlock.BlockMaterial.BURNING_FURNACE.material);
            } else if (name.startsWith("REDSTONE_LAMP")) {
                block.setType(XBlock.BlockMaterial.REDSTONE_LAMP_ON.material);
            } else {
                block.setType(XBlock.BlockMaterial.REDSTONE_TORCH_ON.material);
            }

        }
    }

    public static boolean isCrop(XMaterial material) {
        return CROPS.contains(material);
    }

    public static boolean isDangerous(XMaterial material) {
        return DANGEROUS.contains(material);
    }

    public static DyeColor getColor(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Colorable)) {
                return null;
            } else {
                Colorable colorable = (Colorable)block.getBlockData();
                return colorable.getColor();
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (data instanceof Wool) {
                Wool wool = (Wool)data;
                return wool.getColor();
            } else {
                return null;
            }
        }
    }

    public static boolean isCake(@Nullable Material material) {
        return material == Material.CAKE || material == XBlock.BlockMaterial.CAKE_BLOCK.material;
    }

    public static boolean isWheat(@Nullable Material material) {
        return material == Material.WHEAT || material == XBlock.BlockMaterial.CROPS.material;
    }

    public static boolean isSugarCane(@Nullable Material material) {
        return material == Material.SUGAR_CANE || material == XBlock.BlockMaterial.SUGAR_CANE_BLOCK.material;
    }

    public static boolean isBeetroot(@Nullable Material material) {
        return material == Material.BEETROOT || material == Material.BEETROOTS || material == XBlock.BlockMaterial.BEETROOT_BLOCK.material;
    }

    public static boolean isNetherWart(@Nullable Material material) {
        return material == Material.NETHER_WART || material == XBlock.BlockMaterial.NETHER_WARTS.material;
    }

    public static boolean isCarrot(@Nullable Material material) {
        return material == Material.CARROT || material == Material.CARROTS;
    }

    public static boolean isMelon(@Nullable Material material) {
        return material == Material.MELON || material == Material.MELON_SLICE || material == XBlock.BlockMaterial.MELON_BLOCK.material;
    }

    public static boolean isPotato(@Nullable Material material) {
        return material == Material.POTATO || material == Material.POTATOES;
    }

    public static BlockFace getDirection(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Directional)) {
                return BlockFace.SELF;
            } else {
                Directional direction = (Directional)block.getBlockData();
                return direction.getFacing();
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            return data instanceof org.bukkit.material.Directional ? ((org.bukkit.material.Directional)data).getFacing() : BlockFace.SELF;
        }
    }

    public static boolean setDirection(Block block, BlockFace facing) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Directional)) {
                return false;
            } else {
                BlockData data = block.getBlockData();
                Directional direction = (Directional)data;
                direction.setFacing(facing);
                block.setBlockData(data, false);
                return true;
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (data instanceof org.bukkit.material.Directional) {
                ((org.bukkit.material.Directional)data).setFacingDirection(facing);
                state.update(true);
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean setType(@Nonnull Block block, @Nullable XMaterial material, boolean applyPhysics) {
        Objects.requireNonNull(block, "Cannot set type of null block");
        if (material == null) {
            material = XMaterial.AIR;
        }

        XMaterial smartConversion = (XMaterial)ITEM_TO_BLOCK.get(material);
        if (smartConversion != null) {
            material = smartConversion;
        }

        if (material.parseMaterial() == null) {
            return false;
        } else {
            block.setType(material.parseMaterial());
            if (XMaterial.supports(13)) {
                return false;
            } else {
                String parsedName = material.parseMaterial().name();
                if (parsedName.endsWith("_ITEM")) {
                    String blockName = parsedName.substring(0, parsedName.length() - "_ITEM".length());
                    Material blockMaterial = (Material)Objects.requireNonNull(Material.getMaterial(blockName), () -> {
                        return "Could not find block material for item '" + parsedName + "' as '" + blockName + '\'';
                    });
                    block.setType(blockMaterial);
                } else if (parsedName.contains("CAKE")) {
                    Material blockMaterial = Material.getMaterial("CAKE_BLOCK");
                    block.setType(blockMaterial);
                }

                LegacyMaterial legacyMaterial = XBlock.LegacyMaterial.getMaterial(parsedName);
                if (legacyMaterial == XBlock.LegacyMaterial.BANNER) {
                    block.setType(XBlock.LegacyMaterial.STANDING_BANNER.material);
                }

                LegacyMaterial.Handling handling = legacyMaterial == null ? null : legacyMaterial.handling;
                BlockState state = block.getState();
                boolean update = false;
                if (handling == XBlock.LegacyMaterial.Handling.COLORABLE) {
                    if (state instanceof Banner) {
                        Banner banner = (Banner) state;
                        String xName = material.name();
                        int colorIndex = xName.indexOf(95);
                        String color = xName.substring(0, colorIndex);
                        if (color.equals("LIGHT")) {
                            color = xName.substring(0, "LIGHT_".length() + 4);
                        }

                        banner.setBaseColor(DyeColor.valueOf(color));
                    } else {
                        state.setRawData(material.getData());
                    }

                    update = true;
                } else if (handling == XBlock.LegacyMaterial.Handling.WOOD_SPECIES) {
                    String name = material.name();
                    int firstIndicator = name.indexOf(95);
                    if (firstIndicator < 0) {
                        return false;
                    }

                    TreeSpecies species;
                    switch (name.substring(0, firstIndicator)) {
                        case "OAK":
                            species = TreeSpecies.GENERIC;
                            break;
                        case "DARK":
                            species = TreeSpecies.DARK_OAK;
                            break;
                        case "SPRUCE":
                            species = TreeSpecies.REDWOOD;
                            break;
                        default:
                            try {
                                species = TreeSpecies.valueOf(woodType);
                            } catch (IllegalArgumentException var16) {
                                throw new AssertionError("Unknown material " + legacyMaterial + " for wood species");
                            }
                    }

                    boolean firstType = false;
                    switch (legacyMaterial) {
                        case WOOD:
                        case WOOD_DOUBLE_STEP:
                            state.setRawData(species.getData());
                            update = true;
                            break;
                        case LOG:
                        case LEAVES:
                            firstType = true;
                        case LOG_2:
                        case LEAVES_2:
                            switch (species) {
                                case GENERIC:
                                case REDWOOD:
                                case BIRCH:
                                case JUNGLE:
                                    if (!firstType) {
                                        throw new AssertionError("Invalid tree species " + species + " for block type" + legacyMaterial + ", use block type 2 instead");
                                    }
                                    break;
                                case ACACIA:
                                case DARK_OAK:
                                    if (firstType) {
                                        throw new AssertionError("Invalid tree species " + species + " for block type 2 " + legacyMaterial + ", use block type instead");
                                    }
                            }

                            state.setRawData((byte)(state.getRawData() & 12 | species.getData() & 3));
                            update = true;
                            break;
                        case SAPLING:
                        case WOOD_STEP:
                            state.setRawData((byte)(state.getRawData() & 8 | species.getData()));
                            update = true;
                            break;
                        default:
                            throw new AssertionError("Unknown block type " + legacyMaterial + " for tree species: " + species);
                    }
                } else if (material.getData() != 0) {
                    state.setRawData(material.getData());
                    update = true;
                }

                if (update) {
                    state.update(false, applyPhysics);
                }

                return update;
            }
        }
    }

    public static boolean setType(@Nonnull Block block, @Nullable XMaterial material) {
        return setType(block, material, true);
    }

    public static int getAge(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Ageable)) {
                return 0;
            } else {
                Ageable ageable = (Ageable)block.getBlockData();
                return ageable.getAge();
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            return data.getData();
        }
    }

    public static void setAge(Block block, int age) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Ageable)) {
                return;
            }

            BlockData data = block.getBlockData();
            Ageable ageable = (Ageable)data;
            ageable.setAge(age);
            block.setBlockData(data, false);
        }

        BlockState state = block.getState();
        MaterialData data = state.getData();
        data.setData((byte)age);
        state.update(true);
    }

    public static boolean setColor(Block block, DyeColor color) {
        if (ISFLAT) {
            String type = block.getType().name();
            int index = type.indexOf(95);
            if (index == -1) {
                return false;
            } else {
                String realType = type.substring(index);
                Material material = Material.getMaterial(color.name() + '_' + realType);
                if (material == null) {
                    return false;
                } else {
                    block.setType(material);
                    return true;
                }
            }
        } else {
            BlockState state = block.getState();
            state.setRawData(color.getWoolData());
            state.update(true);
            return false;
        }
    }

    public static boolean setFluidLevel(Block block, int level) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Levelled)) {
                return false;
            } else {
                BlockData data = block.getBlockData();
                Levelled levelled = (Levelled)data;
                levelled.setLevel(level);
                block.setBlockData(data, false);
                return true;
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            data.setData((byte)level);
            state.update(true);
            return false;
        }
    }

    public static int getFluidLevel(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Levelled)) {
                return -1;
            } else {
                Levelled levelled = (Levelled)block.getBlockData();
                return levelled.getLevel();
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            return data.getData();
        }
    }

    public static boolean isWaterStationary(Block block) {
        return ISFLAT ? getFluidLevel(block) < 7 : block.getType() == XBlock.BlockMaterial.STATIONARY_WATER.material;
    }

    public static boolean isWater(Material material) {
        return material == Material.WATER || material == XBlock.BlockMaterial.STATIONARY_WATER.material;
    }

    public static boolean isLava(Material material) {
        return material == Material.LAVA || material == XBlock.BlockMaterial.STATIONARY_LAVA.material;
    }

    public static boolean isOneOf(Block block, Collection<String> blocks) {
        if (blocks != null && !blocks.isEmpty()) {
            String name = block.getType().name();
            XMaterial matched = XMaterial.matchXMaterial(block.getType());
            Iterator var4 = blocks.iterator();

            Optional xMat;
            do {
                label37:
                do {
                    while(true) {
                        while(var4.hasNext()) {
                            String comp = (String)var4.next();
                            String checker = comp.toUpperCase(Locale.ENGLISH);
                            if (!checker.startsWith("CONTAINS:")) {
                                if (!checker.startsWith("REGEX:")) {
                                    xMat = XMaterial.matchXMaterial(comp);
                                    continue label37;
                                }

                                comp = comp.substring(6);
                                if (name.matches(comp)) {
                                    return true;
                                }
                            } else {
                                comp = XMaterial.format(checker.substring(9));
                                if (name.contains(comp)) {
                                    return true;
                                }
                            }
                        }

                        return false;
                    }
                } while(!xMat.isPresent());
            } while(matched != xMat.get() && !isType(block, (XMaterial)xMat.get()));

            return true;
        } else {
            return false;
        }
    }

    public static void setCakeSlices(Block block, int amount) {
        Validate.isTrue(isCake(block.getType()), "Block is not a cake: " + block.getType());
        if (ISFLAT) {
            BlockData data = block.getBlockData();
            Cake cake = (Cake)data;
            int remaining = cake.getMaximumBites() - (cake.getBites() + amount);
            if (remaining > 0) {
                cake.setBites(remaining);
                block.setBlockData(data);
            } else {
                block.breakNaturally();
            }

        } else {
            BlockState state = block.getState();
            org.bukkit.material.Cake cake = (org.bukkit.material.Cake)state.getData();
            if (amount > 0) {
                cake.setSlicesRemaining(amount);
                state.update(true);
            } else {
                block.breakNaturally();
            }

        }
    }

    public static int addCakeSlices(Block block, int slices) {
        Validate.isTrue(isCake(block.getType()), "Block is not a cake: " + block.getType());
        int remaining;
        if (ISFLAT) {
            BlockData data = block.getBlockData();
            Cake cake = (Cake)data;
            remaining = cake.getBites() - slices;
            int remaining = cake.getMaximumBites() - remaining;
            if (remaining > 0) {
                cake.setBites(remaining);
                block.setBlockData(data);
                return remaining;
            } else {
                block.breakNaturally();
                return 0;
            }
        } else {
            BlockState state = block.getState();
            org.bukkit.material.Cake cake = (org.bukkit.material.Cake)state.getData();
            remaining = cake.getSlicesRemaining() + slices;
            if (remaining > 0) {
                cake.setSlicesRemaining(remaining);
                state.update(true);
                return remaining;
            } else {
                block.breakNaturally();
                return 0;
            }
        }
    }

    public static void setEnderPearlOnFrame(Block endPortalFrame, boolean eye) {
        BlockState state = endPortalFrame.getState();
        if (ISFLAT) {
            BlockData data = state.getBlockData();
            EndPortalFrame frame = (EndPortalFrame)data;
            frame.setEye(eye);
            state.setBlockData(data);
        } else {
            state.setRawData((byte)(eye ? 4 : 0));
        }

        state.update(true);
    }

    /** @deprecated */
    @Deprecated
    public static XMaterial getType(Block block) {
        if (ISFLAT) {
            return XMaterial.matchXMaterial(block.getType());
        } else {
            String type = block.getType().name();
            BlockState state = block.getState();
            MaterialData data = state.getData();
            byte dataValue;
            if (data instanceof Wood) {
                TreeSpecies species = ((Wood)data).getSpecies();
                dataValue = species.getData();
            } else if (data instanceof Colorable) {
                DyeColor color = ((Colorable)data).getColor();
                dataValue = color.getDyeData();
            } else {
                dataValue = data.getData();
            }

            return (XMaterial)XMaterial.matchDefinedXMaterial(type, dataValue).orElseThrow(() -> {
                return new IllegalArgumentException("Unsupported material for block " + dataValue + ": " + block.getType().name());
            });
        }
    }

    public static boolean isSimilar(Block block, XMaterial material) {
        return material == XMaterial.matchXMaterial(block.getType()) || isType(block, material);
    }

    public static boolean isType(Block block, XMaterial material) {
        Material mat = block.getType();
        switch (material) {
            case Cake:
                return isCake(mat);
            case NETHER_WART:
                return isNetherWart(mat);
            case isMelon():
            case MELON_SlICE:
                return isMelon(mat);
            case CARROT:
            case CARROTS:
                return isCarrot(mat);
            case POTATO:
            case POTATOES:
                return isPotato(mat);
            case WHEAT:
            case WHEAT_SEEDS:
                return isWheat(mat);
            case BEETROOT:
            case BEETROOT_SEEDS:
            case BEETROOTS:
                return isBeetroot(mat);
            case SUGAR_CANE:
                return isSugarCane(mat);
            case WATER:
                return isWater(mat);
            case LAVA:
                return isLava(mat);
            case AIR:
            case CAVE_AIR:
            case VOID_AIR:
                return isAir(mat);
            default:
                return false;
        }
    }

    public static boolean isAir(@Nullable Material material) {
        if (ISFLAT) {
            switch (material) {
                case AIR:
                case CAVE_AIR:
                case VOID_AIR:
                    return true;
                default:
                    return false;
            }
        } else {
            return material == Material.AIR;
        }
    }

    public static boolean isPowered(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Powerable)) {
                return false;
            } else {
                Powerable powerable = (Powerable)block.getBlockData();
                return powerable.isPowered();
            }
        } else {
            String name = block.getType().name();
            if (name.startsWith("REDSTONE_COMPARATOR")) {
                return block.getType() == XBlock.BlockMaterial.REDSTONE_COMPARATOR_ON.material;
            } else {
                return false;
            }
        }
    }

    public static void setPowered(Block block, boolean powered) {
        if (ISFLAT) {
            if (block.getBlockData() instanceof Powerable) {
                BlockData data = block.getBlockData();
                Powerable powerable = (Powerable)data;
                powerable.setPowered(powered);
                block.setBlockData(data, false);
            }
        } else {
            String name = block.getType().name();
            if (name.startsWith("REDSTONE_COMPARATOR")) {
                block.setType(XBlock.BlockMaterial.REDSTONE_COMPARATOR_ON.material);
            }

        }
    }

    public static boolean isOpen(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Openable)) {
                return false;
            } else {
                Openable openable = (Openable)block.getBlockData();
                return openable.isOpen();
            }
        } else {
            BlockState state = block.getState();
            if (!(state instanceof org.bukkit.material.Openable)) {
                return false;
            } else {
                org.bukkit.material.Openable openable = (org.bukkit.material.Openable)state.getData();
                return openable.isOpen();
            }
        }
    }

    public static void setOpened(Block block, boolean opened) {
        if (ISFLAT) {
            if (block.getBlockData() instanceof Openable) {
                BlockData data = block.getBlockData();
                Openable openable = (Openable)data;
                openable.setOpen(opened);
                block.setBlockData(data, false);
            }
        } else {
            BlockState state = block.getState();
            if (state instanceof org.bukkit.material.Openable) {
                org.bukkit.material.Openable openable = (org.bukkit.material.Openable)state.getData();
                openable.setOpen(opened);
                state.setData((MaterialData)openable);
                state.update();
            }
        }
    }

    public static BlockFace getRotation(Block block) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Rotatable)) {
                return null;
            } else {
                Rotatable rotatable = (Rotatable)block.getBlockData();
                return rotatable.getRotation();
            }
        } else {
            return null;
        }
    }

    public static void setRotation(Block block, BlockFace facing) {
        if (ISFLAT) {
            if (!(block.getBlockData() instanceof Rotatable)) {
                return;
            }

            BlockData data = block.getBlockData();
            Rotatable rotatable = (Rotatable)data;
            rotatable.setRotation(facing);
            block.setBlockData(data, false);
        }

    }

    private static boolean isMaterial(Block block, BlockMaterial... materials) {
        Material type = block.getType();
        BlockMaterial[] var3 = materials;
        int var4 = materials.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            BlockMaterial material = var3[var5];
            if (type == material.material) {
                return true;
            }
        }

        return false;
    }

    static {
        CROPS = Collections.unmodifiableSet(EnumSet.of(XMaterial.CARROT, XMaterial.POTATO, XMaterial.NETHER_WART, XMaterial.WHEAT_SEEDS, XMaterial.PUMPKIN_SEEDS, XMaterial.MELON_SEEDS, XMaterial.BEETROOT_SEEDS, XMaterial.SUGAR_CANE, XMaterial.BAMBOO_SAPLING, XMaterial.CHORUS_PLANT, XMaterial.KELP, XMaterial.SEA_PICKLE, XMaterial.BROWN_MUSHROOM, XMaterial.RED_MUSHROOM));
        DANGEROUS = Collections.unmodifiableSet(EnumSet.of(XMaterial.MAGMA_BLOCK, XMaterial.LAVA, XMaterial.CAMPFIRE, XMaterial.FIRE, XMaterial.SOUL_FIRE));
        ISFLAT = XMaterial.supports(13);
        ITEM_TO_BLOCK = new EnumMap(XMaterial.class);
        ITEM_TO_BLOCK.put(XMaterial.MELON_SLICE, XMaterial.MELON_STEM);
        ITEM_TO_BLOCK.put(XMaterial.MELON_SEEDS, XMaterial.MELON_STEM);
        ITEM_TO_BLOCK.put(XMaterial.CARROT_ON_A_STICK, XMaterial.CARROTS);
        ITEM_TO_BLOCK.put(XMaterial.GOLDEN_CARROT, XMaterial.CARROTS);
        ITEM_TO_BLOCK.put(XMaterial.CARROT, XMaterial.CARROTS);
        ITEM_TO_BLOCK.put(XMaterial.POTATO, XMaterial.POTATOES);
        ITEM_TO_BLOCK.put(XMaterial.BAKED_POTATO, XMaterial.POTATOES);
        ITEM_TO_BLOCK.put(XMaterial.POISONOUS_POTATO, XMaterial.POTATOES);
        ITEM_TO_BLOCK.put(XMaterial.PUMPKIN_SEEDS, XMaterial.PUMPKIN_STEM);
        ITEM_TO_BLOCK.put(XMaterial.PUMPKIN_PIE, XMaterial.PUMPKIN);
    }

    public static enum BlockMaterial {

        CAKE_BLOCK,
        CROPS,
        SUGAR_CANE_BLOCK,
        BEETROOT_BLOCK,
        NETHER_WARTS,
        MELON_BLOCK,
        BURNING_FURNACE,
        STATIONARY_WATER,
        STATIONARY_LAVA,
        REDSTONE_LAMP_ON,
        REDSTONE_LAMP_OFF,
        REDSTONE_TORCH_ON,
        REDSTONE_TORCH_OFF,
        REDSTONE_COMPARATOR_ON,
        REDSTONE_COMPARATOR_OFF;

        @Nullable
        private final Material material = Material.getMaterial(this.name());

        private BlockMaterial() {
        }
    }

    public static enum LegacyMaterial {

        STANDING_BANNER(XBlock.LegacyMaterial.Handling.COLORABLE),
        WALL_BANNER(XBlock.LegacyMaterial.Handling.COLORABLE),
        BANNER(XBlock.LegacyMaterial.Handling.COLORABLE),
        CARPET(XBlock.LegacyMaterial.Handling.COLORABLE),
        WOOL(XBlock.LegacyMaterial.Handling.COLORABLE),
        STAINED_CLAY(XBlock.LegacyMaterial.Handling.COLORABLE),
        STAINED_GLASS(XBlock.LegacyMaterial.Handling.COLORABLE),
        STAINED_GLASS_PANE(XBlock.LegacyMaterial.Handling.COLORABLE),
        THIN_GLASS(XBlock.LegacyMaterial.Handling.COLORABLE),
        WOOD(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        WOOD_STEP(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        WOOD_DOUBLE_STEP(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        LEAVES(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        LEAVES_2(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        LOG(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        LOG_2(XBlock.LegacyMaterial.Handling.WOOD_SPECIES),
        SAPLING(XBlock.LegacyMaterial.Handling.WOOD_SPECIES);

        private static final Map<String, LegacyMaterial> LOOKUP = new HashMap();
        private final Material material = Material.getMaterial(this.name());
        private final Handling handling;

        private LegacyMaterial(Handling handling) {
            this.handling = handling;
        }

        private static LegacyMaterial getMaterial(String name) {
            return (LegacyMaterial)LOOKUP.get(name);
        }

        static {
            LegacyMaterial[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                LegacyMaterial legacyMaterial = var0[var2];
                LOOKUP.put(legacyMaterial.name(), legacyMaterial);
            }

        }
    }

    public static enum Handling {

        COLORABLE,
        WOOD_SPECIES;

        private Handling() {
        }
    }

    public static enum BlockMaterial {

        CAKE_BLOCK,
        CROPS,
        SUGAR_CANE_BLOCK,
        BEETROOT_BLOCK,
        NETHER_WARTS,
        MELON_BLOCK,
        BURNING_FURNACE,
        STATIONARY_WATER,
        STATIONARY_LAVA,
        REDSTONE_LAMP_ON,
        REDSTONE_LAMP_OFF,
        REDSTONE_TORCH_ON,
        REDSTONE_TORCH_OFF,
        REDSTONE_COMPARATOR_ON,
        REDSTONE_COMPARATOR_OFF;

        @Nullable
        private final Material material = Material.getMaterial(this.name());

        private BlockMaterial() {
        }
    }

    public static enum Handling {

        COLORABLE,
        WOOD_SPECIES;

        private Handling() {
        }
    }
}



