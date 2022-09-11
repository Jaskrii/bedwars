package me.jaskri.bedwars.API.PACKAGE.Upgrade.Shop;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;
import me.jaskri.bedwars.API.PACKAGE.Shop.Item.Buyable;

import java.util.Map;

public interface UpgradeShop {

    Map<Integer, Buyable> getItems();

    Buyable getItem(int var1);

    boolean addItem(int var1, Buyable var2);

    Buyable removeItem(int var1);

    boolean openShop(GamePlayer var1);

    boolean contains(Buyable var1);
}
