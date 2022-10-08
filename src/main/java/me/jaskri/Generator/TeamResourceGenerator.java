package me.jaskri.Generator;

import com.google.common.base.Preconditions;
import me.jaskri.API.Generator.DropItem;
import me.jaskri.API.Generator.TeamGenerator;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class TeamResourceGenerator implements TeamGenerator {

    private static final Vector I_BELIEVE_I_CANT_FLY = new Vector(0.0, 0.25, 0.0);
    private Map<DropItem, BukkitTask[]> drop_task = new HashMap();
    private Location loc;
    private boolean isDropping;

    public TeamResourceGenerator(Location loc, Set<DropItem> drops) {
        Preconditions.checkNotNull(loc, "Drop location cannot be null!");
        Preconditions.checkNotNull(drops, "Drops cannot be null!");
        Iterator var3 = drops.iterator();

        while(var3.hasNext()) {
            DropItem item = (DropItem)var3.next();
            if (item == null) {
                throw new IllegalArgumentException("Drop cannot be null!");
            }

            this.drop_task.put(item, (Object)null);
        }

        if (this.drop_task.isEmpty()) {
            throw new IllegalArgumentException("Drops cannot be empty!");
        } else {
            this.loc = loc.clone();
        }
    }

    public TeamResourceGenerator(Location loc, DropItem drop) {
        Preconditions.checkNotNull(loc, "Drop location cannot be null!");
        Preconditions.checkNotNull(drop, "Drop cannot be null!");
        this.drop_task.put(drop, (Object)null);
        this.loc = loc;
    }

    public Location getDropLocation() {
        return this.loc.clone();
    }

    public void setDropLocation(Location loc) {
        if (loc != null) {
            this.loc = loc.clone();
        }

    }

    public void start() {
        if (!this.isDropping) {
            Iterator var1 = this.drop_task.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry<DropItem, BukkitTask[]> entry = (Map.Entry)var1.next();
                entry.setValue(this.startDrop((DropItem)entry.getKey()));
            }

            this.isDropping = true;
        }
    }

    public void stop() {
        if (this.isDropping) {
            Iterator var1 = this.drop_task.values().iterator();

            while(var1.hasNext()) {
                BukkitTask[] tasks = (BukkitTask[])var1.next();
                BukkitTask[] var3 = tasks;
                int var4 = tasks.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    BukkitTask task = var3[var5];
                    task.cancel();
                }
            }

            this.isDropping = false;
        }
    }

    public Set<DropItem> getDrops() {
        return new HashSet(this.drop_task.keySet());
    }

    public boolean addDrop(DropItem drop) {
        if (drop == null) {
            return false;
        } else {
            this.drop_task.put(drop, this.startDrop(drop));
            return true;
        }
    }

    public DropItem getDrop(Material material) {
        Iterator var2 = this.drop_task.keySet().iterator();

        DropItem drop;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            drop = (DropItem)var2.next();
        } while(drop.getType() != material);

        return drop;
    }

    public boolean removeDrop(DropItem item) {
        if (!this.contains(item)) {
            return false;
        } else {
            BukkitTask[] tasks = (BukkitTask[])this.drop_task.remove(item);
            if (tasks == null) {
                return true;
            } else {
                BukkitTask[] var3 = tasks;
                int var4 = tasks.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    BukkitTask task = var3[var5];
                    task.cancel();
                }

                return true;
            }
        }
    }

    public boolean contains(DropItem item) {
        return item != null && this.drop_task.containsKey(item);
    }

    public boolean isDropping() {
        return this.isDropping;
    }

    private BukkitTask[] startDrop(DropItem drop) {
        int drops = drop.getDropsPerMinute();
        if (drops <= 60) {
            return new BukkitTask[]{this.createTask(drop, 0L, (long)(1200 / drops))};
        } else {
            int length = (int)Math.ceil((double)drops / 60.0);
            BukkitTask[] tasks = new BukkitTask[length];

            for(int i = 0; i < length; ++i) {
                if (drops > 60) {
                    drops -= 60;
                }

                tasks[i] = this.createTask(drop, (long)i, (long)(1200 / drops));
            }

            return tasks;
        }
    }

    private BukkitTask createTask(final DropItem drop, long delay, long ticks) {
        return (new BukkitRunnable() {
            private ItemStack item = drop.getItem();
            private World world;

            {
                this.world = TeamResourceGenerator.this.loc.getWorld();
            }

            public void run() {
                Iterator var1 = this.world.getNearbyEntities(TeamResourceGenerator.this.loc, 1.5, 1.5, 1.5).iterator();

                while(var1.hasNext()) {
                    Entity entity = (Entity)var1.next();
                    if (entity.getType() == EntityType.DROPPED_ITEM) {
                        ItemStack dropped = ((Item)entity).getItemStack();
                        if (dropped.getType() == this.item.getType()) {
                            if (dropped.getAmount() >= drop.getDropLimit()) {
                                return;
                            }

                            TeamResourceGenerator.this.dropItem(this.item, TeamResourceGenerator.this.loc);
                            return;
                        }
                    }
                }

                TeamResourceGenerator.this.dropItem(this.item, TeamResourceGenerator.this.loc);
            }
        }).runTaskTimer(Bedwars.getInstance(), delay, ticks);
    }

    private void dropItem(ItemStack item, Location loc) {
        Item e = loc.getWorld().dropItem(loc, item);
        e.setMetadata("bedwars", GamePlayerListener.EMPTY);
        e.setVelocity(I_BELIEVE_I_CANT_FLY);
    }
}
