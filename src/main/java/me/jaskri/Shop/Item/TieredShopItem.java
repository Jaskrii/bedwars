package me.jaskri.Shop.Item;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.GameInventory;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Item;
import me.jaskri.API.Shop.Item.TieredItem;
import me.jaskri.API.Shop.Item.TieredItemStack;
import me.jaskri.API.events.Player.GamePlayerItemBuyEvent;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TieredShopItem implements TieredItem {

    private List<ShopItem> tiers;

    public TieredShopItem(List<ShopItem> items) {
        Preconditions.checkNotNull(items, "Tiers cannot be null");
        List<ShopItem> result = new ArrayList(items.size());
        Iterator var3 = items.iterator();

        while(var3.hasNext()) {
            ShopItem item = (ShopItem)var3.next();
            if (item != null) {
                result.add(item);
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Tiers cannot be empty!");
        } else {
            this.tiers = result;
        }
    }

    public List<Item> getTiers() {
        return new ArrayList(this.tiers);
    }

    public Item getTier(int tier) {
        return tier >= 1 && tier <= this.tiers.size() ? (Item)this.tiers.get(tier - 1) : null;
    }

    public TieredItemStack getPlayerTier(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            TieredItemStack item = this.createTieredItemStack(gp);
            GameInventory inv = gp.getInventory();
            Iterator var4 = inv.getTieredItems().iterator();

            TieredItemStack tiered;
            do {
                if (!var4.hasNext()) {
                    return item;
                }

                tiered = (TieredItemStack)var4.next();
            } while(!tiered.equals(item));

            return tiered;
        }
    }

    private TieredItemStack createTieredItemStack(GamePlayer gp) {
        List<ItemStack> items = new ArrayList(this.tiers.size());
        Iterator var3 = this.tiers.iterator();

        while(var3.hasNext()) {
            ShopItem shopItem = (ShopItem)var3.next();
            ItemStack item = shopItem.getRawItem(gp);
            if (item != null) {
                items.add(item);
            }
        }

        return new TieredItemStack(items);
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            TieredItemStack tier = this.getPlayerTier(gp);
            ItemManager manager = new ItemManager(((ShopItem)this.tiers.get(tier.getNextTier() - 1)).getDisplayItem(gp));
            List<String> lore = manager.getLore();
            lore.add(1, "§7Tier: §e" + tier.getNextTier());
            if (tier.getCurrentTier() == tier.getMaximumTier()) {
                lore.set(lore.size() - 1, "§cYou already have the maximum tier!");
            }

            return manager.setLore(lore).getItem();
        }
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            Player player = gp.getPlayer();
            TieredItemStack item = this.getPlayerTier(gp);
            if (item.getCurrentTier() == item.getMaximumTier()) {
                player.sendMessage("§cYou already have the highest tier!");
                return false;
            } else {
                ShopItem next = (ShopItem)this.tiers.get(item.getNextTier() - 1);
                GamePlayerItemBuyEvent event = new GamePlayerItemBuyEvent(gp, this, "§aYou purchased §6" + next.getName());
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled() && ShopUtils.buyItem(gp, next)) {
                    item.setCurrentTier(item.getNextTier());
                    gp.getInventory().addTieredItem(item);
                    if (item.hasPrevious()) {
                        player.getInventory().removeItem(new ItemStack[]{item.previous()});
                    }

                    player.sendMessage(event.getBuyMessage());
                    XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 100.0F, 2.0F);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean contains(Item item) {
        return item != null && this.tiers.contains(item);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.tiers});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof TieredShopItem)) {
            return false;
        } else {
            TieredShopItem other = (TieredShopItem)obj;
            return Objects.equals(this.tiers, other.tiers);
        }
    }
}
