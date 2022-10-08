package me.jaskri.Listener;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Shop.Category;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.Shop.Shop;
import me.jaskri.Game.AbstractGame;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import java.util.Iterator;

public class QuickBuyListener implements Listener {

    public QuickBuyListener() {
    }

    @EventHandler
    public void onQuickBuyEdit(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.isShiftClick()) {
            Player player = (Player) event.getWhoClicked();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && !game.isSpectator(player)) {
                Shop shop = Bedwars.getInstance().getShopConfig().getShop(game.getMode());
                if (shop != null) {
                    Category current = this.getCurrentOpenedCategory(shop, event.getView());
                    if (current instanceof QuickBuy) {
                        event.setCancelled(true);
                        if (event.getCurrentItem() != null) {
                            ;
                        }
                    }
                }
            }
        }
    }

    private Category getCurrentOpenedCategory(Shop shop, InventoryView view) {
        Iterator var3 = shop.getCategories().iterator();

        Category category;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            category = (Category)var3.next();
        } while(!view.getTitle().equals(category.getName()));

        return category;
    }
}
