package me.jaskri.Shop.Item;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.events.Player.GamePlayerItemBuyEvent;
import me.jaskri.Manager.ItemManager;
import me.jaskri.Util.ShopUtils;
import me.jaskri.Util.Version;
import me.jaskri.Util.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

public class PotionShopItem extends AbstractShopItem{

    private ItemStack unbuyable;
    private ItemStack buyable;
    private ItemStack raw;

    public PotionShopItem(String name, ItemStack display, PotionEffect effect, ItemCost cost, ItemDescription desc) {
        super(name, cost, desc);
        Preconditions.checkNotNull(effect, "PotionEffect cannot be null!");
        this.initRawItem(effect);
        this.initDisplayItem(display, effect);
    }

    private void initRawItem(PotionEffect effect) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.addCustomEffect(effect, true);
        if (Version.getVersion().isNewAPI()) {
            meta.setBasePotionData(new PotionData(PotionType.getByEffect(effect.getType())));
        } else {
            meta.setMainEffect(effect.getType());
        }

        item.setItemMeta(meta);
        this.raw = item;
    }

    private void initDisplayItem(ItemStack item, PotionEffect effect) {
        ItemManager manager = new ItemManager(item != null ? item : new ItemStack(Material.POTION));
        ItemMeta meta = manager.getItemMeta();
        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta)meta;
            PotionEffectType type = effect.getType();
            if (!potionMeta.hasCustomEffect(type)) {
                potionMeta.addCustomEffect(effect, true);
            }

            if (Version.getVersion().isNewAPI()) {
                potionMeta.setBasePotionData(new PotionData(PotionType.getByEffect(effect.getType())));
            } else {
                potionMeta.setMainEffect(effect.getType());
            }
        }

        manager.setItemMeta(meta);
        ItemStack[] display_items = ShopUtils.toShopDisplayItems(manager, this.name, this.cost, this.desc);
        this.unbuyable = display_items[0];
        this.buyable = display_items[1];
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            return ShopUtils.hasEnough(gp.getPlayer(), this.cost) ? this.buyable.clone() : this.unbuyable.clone();
        }
    }

    public ItemStack getRawItem(GamePlayer gp) {
        return this.raw.clone();
    }

    public ShopItemType getShopItemType() {
        return ShopItemType.fromString(Potion);
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            GamePlayerItemBuyEvent event = new GamePlayerItemBuyEvent(gp, this, "§aYou purchased &6" + this.name);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled() && ShopUtils.buyItem(gp, this)) {
                Player player = gp.getPlayer();
                player.sendMessage(event.getBuyMessage());
                XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                return true;
            } else {
                return false;
            }
        }
    }
}
