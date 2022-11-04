package me.jaskri.API.Shop;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Buyable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface Category {

    String getName();

    ItemStack getDisplayItem();

    Map<Integer, Buyable> getItems();

    void setItems(Map<Integer, Buyable> var1);

    void addItems(Buyable... var1);

    Buyable getItem(int var1);

    void setItem(int var1, Buyable var2);

    Buyable removeItem(int var1);

    boolean removeItem(Buyable var1);

    void applyItems(Inventory var1, GamePlayer var2);

    void clear();
}
