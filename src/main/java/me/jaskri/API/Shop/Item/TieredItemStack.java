package me.jaskri.API.Shop.Item;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TieredItemStack {

    private List<ItemStack> tiers;
    private int current = 0;

    public TieredItemStack(List<ItemStack> tiers) {
        Preconditions.checkNotNull(tiers, "Tiers cannot be null");
        List<ItemStack> result = new ArrayList<>(tiers.size());
        Iterator var3 = tiers.iterator();

        while(var3.hasNext()) {
            ItemStack item = (ItemStack)var3.next();
            if (item != null) {
                result.add(item);
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Tiers cannot be empty");
        } else {
            this.tiers = result;
        }
    }

    public List<ItemStack> getTiers() {
        return new ArrayList(this.tiers);
    }

    public ItemStack getTier(int tier) {
        return this.isValidTier(tier) ? (ItemStack)this.tiers.get(tier - 1) : null;
    }

    public ItemStack next() {
        return this.isValidIndex(this.current) ? (ItemStack)this.tiers.get(this.current) : null;
    }

    public int getNextTier() {
        int result = this.current + 1;
        return this.isValidTier(result) ? result : this.tiers.size();
    }

    public boolean hasNext() {
        return this.isValidTier(this.current + 1);
    }

    public ItemStack current() {
        int result = this.current - 1;
        return this.isValidIndex(result) ? (ItemStack)this.tiers.get(result) : null;
    }

    public int getCurrentTier() {
        return this.current;
    }

    public void setCurrentTier(int tier) {
        if (tier >= 0 && tier <= this.tiers.size()) {
            this.current = tier;
        }

    }

    public ItemStack previous() {
        int result = this.current - 2;
        return this.isValidIndex(result) ? (ItemStack)this.tiers.get(result) : null;
    }

    public int getPreviousTier() {
        int result = this.current - 1;
        return this.isValidTier(result) ? result : 1;
    }

    public boolean hasPrevious() {
        return this.isValidTier(this.current - 1);
    }

    public int size() {
        return this.tiers.size();
    }

    public int getMaximumTier() {
        return this.tiers.size();
    }

    public boolean contains(ItemStack item) {
        return item != null ? this.tiers.contains(item) : false;
    }

    public boolean contains(ItemStack item, Predicate<ItemStack> predicate) {
        if (item == null) {
            return false;
        } else if (predicate == null) {
            return this.tiers.contains(item);
        } else {
            Iterator var3 = this.tiers.iterator();

            ItemStack tier;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                tier = (ItemStack)var3.next();
            } while(!predicate.test(tier));

            return true;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.tiers});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof TieredItemStack)) {
            return false;
        } else {
            TieredItemStack other = (TieredItemStack)obj;
            return Objects.equals(this.tiers, other.tiers);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.tiers.size();
    }

    private boolean isValidTier(int tier) {
        return tier >= 1 && tier <= this.tiers.size();
    }
}
