package me.jaskri.bedwars.API.PACKAGE.Upgrade.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Shop.Item.Buyable;
import me.jaskri.bedwars.API.PACKAGE.Shop.Item.ItemDescription;
import me.jaskri.bedwars.API.PACKAGE.Trap.Trap;

public interface TrapItem extends Buyable {

    String getName();

    Trap getTrap();

    ItemDescription getDescription();
}
