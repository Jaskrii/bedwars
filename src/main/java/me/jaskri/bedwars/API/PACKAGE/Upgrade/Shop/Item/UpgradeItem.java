package me.jaskri.bedwars.API.PACKAGE.Upgrade.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Shop.Item.Buyable;
import me.jaskri.bedwars.API.PACKAGE.Shop.Item.ItemCost;
import me.jaskri.bedwars.API.PACKAGE.Shop.Item.ItemDescription;

public interface UpgradeItem extends Buyable {

    String getName();

    Upgrade getUpgrade();

    ItemCost getCost();

    ItemDescription getDescription();
}
