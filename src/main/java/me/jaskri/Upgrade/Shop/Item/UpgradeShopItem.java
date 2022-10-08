package me.jaskri.Upgrade.Shop.Item;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Upgrade.Shop.Item.UpgradeItem;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.API.Upgrade.UpgradeManager;
import me.jaskri.API.events.Player.GamePlayerUpgradeBuyEvent;
import me.jaskri.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UpgradeShopItem implements UpgradeItem {

    private ItemStack unbuyable;
    private ItemStack buyable;
    private ItemStack unlocked;
    private String name;
    private ItemCost cost;
    private ItemDescription desc;
    private Upgrade upgrade;

    public UpgradeShopItem(String name, ItemStack display, ItemCost cost, ItemDescription desc, Upgrade upgrade) {
        Preconditions.checkNotNull(display, "Display item cannot be null!");
        this.name = name;
        this.cost = cost;
        this.desc = desc;
        this.upgrade = upgrade;
        this.initDisplayItem(display);
    }

    private void initDisplayItem(ItemStack item) {
        ItemManager manager = new ItemManager(item.clone());
        manager.addItemFlags(ItemFlag.values());
        List<String> lore = new ArrayList();
        if (!this.desc.isEmpty()) {
            this.desc.apply(lore);
            lore.add((Object)null);
        }

        lore.add("§7Cost: " + ShopUtils.formatCost(this.cost));
        lore.add((Object)null);
        manager.setLore(lore);
        lore.add("§cYou don't have enough " + ShopUtils.formatResource(this.cost) + "!");
        this.unbuyable = manager.setName(ChatColor.RED + this.name).getItem().clone();
        lore.set(lore.size() - 1, "§eClick to purchase!");
        this.buyable = manager.setName(ChatColor.GREEN + this.name).getItem().clone();
        lore.set(lore.size() - 1, "§aUNLOCKED!");
        this.unlocked = manager.addEnchantment(Enchantment.WATER_WORKER, 1, false).getItem().clone();
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return null;
            } else if (team.getUpgradeManager().contains(this.upgrade)) {
                return this.unlocked.clone();
            } else {
                return ShopUtils.hasEnough(gp.getPlayer(), this.cost) ? this.buyable.clone() : this.unbuyable.clone();
            }
        }
    }

    public boolean onBuy(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return false;
            } else {
                Player player = gp.getPlayer();
                UpgradeManager upgradeManager = team.getUpgradeManager();
                if (upgradeManager.contains(this.upgrade)) {
                    player.sendMessage("§cYou already purchased this upgrade!");
                    return false;
                } else {
                    GamePlayerUpgradeBuyEvent event = new GamePlayerUpgradeBuyEvent(gp, this, "§aYou purchased §6" + this.name + "§a!");
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return false;
                    } else {
                        int amount = ShopUtils.getAmountNeeded(player, this.cost);
                        if (amount != 0) {
                            player.sendMessage("§cYou don't have enough " + this.cost.getResource() + "! Need " + amount + " more!");
                            return false;
                        } else if (!ShopUtils.removeCost(player, this.cost)) {
                            return false;
                        } else {
                            upgradeManager.add(this.upgrade);
                            player.sendMessage(event.getBuyMessage());
                            XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                            return true;
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public Upgrade getUpgrade() {
        return this.upgrade;
    }

    public ItemCost getCost() {
        return this.cost.clone();
    }

    public ItemDescription getDescription() {
        return this.desc.clone();
    }
}
