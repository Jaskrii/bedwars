package me.jaskri.API.Shop.Item;

import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

public interface Item extends Buyable{

    String getName();

    ItemDescription getDescription();

    void setDescription(ItemDescription var1);

    ItemCost getCost();

    void setCost(ItemCost var1);

    ItemStack getRawItem(GamePlayer var1);
}
