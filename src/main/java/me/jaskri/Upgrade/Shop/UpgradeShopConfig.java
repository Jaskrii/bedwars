package me.jaskri.Upgrade.Shop;

import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Shop.Item.ItemCost;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Upgrade.Shop.Item.TieredUpgradeItem;
import me.jaskri.API.Upgrade.Shop.Item.TieredUpgradeItemTier;
import me.jaskri.API.Upgrade.Shop.Item.TrapItem;
import me.jaskri.API.Upgrade.Shop.Item.UpgradeItem;
import me.jaskri.API.Upgrade.Shop.UpgradeShop;
import me.jaskri.API.Upgrade.TieredUpgrade;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.Configuration.Configuration;
import me.jaskri.Upgrade.Shop.Item.TieredUpgradeShopItem;
import me.jaskri.Upgrade.Shop.Item.TrapShopItem;
import me.jaskri.Upgrade.Shop.Item.UpgradeShopItem;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.NumberConversions;

import java.io.File;
import java.util.*;

public class UpgradeShopConfig extends Configuration {

    private static final Map<GameMode, UpgradeShop> SHOPS = new HashMap();
    private static UpgradeShopConfig instance;
    private ConfigUtils utils = new ConfigUtils(this.getConfig());

    private UpgradeShopConfig() {
        super(new File(Bedwars.getInstance().getDataFolder(), "UpgradeShop.yml"));
        this.saveDefaultConfig();
    }

    public UpgradeShop getUpgradeShop(GameMode mode) {
        UpgradeShop shop = (UpgradeShop)SHOPS.get(mode);
        if (shop != null) {
            return shop;
        } else {
            BedwarsUpgradeShop shop;
            SHOPS.put(mode, shop = new BedwarsUpgradeShop());
            ConfigurationSection section = this.config.getConfigurationSection("Shop");
            if (section == null) {
                return shop;
            } else {
                Iterator var4 = section.getKeys(false).iterator();

                while(var4.hasNext()) {
                    String key = (String)var4.next();
                    int slot = NumberConversions.toInt(key.replace("Slot-", ""));
                    if (slot > 0) {
                        Type type = this.getType("Shop." + key);
                        if (type != null) {
                            switch (type) {
                                case TIERED_UPGRADE:
                                    shop.addItem(slot - 1, this.getTieredUpgradeItem("Shop." + key, mode));
                                    break;
                                case UPGRADE:
                                    shop.addItem(slot - 1, this.getUpgradeItem("Shop." + key, mode));
                                    break;
                                case TRAP:
                                    shop.addItem(slot - 1, this.getTrapItem("Shop." + key, mode));
                            }
                        }
                    }
                }

                return shop;
            }
        }
    }

    private TieredUpgradeItem getTieredUpgradeItem(String path, GameMode mode) {
        Type type = this.getType(path);
        if (type != UpgradeShopConfig.Type.TIERED_UPGRADE) {
            return null;
        } else {
            ConfigurationSection section = this.config.getConfigurationSection(path + ".tiers");
            if (section == null) {
                return null;
            } else {
                TieredUpgrade upgrade = Bedwars.getInstance().getUpgradesManager().getTieredUpgrade(this.config.getString(path + ".upgrade"));
                if (upgrade == null) {
                    return null;
                } else {
                    XMaterial itemType = this.utils.getXMaterial(path + ".type");
                    if (itemType == null) {
                        return null;
                    } else {
                        List<TieredUpgradeItemTier> tiers = new ArrayList();
                        Iterator var8 = section.getKeys(false).iterator();

                        while(var8.hasNext()) {
                            String key = (String)var8.next();
                            String name = section.getString(key + ".name");
                            if (name != null) {
                                ItemCost cost = this.utils.getCost(section.getCurrentPath() + "." + key + ".cost", mode);
                                if (cost != null) {
                                    tiers.add(new TieredUpgradeItemTier(name, cost));
                                }
                            }
                        }

                        if (tiers.isEmpty()) {
                            return null;
                        } else {
                            ItemDescription desc = this.utils.getDescription(path + ".description");
                            return new TieredUpgradeShopItem(this.getName(path), itemType.parseItem(), tiers, desc, upgrade);
                        }
                    }
                }
            }
        }
    }

    private UpgradeItem getUpgradeItem(String path, GameMode mode) {
        Type type = this.getType(path);
        if (type != UpgradeShopConfig.Type.UPGRADE) {
            return null;
        } else {
            Upgrade upgrade = Bedwars.getInstance().getUpgradesManager().getUpgrade(this.config.getString(path + ".upgrade"));
            if (upgrade == null) {
                return null;
            } else {
                XMaterial itemType = this.utils.getXMaterial(path + ".type");
                if (itemType == null) {
                    return null;
                } else {
                    ItemCost cost = this.utils.getCost(path + ".cost", mode);
                    if (cost == null) {
                        return null;
                    } else {
                        ItemDescription desc = this.utils.getDescription(path + ".description");
                        return new UpgradeShopItem(this.getName(path), itemType.parseItem(), cost, desc, upgrade);
                    }
                }
            }
        }
    }

    private TrapItem getTrapItem(String path, GameMode mode) {
        Type type = this.getType(path);
        if (type != UpgradeShopConfig.Type.TRAP) {
            return null;
        } else {
            Trap upgrade = Bedwars.getInstance().getUpgradesManager().getTrapUpgrade(this.config.getString(path + ".trap"));
            if (upgrade == null) {
                return null;
            } else {
                XMaterial itemType = this.utils.getXMaterial(path + ".type");
                if (itemType == null) {
                    return null;
                } else {
                    ItemDescription desc = this.utils.getDescription(path + ".description");
                    return new TrapShopItem(this.getName(path), itemType.parseItem(), desc, upgrade);
                }
            }
        }
    }

    private String getName(String path) {
        return this.config.getString(path + ".name", "BedWars Trap");
    }

    private Type getType(String path) {
        return UpgradeShopConfig.Type.getByName(this.config.getString(path + ".upgrade-type"));
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            Bedwars.getInstance().saveResource("UpgradeShop.yml", false);
        }

    }

    public static UpgradeShopConfig getInstance() {
        if (instance == null) {
            instance = new UpgradeShopConfig();
        }

        return instance;
    }

    static enum Type {
        TIERED_UPGRADE,
        UPGRADE,
        TRAP;

        private static final Map<String, Type> BY_NAME = new HashMap(3);

        private Type() {
        }

        public static Type getByName(String name) {
            return name != null ? (Type)BY_NAME.get(name.toLowerCase()) : null;
        }

        static {
            Type[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Type type = var0[var2];
                BY_NAME.put(type.name().toLowerCase(), type);
            }

            BY_NAME.put("tiered upgrade", TIERED_UPGRADE);
        }
    }

    public enum Type {

        static enum Type {
            TIERED_UPGRADE,
            UPGRADE,
            TRAP;

            private static final Map<String, Type> BY_NAME = new HashMap(3);

            private Type() {
            }

            public static Type getByName(String name) {
                return name != null ? (Type)BY_NAME.get(name.toLowerCase()) : null;
            }

            static {
                Type[] var0 = Type.values();
                int var1 = var0.length;

                for(int var2 = 0; var2 < var1; ++var2) {
                    Type type = var0[var2];
                    BY_NAME.put(type.name().toLowerCase(), type);
                }

                BY_NAME.put("tiered upgrade", TIERED_UPGRADE);
            }
        }
    }
}
