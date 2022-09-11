package me.jaskri.bedwars.API.PACKAGE.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;

import java.util.List;

public interface TieredItem extends Buyable{

    List<Item> getTiers();

    Item getTier(int var1);

    TieredItemStack getPlayerTier(GamePlayer var1);

    boolean contains(Item var1);
}
