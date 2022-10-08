package me.jaskri.API.Upgrade.Shop.Item;

import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;

public interface UpgradeItem extends Buyable {

    String getName();

    Upgrade getUpgrade();

    ItemCost getCost();

    ItemDescription getDescription();
}
