package me.jaskri.Shop;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Category;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.Shop.Shop;
import me.jaskri.API.User.User;
import me.jaskri.API.events.Shop.ShopCategoryOpenEvent;
import me.jaskri.API.events.quickbuy.QuickBuyOpenEvent;
import me.jaskri.bedwars.Bedwars;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BedwarsShop implements Shop {

    private static final ItemStack GREEN_SEPARATOR;
    private static final ItemStack GRAY_SEPARATOR;
    private List<Category> categories;

    public BedwarsShop(List<Category> categories) {
        this.categories = new ArrayList();
        Preconditions.checkNotNull(categories, "Shop categories cannot be null!");

        for(int i = 0; i < categories.size(); ++i) {
            Category category = (Category)categories.get(i);
            if (category == null) {
                throw new IllegalArgumentException("Shop category cannot be null!");
            }

            if (this.categories.contains(category)) {
                throw new IllegalStateException("Shop cannot have duplicate categories!");
            }

            this.categories.add(category);
        }

    }

    public BedwarsShop() {
        this(new ArrayList(9));
    }

    public boolean openShop(GamePlayer gp, Category category) {
        return this.openShop(gp, this.categories.indexOf(category));
    }

    public boolean openShop(GamePlayer gp, int index) {
        if (gp != null && this.isValidIndex(index)) {
            Category category = (Category)this.categories.get(index);
            Player player = gp.getPlayer();
            Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, category.getName());
            if (category instanceof me.jaskri.QuickBuy.QuickBuy) {
                User user = Bedwars.getInstance().getUser(player);
                if (user == null) {
                    return false;
                }

                QuickBuy userQB = user.getQuickBuy(gp.getGame().getMode());
                if (userQB == null) {
                    userQB = new me.jaskri.QuickBuy.QuickBuy();
                }

                QuickBuyOpenEvent quickbuyevent = new QuickBuyOpenEvent(gp, (QuickBuy)userQB);
                Bukkit.getPluginManager().callEvent(quickbuyevent);
                if (quickbuyevent.isCancelled()) {
                    return false;
                }

                this.applyItems(gp, inv, (Category)userQB, index);
            } else {
                ShopCategoryOpenEvent shopevent = new ShopCategoryOpenEvent(gp, this, category);
                Bukkit.getPluginManager().callEvent(shopevent);
                if (shopevent.isCancelled()) {
                    return false;
                }

                this.applyItems(gp, inv, category, index);
            }

            player.openInventory(inv);
            return true;
        } else {
            return false;
        }
    }

    public boolean openShop(GamePlayer gp) {
        return this.openShop(gp, 0);
    }

    public List<Category> getCategories() {
        return new ArrayList(this.categories);
    }

    public void setCategories(List<Category> categories) {
        for(int i = 0; i < 9 && i < categories.size(); ++i) {
            categories.set(i, (Category)categories.get(i));
        }

    }

    public Category getCategory(int index) {
        return index >= 0 && index < this.categories.size() ? (Category)this.categories.get(index) : null;
    }

    public boolean addCategory(Category category) {
        return category != null && this.categories.size() < 9 && this.categories.add(category);
    }

    public boolean removeCategory(Category category) {
        return category != null && this.categories.remove(category);
    }

    public Category removeCategory(int index) {
        Category category = this.isValidIndex(index) ? (Category)this.categories.get(index) : null;
        this.removeCategory(category);
        return category;
    }

    public void setCategory(int index, Category category) {
        if (this.isValidIndex(index)) {
            this.categories.set(index, category);
        }

    }

    public boolean contains(Category category) {
        return category != null && this.categories.contains(category);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.categories});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BedwarsShop)) {
            return false;
        } else {
            BedwarsShop other = (BedwarsShop)obj;
            return Objects.equals(this.categories, other.categories);
        }
    }

    private void applyItems(GamePlayer gp, Inventory inv, Category category, int index) {
        for(int i = 0; i < this.categories.size() && i < 9; ++i) {
            Category current = (Category)this.categories.get(i);
            if (current != null) {
                if (i != index) {
                    inv.setItem(i + 9, GRAY_SEPARATOR);
                } else {
                    category.applyItems(inv, gp);
                    inv.setItem(i + 9, GREEN_SEPARATOR);
                }

                inv.setItem(i, current.getDisplayItem());
            }
        }

    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.categories.size();
    }

    static {
        ItemManager separator = new ItemManager(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem());
        separator.setName("§8⬆ §7Categories");
        separator.addToLore("§8⬇ §7Items");
        GRAY_SEPARATOR = separator.getItem();
        GREEN_SEPARATOR = XMaterial.GREEN_STAINED_GLASS_PANE.setType(GRAY_SEPARATOR.clone());
    }
}
