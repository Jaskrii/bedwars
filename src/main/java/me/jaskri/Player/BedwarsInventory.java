package me.jaskri.Player;

import me.jaskri.API.Game.player.GameInventory;
import me.jaskri.API.Shop.Item.TieredItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public class BedwarsInventory implements GameInventory {

    private Set<ItemStack> stacks = new HashSet();
    private Set<TieredItemStack> tiers = new HashSet();

    public BedwarsInventory() {
    }

    public Set<ItemStack> getPermanentItems() {
        return this.stacks;
    }

    public boolean addItem(ItemStack item) {
        return item != null ? this.stacks.add(item) : false;
    }

    public boolean removeItem(ItemStack item) {
        return item != null ? this.stacks.remove(item) : false;
    }

    public Set<TieredItemStack> getTieredItems() {
        return new HashSet(this.tiers);
    }

    public boolean addTieredItem(TieredItemStack item) {
        return item != null ? this.tiers.add(item) : false;
    }

    public boolean removeTieredItem(TieredItemStack item) {
        return item != null ? this.tiers.remove(item) : false;
    }

    public boolean contains(ItemStack item) {
        if (item == null) {
            return false;
        } else {
            return this.stacks.contains(item) || this.tiersContain(item);
        }
    }

    public boolean contains(ItemStack item, Predicate<ItemStack> predicate) {
        if (item == null) {
            return false;
        } else {
            Iterator var3 = this.stacks.iterator();

            ItemStack itemstack;
            do {
                if (!var3.hasNext()) {
                    return this.tiersContain(item);
                }

                itemstack = (ItemStack)var3.next();
            } while(!predicate.test(itemstack));

            return true;
        }
    }

    private boolean tiersContain(ItemStack item) {
        Iterator var2 = this.tiers.iterator();

        TieredItemStack tiered;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            tiered = (TieredItemStack)var2.next();
        } while(!tiered.contains(item));

        return true;
    }

    public boolean contains(TieredItemStack item) {
        return item != null ? this.tiers.contains(item) : false;
    }

    public void clear() {
        this.stacks.clear();
        this.tiers.clear();
    }
}
