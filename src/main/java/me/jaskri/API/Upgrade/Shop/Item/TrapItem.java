package me.jaskri.API.Upgrade.Shop.Item;

import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Trap.Trap;

public interface TrapItem extends Buyable {

    String getName();

    Trap getTrap();

    ItemDescription getDescription();
}
