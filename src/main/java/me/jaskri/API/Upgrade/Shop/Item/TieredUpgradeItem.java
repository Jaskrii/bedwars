package me.jaskri.API.Upgrade.Shop.Item;

import com.sun.tools.javac.util.List;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Upgrade.TieredUpgrade;

public interface TieredUpgradeItem extends Buyable {

    String getName();

    TieredUpgrade getUpgrade();

    List<TieredUpgradeItemTier> getTiers();

    TieredUpgradeItemTier getTier(int var1);

    ItemDescription getDescription();
}
