package me.jaskri.Generator;

import com.google.common.base.Preconditions;
import me.jaskri.API.Generator.GeneratorTier;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Generator.TieredGenerator;
import me.jaskri.API.Shop.Item.Item;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MapResourceGenerator implements TieredGenerator {

    private static final Vector I_BELIEVE_I_CAN_FLY = new Vector(0.0, 0.25, 0.0);
    private List<GeneratorTier> tiers;
    private Hologram hologram;
    private ArmorStand animation;
    private BukkitTask dropTask;
    private BukkitTask hologramTask;
    private BukkitTask hologramAnimation;
    private GeneratorTier current;
    private Resource drop;
    private Location loc;
    private ItemStack item;
    private int tier = 1;
    private boolean isDropping;

    public MapResourceGenerator(Resource drop, Location loc, List<GeneratorTier> tiers) {
        Preconditions.checkNotNull(drop, "Resource cannot be null!");
        Preconditions.checkNotNull(loc, "Drop location cannot be null!");
        Preconditions.checkArgument(!tiers.isEmpty(), "Generator tiers cannot be empty");
        this.drop = drop;
        this.loc = loc;
        List<GeneratorTier> result = new ArrayList(tiers.size());
        Iterator var5 = tiers.iterator();

        while(var5.hasNext()) {
            GeneratorTier tier = (GeneratorTier)var5.next();
            if (tier != null) {
                result.add(tier);
            }
        }

        this.tiers = result;
        this.item = new ItemStack(drop.getMaterial());
    }

    public List<GeneratorTier> getTiers() {
        return new ArrayList(this.tiers);
    }

    public Location getDropLocation() {
        return this.loc.clone();
    }

    public void setDropLocation(Location loc) {
        if (loc != null) {
            this.loc = loc.clone();
            if (this.hologram != null) {
                this.hologram.teleport(loc);
            }

        }
    }

    public GeneratorTier getCurrentTier() {
        return (GeneratorTier)this.tiers.get(this.tier - 1);
    }

    public void setCurrentTier(int tier) {
        if (tier >= 1 && tier <= this.tiers.size()) {
            this.upgrade(tier);
        }

    }

    public Resource getDrop() {
        return this.drop;
    }

    public void start() {
        if (!this.isDropping) {
            this.upgrade(this.tier);
            this.hologram = new BedwarsHologram(this.loc.clone().add(0.0, 3.0, 0.0), 0.3);
            this.hologram.addLine("&eSpawns in &c" + this.current.getDropTime() + " &eseconds!");
            this.hologram.addLine(this.current.getDisplayName());
            this.hologram.addLine(this.drop.getColoredName());
            this.hologramTask = (new BukkitRunnable() {
                int time;

                {
                    this.time = MapResourceGenerator.this.current.getDropTime();
                }

                public void run() {
                    if (this.time == 0) {
                        this.time = MapResourceGenerator.this.current.getDropTime();
                    }

                    MapResourceGenerator.this.hologram.setText(0, "&eSpawns in &c" + this.time-- + " &eseconds!");
                }
            }).runTaskTimerAsynchronously(Bedwars.getInstance(), (long)this.tier, 20L);
            this.animation = (ArmorStand)this.loc.getWorld().spawnEntity(this.loc.clone().add(0.0, 2.0, 0.0), EntityType.ARMOR_STAND);
            this.animation.setHelmet(new ItemStack(this.drop.getBlock()));
            this.animation.setGravity(false);
            this.animation.setVisible(false);
            this.hologramAnimation = (new BukkitRunnable() {
                private Location loc;
                private float toAdd;
                private float yaw;
                private int count;
                private boolean up;

                {
                    this.loc = MapResourceGenerator.this.animation.getLocation();
                    this.toAdd = 5.95F;
                    this.yaw = 0.0F;
                    this.count = 0;
                    this.up = false;
                }

                public void run() {
                    if (this.up) {
                        this.loc.setY(this.loc.getY() + 0.025);
                        this.yaw += this.toAdd + (float)this.count;
                    } else {
                        this.loc.setY(this.loc.getY() - 0.025);
                        this.yaw -= this.toAdd + (float)this.count;
                    }

                    if (this.count++ > 20) {
                        this.count = 0;
                        this.up = !this.up;
                    }

                    this.loc.setYaw(this.yaw);
                    MapResourceGenerator.this.animation.teleport(this.loc);
                }
            }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 1L);
            this.isDropping = true;
        }
    }

    private void upgrade(int tier) {
        if (tier != this.tiers.size()) {
            this.current = (GeneratorTier)this.tiers.get(tier - 1);
            this.tier = tier;
            if (this.hologram != null) {
                this.hologram.setText(1, this.current.getDisplayName());
            }

            if (this.dropTask != null) {
                this.dropTask.cancel();
            }

            this.dropTask = (new BukkitRunnable() {
                public void run() {
                    Collection<Entity> nearby = MapResourceGenerator.this.loc.getWorld().getNearbyEntities(MapResourceGenerator.this.loc, 2.5, 2.5, 2.5);
                    Iterator var2 = nearby.iterator();

                    while(var2.hasNext()) {
                        Entity entity = (Entity)var2.next();
                        if (entity.getType() == EntityType.DROPPED_ITEM) {
                            ItemStack dropped = ((org.bukkit.entity.Item)entity).getItemStack();
                            if (dropped.getType() == MapResourceGenerator.this.drop.getMaterial()) {
                                if (dropped.getAmount() >= MapResourceGenerator.this.current.getDropLimit()) {
                                    return;
                                }

                                MapResourceGenerator.this.dropItem(MapResourceGenerator.this.item);
                                return;
                            }
                        }
                    }

                    MapResourceGenerator.this.dropItem(MapResourceGenerator.this.item);
                }
            }).runTaskTimer(Bedwars.getInstance(), 0L, (long)(this.current.getDropTime() * 20));
        }
    }

    private void dropItem(ItemStack drop) {
        Item item = this.loc.getWorld().dropItem(this.loc, drop);
        item.setVelocity(I_BELIEVE_I_CAN_FLY);
    }

    public void stop() {
        if (this.isDropping) {
            this.dropTask.cancel();
            this.hologramTask.cancel();
            this.hologramAnimation.cancel();
            this.hologram.remove();
            this.animation.remove();
            this.isDropping = false;
        }
    }

    public boolean isDropping() {
        return this.isDropping;
    }
}
