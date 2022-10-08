package me.jaskri.Upgrade.Shop.Item;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Upgrade.Shop.Item.TieredUpgradeItemTier;
import me.jaskri.API.Upgrade.TieredUpgrade;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.API.Upgrade.UpgradeManager;
import me.jaskri.Manager.ItemManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TieredUpgradeShopItem implements TieredUpgrade {

    private List<TieredUpgradeItemTier> tiers;
    private String name;
    private ItemStack display;
    private ItemDescription desc;
    private TieredUpgrade upgrade;

    public TieredUpgradeShopItem(String name, ItemStack display, List<TieredUpgradeItemTier> tiers, ItemDescription desc, TieredUpgrade upgrade) {
        this.name = name;
        this.desc = desc;
        this.tiers = tiers;
        this.upgrade = upgrade;
        this.initDisplayItem(display);
    }

    private void initDisplayItem(ItemStack display) {
        ItemManager manager = new ItemManager(display);
        manager.setName(this.name);
        List<String> lore = new ArrayList();
        this.desc.apply(lore);
        manager.addItemFlags(ItemFlag.values());
        manager.setLore(lore);
        this.display = manager.getItem();
    }

    public String getName() {
        return this.name;
    }

    public TieredUpgrade getUpgrade() {
        return this.upgrade;
    }

    public List<TieredUpgradeItemTier> getTiers() {
        return new ArrayList(this.tiers);
    }

    public TieredUpgradeItemTier getTier(int tier) {
        return tier >= 1 && tier <= this.tiers.size() ? (TieredUpgradeItemTier)this.tiers.get(tier - 1) : null;
    }

    private String formatCost(String name, int tier, ItemCost cost) {
        StringBuilder builder = (new StringBuilder("§7Tier ")).append(tier).append(": ").append(name).append(", ").append(ShopUtils.formatCost(cost));
        return builder.toString();
    }

    private String formatUnlocked(String name, int tier) {
        StringBuilder builder = (new StringBuilder("§7Tier ")).append(tier).append(": ").append(name).append(", ").append("§aUNLOCKED!");
        return builder.toString();
    }

    public ItemStack getDisplayItem(GamePlayer gp) {
        if (gp == null) {
            return null;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return null;
            } else {
                Player player = gp.getPlayer();
                UpgradeManager upgradeManager = team.getUpgradeManager();
                TieredUpgrade upgrade = this.getTieredUpgrade(upgradeManager);
                int next = upgrade.getNextTier();
                ItemManager itemManager = new ItemManager(this.display.clone());
                itemManager.addToLore((String)null);

                for(int tier = 1; tier <= this.tiers.size(); ++tier) {
                    TieredUpgradeItemTier upgradeTier = (TieredUpgradeItemTier)this.tiers.get(tier - 1);
                    if (tier <= upgrade.getCurrentTier()) {
                        itemManager.addToLore(this.formatUnlocked(upgradeTier.getName(), tier));
                    } else {
                        itemManager.addToLore(this.formatCost(upgradeTier.getName(), tier, upgradeTier.getCost()));
                    }
                }

                itemManager.addToLore((String)null);
                if (upgrade.getCurrentTier() >= 1) {
                    itemManager.addEnchantment(Enchantment.WATER_WORKER, 1, true);
                }

                if (upgrade.getCurrentTier() == upgrade.getMaximumTier()) {
                    itemManager.addToLore("§cYou already unlocked the highest tier!");
                } else {
                    ShopUtils.setBuyable(player, itemManager, ((TieredUpgradeItemTier)this.tiers.get(next - 1)).getCost());
                }

                return itemManager.getItem();
            }
        }
    }

    private TieredUpgrade getTieredUpgrade(UpgradeManager mgr) {
        Upgrade existing = mgr.getUpgrade(this.upgrade.getName());
        if (existing instanceof TieredUpgrade) {
            return (TieredUpgrade)existing;
        } else {
            TieredUpgrade result = this.upgrade.clone();
            result.setCurrentTier(0);
            return result;
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
                TieredUpgrade upgrade = this.getTieredUpgrade(upgradeManager);
                if (upgrade.getCurrentTier() == upgrade.getMaximumTier()) {
                    player.sendMessage("§cYou already unlocked the highest tier!");
                    return false;
                } else {
                    int next = upgrade.getNextTier();
                    TieredUpgradeItemTier tier = (TieredUpgradeItemTier)this.tiers.get(next - 1);
                    ItemCost cost = tier.getCost();
                    int amount = ShopUtils.getAmountNeeded(player, cost);
                    if (amount > 0) {
                        player.sendMessage("§cYou don't have enough Diamond! Need " + amount + " more!");
                        return false;
                    } else if (!ShopUtils.removeCost(player, cost)) {
                        return false;
                    } else {
                        upgrade.setCurrentTier(next);
                        upgradeManager.add(upgrade);
                        player.sendMessage("§aYou purchased §6" + this.name + "§a!");
                        XSound.BLOCK_NOTE_BLOCK_PLING.play(player.getLocation(), 1.0F, 2.0F);
                        return true;
                    }
                }
            }
        }
    }

    public ItemDescription getDescription() {
        return this.desc;
    }
}
