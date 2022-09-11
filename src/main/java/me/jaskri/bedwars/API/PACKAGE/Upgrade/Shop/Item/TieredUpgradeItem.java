package me.jaskri.bedwars.API.PACKAGE.Upgrade.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Shop.Item.Buyable;
import me.jaskri.bedwars.API.PACKAGE.Shop.Item.ItemDescription;
import me.jaskri.bedwars.API.PACKAGE.Upgrade.TieredUpgrade;

public interface TieredUpgradeItem extends Buyable {

    String getName();

    TieredUpgrade getUpgrade();

    List<TieredUpgradeItemTier> getTiers();

    TieredUpgradeItemTier getTier(int var1);

    ItemDescription getDescription();
}
