package me.jaskri.Hologram;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class BedwarsHologram implements Hologram{

    private static final Set<ArmorStand> holograms = new HashSet();
    private final List<ArmorStand> lines;
    private final Location loc;
    private final double height;

    public BedwarsHologram(Location loc, double heightBetweenLines) {
        this.lines = new ArrayList();
        Preconditions.checkNotNull(loc, "Location cannot be null");
        this.loc = loc;
        this.height = heightBetweenLines;
    }

    public BedwarsHologram(Location loc) {
        this(loc, 0.4);
    }

    public String getText(int line) {
        return this.isValidIndex(line) ? ((ArmorStand)this.lines.get(line)).getCustomName() : null;
    }

    public void setText(int line, String text) {
        if (this.isValidIndex(line)) {
            ((ArmorStand)this.lines.get(line)).setCustomName(ChatUtils.format(text));
        }

    }

    public void addLine(String text) {
        this.createLine(this.loc.clone().add(0.0, (double)this.lines.size() * this.height, 0.0), text);
    }

    public void removeLine(int line) {
        if (this.isValidIndex(line)) {
            Entity entity = (Entity)this.lines.remove(line);
            holograms.remove(entity);
            Location teleportTo = entity.getLocation().clone();

            for(int i = line; line < this.lines.size(); ++line) {
                ((ArmorStand)this.lines.get(i)).teleport(teleportTo.add(0.0, -this.height, 0.0));
            }

        }
    }

    public List<String> getLines() {
        List<String> result = new ArrayList(this.lines.size());
        Iterator var2 = this.lines.iterator();

        while(var2.hasNext()) {
            ArmorStand line = (ArmorStand)var2.next();
            result.add(line.getCustomName());
        }

        return result;
    }

    public void setLines(List<String> lines) {
        if (lines != null) {
            Location teleportTo = this.loc.clone();

            for(int i = 0; i < lines.size(); ++i) {
                if (this.isValidIndex(i)) {
                    ((ArmorStand)this.lines.get(i)).setCustomName((String)lines.get(i));
                } else {
                    this.createLine(teleportTo.add(0.0, this.height, 0.0), (String)lines.get(i));
                }
            }

        }
    }

    public void setVisible(int line, boolean visible) {
        if (this.isValidIndex(line)) {
            ((ArmorStand)this.lines.get(line)).setCustomNameVisible(visible);
        }

    }

    public boolean isVisible(int line) {
        return this.isValidIndex(line) && ((ArmorStand)this.lines.get(line)).isCustomNameVisible();
    }

    public void remove() {
        Iterator var1 = this.lines.iterator();

        while(var1.hasNext()) {
            ArmorStand line = (ArmorStand)var1.next();
            holograms.remove(line);
            line.remove();
        }

        this.lines.clear();
    }

    public void teleport(Location loc) {
        if (loc != null) {
            Location teleportTo = loc.clone();
            Iterator var3 = this.lines.iterator();

            while(var3.hasNext()) {
                ArmorStand as = (ArmorStand)var3.next();
                as.teleport(teleportTo.add(0.0, this.height, 0.0));
            }

        }
    }

    public int size() {
        return this.lines.size();
    }

    private void createLine(Location loc, String text) {
        ArmorStand as = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setCanPickupItems(false);
        as.setCustomNameVisible(true);
        as.setCustomName(ChatUtils.format(text));
        as.setVisible(false);
        as.setGravity(false);
        as.setSmall(true);
        this.lines.add(as);
        holograms.add(as);
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.lines.size();
    }

    public static boolean isHologram(Entity entity) {
        return holograms.contains(entity);
    }
}
