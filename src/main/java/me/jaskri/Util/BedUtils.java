package me.jaskri.Util;

import me.jaskri.API.Team.Team;
import me.jaskri.API.arena.Arena;
import me.jaskri.API.arena.BedwarsBed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import sun.tools.jconsole.Version;

import java.util.Iterator;

public class BedUtils {

    public BedUtils() {
    }

    public static Block getOtherBedPart(Block block) {
        if (Version.getVersion().isNewAPI()) {
            BlockData data = block.getBlockData();
            if (!(data instanceof Bed)) {
                return null;
            } else {
                Bed bed = (Bed)data;
                return block.getRelative(bed.getPart() == Bed.Part.HEAD ? bed.getFacing().getOppositeFace() : bed.getFacing());
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (!(data instanceof org.bukkit.material.Bed)) {
                return null;
            } else {
                org.bukkit.material.Bed bed = (org.bukkit.material.Bed)data;
                return block.getRelative(bed.isHeadOfBed() ? bed.getFacing().getOppositeFace() : bed.getFacing());
            }
        }
    }

    public static BedwarsBed getArenaBed(Arena arena, Block block) {
        Location loc = block.getLocation();
        Iterator var3 = arena.getTeams().iterator();

        BedwarsBed bed;
        do {
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                Team team = (Team)var3.next();
                bed = arena.getTeamBed(team);
            } while(bed == null);
        } while(!loc.equals(bed.getHead().getLocation()) && !loc.equals(bed.getFoot().getLocation()));

        return bed;
    }

    public static void breakBed(BedwarsBed bed) {
        breakBed(bed.getFoot());
    }

    public static void breakBed(Block block) {
        if (Version.getVersion().isNewAPI()) {
            if (!(block.getBlockData() instanceof Bed)) {
                return;
            }

            Bed bed = (Bed)block.getBlockData();
            breakBed(block, bed.getFacing(), bed.getPart() == Bed.Part.HEAD);
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (!(data instanceof org.bukkit.material.Bed)) {
                return;
            }

            org.bukkit.material.Bed bed = (org.bukkit.material.Bed)data;
            breakBed(block, bed.getFacing(), bed.isHeadOfBed());
        }

    }

    private static void breakBed(Block block, BlockFace facing, boolean head) {
        if (!head) {
            block.setType(Material.AIR);
        } else {
            block.getRelative(facing.getOppositeFace()).setType(Material.AIR);
        }

    }

    public static boolean isBedHead(Block block) {
        if (Version.getVersion().isNewAPI()) {
            if (!(block.getBlockData() instanceof Bed)) {
                return false;
            } else {
                Bed bed = (Bed)block.getBlockData();
                return bed.getPart() == Bed.Part.HEAD;
            }
        } else {
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (!(data instanceof org.bukkit.material.Bed)) {
                return false;
            } else {
                org.bukkit.material.Bed bed = (org.bukkit.material.Bed)data;
                return bed.isHeadOfBed();
            }
        }
    }

    public static boolean isBedFoot(Block block) {
        return !isBedHead(block);
    }

    private static void setBedData(Block block, Material material, BlockFace face, Bed.Part part) {
        block.setBlockData(Bukkit.createBlockData(material, (data) -> {
            ((Bed)data).setFacing(face);
            ((Bed)data).setPart(part);
        }));
    }

    public static void placeBed(Block foot, BlockFace facing, Material material) {
        if (Version.getVersion().isNewAPI()) {
            setBedData(foot.getRelative(facing), material, facing, Bed.Part.HEAD);
            setBedData(foot, material, facing, Bed.Part.FOOT);
        } else {
            BlockState bedFoot = foot.getState();
            BlockState bedHead = foot.getRelative(facing).getState();
            bedFoot.setType(material);
            bedHead.setType(material);
            bedFoot.setRawData((byte)0);
            bedHead.setRawData((byte)8);
            setDirection(bedHead, facing);
            setDirection(bedFoot, facing);
            bedFoot.update(true, false);
            bedHead.update(true, false);
        }
    }

    private static void setDirection(BlockState state, BlockFace facing) {
        Directional directional = (Directional)state.getData();
        directional.setFacingDirection(facing);
    }

    public static boolean isBed(Block part1, Block part2) {
        return isBed(part1) && isBed(part2);
    }

    public static boolean isBed(Block block) {
        return isBed(block.getType());
    }

    public static boolean isBed(BedwarsBed bed) {
        return isBed(bed.getHead().getType()) && isBed(bed.getFoot().getType());
    }

    public static boolean isBed(Material type) {
        if (!Version.getVersion().isNewAPI()) {
            return type == Material.getMaterial("BED_BLOCK");
        } else {
            switch (type) {
                case RED_BED:
                case BLUE_BED:
                case GREEN_BED:
                case YELLOW_BED:
                case CYAN_BED:
                case WHITE_BED:
                case PINK_BED:
                case GRAY_BED:
                case BLACK_BED:
                case BROWN_BED:
                case LIGHT_BLUE_BED:
                case LIGHT_GRAY_BED:
                case LIME_BED:
                case MAGENTA_BED:
                case ORANGE_BED:
                case PURPLE_BED:
                    return true;
                default:
                    return false;
            }
        }
    }
}
