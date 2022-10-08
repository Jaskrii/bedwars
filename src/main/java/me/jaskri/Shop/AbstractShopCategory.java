package me.jaskri.Shop;

import com.google.common.base.Preconditions;
import me.jaskri.API.Shop.Category;
import me.jaskri.API.Shop.Item.Buyable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class AbstractShopCategory implements Category {

    protected Map<Integer, Buyable> items;
    protected String name;
    protected ItemStack display;

    protected AbstractShopCategory(String name, ItemStack display, Map<Integer, Buyable> items) {
        this.items = new HashMap();
        Preconditions.checkNotNull(name, "Category name cannot be null");
        Preconditions.checkNotNull(display, "Category display item cannot be null");
        this.name = name;
        this.display = display;
        this.setItems(items);
    }

    public AbstractShopCategory(String name, ItemStack item) {
        this(name, item, (Map)null);
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getDisplayItem() {
        return this.display.clone();
    }

    public Map<Integer, Buyable> getItems() {
        return new HashMap(this.items);
    }

    public void setItems(Map<Integer, Buyable> items) {
        if (items != null) {
            this.items.clear();
            Iterator var2 = items.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<Integer, Buyable> entry = (Map.Entry)var2.next();
                Buyable item = (Buyable)entry.getValue();
                int slot = (Integer)entry.getKey();
                if (item != null) {
                    this.items.put(ShopUtils.isValidIndex(slot) ? slot : ShopUtils.getValidIndex(slot), item);
                }
            }

        }
    }

    public void addItems(Buyable... items) {
        if (items != null) {
            int nextIndex = 0;

            for(int i = 0; i < 21 && nextIndex < items.length; ++i) {
                int slot = ShopUtils.getValidIndex(i);
                if (!this.items.containsKey(slot)) {
                    Buyable item = items[nextIndex];
                    if (item != null) {
                        this.items.put(slot, item);
                        ++nextIndex;
                    }
                }
            }

        }
    }

    public Buyable getItem(int slot) {
        return slot < 19 ? (Buyable)this.items.get(ShopUtils.getValidIndex(slot)) : (Buyable)this.items.get(slot);
    }

    public void setItem(int slot, Buyable item) {
        this.items.put(slot < 21 ? ShopUtils.getValidIndex(slot) : slot, item);
    }

    public boolean removeItem(Buyable item) {
        if (item == null) {
            return false;
        } else {
            Iterator var2 = this.items.entrySet().iterator();

            Map.Entry entry;
            Buyable buyable;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                entry = (Map.Entry)var2.next();
                buyable = (Buyable)entry.getValue();
            } while(!item.equals(buyable));

            this.items.remove(entry.getKey());
            return true;
        }
    }

    public Buyable removeItem(int slot) {
        return ShopUtils.isValidIndex(slot) ? (Buyable)this.items.remove(slot) : (Buyable)this.items.remove(ShopUtils.getValidIndex(slot));
    }

    public void clear() {
        this.items.clear();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name.toLowerCase(), this.display, this.items});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AbstractShopCategory)) {
            return false;
        } else {
            AbstractShopCategory other = (AbstractShopCategory)obj;
            return this.name.equalsIgnoreCase(other.name) && this.display.equals(other.display) && this.items.equals(other.items);
        }
    }
}
