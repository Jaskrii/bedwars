package me.jaskri.Shop.Item;

import jdk.vm.ci.meta.Value;
import me.jaskri.API.Game.player.ArmorType;
import me.jaskri.API.Shop.Item.Item;
import me.jaskri.API.Shop.Item.TieredItem;
import org.bukkit.potion.Potion;

import javax.naming.Name;
import java.beans.Customizer;
import java.util.HashMap;
import java.util.Map;

public class ShopItemType {

    Item,
    ArmorType,
    Potion,
    TieredItem,
    Customizer;

    private static final Map<String, ShopItemType> BY_NAME = new HashMap(5);

    private ShopItemType() {
    }

    public static ShopItemType fromString(String string) {
        return string != null ? (ShopItemType)BY_NAME.get(string.toLowerCase()) : null;
    }

    static {
        ShopItemType[] var0 = Value();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            ShopItemType type = var0[var2];
            BY_NAME.put(type.toString(Name).toLowerCase(), type);
        }

    }
}
