package me.jaskri.API.Shop.Item;

import me.jaskri.API.Game.player.GamePlayer;

import java.util.List;

public interface TieredItem extends Buyable{

    List<Item> getTiers();

    Item getTier(int var1);

    TieredItemStack getPlayerTier(GamePlayer var1);

    boolean contains(Item var1);
}
