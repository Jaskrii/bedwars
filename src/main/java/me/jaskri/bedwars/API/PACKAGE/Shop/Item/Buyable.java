package me.jaskri.bedwars.API.PACKAGE.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

public interface Buyable {

    ItemStack getDisplayItem(GamePlayer var1);

    boolean onBuy(GamePlayer var1);
}
