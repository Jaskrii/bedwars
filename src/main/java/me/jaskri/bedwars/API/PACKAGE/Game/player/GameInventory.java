package me.jaskri.bedwars.API.PACKAGE.Game.player;

import me.jaskri.bedwars.API.PACKAGE.Shop.Item.TieredItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.function.Predicate;

public interface GameInventory {

    Set<ItemStack> getPermanentItems();

    boolean addItem(ItemStack var1);

    boolean removeItem(ItemStack var1);

    Set<TieredItemStack> getTieredItems();

    boolean addTieredItem(TieredItemStack var1);

    boolean removeTieredItem(TieredItemStack var1);

    boolean contains(ItemStack var1);

    boolean contains(ItemStack var1, Predicate<ItemStack> var2);

    boolean contains(TieredItemStack var1);

    void clear();
}
