package me.jaskri.API.Shop.Item;

import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

public interface Buyable {

    ItemStack getDisplayItem(GamePlayer var1);

    boolean onBuy(GamePlayer var1);
}
