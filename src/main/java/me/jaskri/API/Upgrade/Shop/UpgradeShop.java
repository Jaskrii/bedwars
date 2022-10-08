package me.jaskri.API.Upgrade.Shop;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Buyable;

import java.util.Map;

public interface UpgradeShop {

    Map<Integer, Buyable> getItems();

    Buyable getItem(int var1);

    boolean addItem(int var1, Buyable var2);

    Buyable removeItem(int var1);

    boolean openShop(GamePlayer var1);

    boolean contains(Buyable var1);
}
