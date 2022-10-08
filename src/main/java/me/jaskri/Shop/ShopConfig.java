package me.jaskri.Shop;

import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Game.player.ArmorType;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Category;
import me.jaskri.API.Shop.Item.*;
import me.jaskri.Shop.Item.*;
import me.jaskri.bedwars.API.Shop.Item.*;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.Shop.Shop;
import me.jaskri.bedwars.Bedwars;
import me.jaskri.Configuration.Configuration;
import me.jaskri.Manager.ItemManager;
import me.jaskri.bedwars.Shop.Item.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;

import java.io.File;
import java.util.*;

public class ShopConfig extends Configuration {

    private static final Map<GameMode, Shop> SHOPS = new HashMap();
    private static final Map<GameMode, Map<Buyable, String>> ITEMS_PATHS = new HashMap();
    private static final Map<GameMode, Map<String, Buyable>> PATHS_ITEMS = new HashMap();
    private static ShopConfig instance;
    private ConfigUtils utils = new ConfigUtils(this.getConfig());

    private ShopConfig() {
        super(new File(Bedwars.getInstance().getDataFolder(), "Shop.yml"));
        this.saveDefaultConfig();
    }

    public Shop getShop(GameMode mode) {
        Shop shop = (Shop)SHOPS.get(mode);
        if (shop != null) {
            return shop;
        } else {
            BedwarsShop shop;
            SHOPS.put(mode, shop = new BedwarsShop());
            ConfigurationSection shopSection = this.config.getConfigurationSection("Shop");
            if (shopSection == null) {
                return shop;
            } else {
                ConfigurationSection categorySection = this.config.getConfigurationSection("Categories");
                if (categorySection == null) {
                    return shop;
                } else {
                    Iterator var5 = categorySection.getKeys(false).iterator();

                    while(var5.hasNext()) {
                        String key = (String)var5.next();
                        if (key.equalsIgnoreCase("Quick-Buy")) {
                            shop.addCategory(new QuickBuy() {
                                @Override
                                public String getName() {
                                    return null;
                                }

                                @Override
                                public ItemStack getDisplayItem() {
                                    return null;
                                }

                                @Override
                                public Map<Integer, Buyable> getItems() {
                                    return null;
                                }

                                @Override
                                public void setItems(Map<Integer, Buyable> var1) {

                                }

                                @Override
                                public void addItems(Buyable... var1) {

                                }

                                @Override
                                public Buyable getItem(int var1) {
                                    return null;
                                }

                                @Override
                                public void setItem(int var1, Buyable var2) {

                                }

                                @Override
                                public Buyable removeItem(int var1) {
                                    return null;
                                }

                                @Override
                                public boolean removeItem(Buyable var1) {
                                    return false;
                                }

                                @Override
                                public void applyItems(Inventory var1, GamePlayer var2) {

                                }

                                @Override
                                public void clear() {

                                }
                            });
                        } else {
                            Category category = this.getShopCategory(key, mode);
                            if (category != null) {
                                shop.addCategory(category);
                            }
                        }
                    }

                    return shop;
                }
            }
        }
    }

    private void putInItems(GameMode mode, Buyable buyable, String path) {
        Map<Buyable, String> items = (Map)ITEMS_PATHS.get(mode);
        if (items == null) {
            ITEMS_PATHS.put(mode, items = new HashMap());
        }

        ((Map)items).put(buyable, path);
    }

    private Category getShopCategory(String name, GameMode mode) {
        if (name == null) {
            return null;
        } else {
            XMaterial type = this.utils.getXMaterial("Categories." + name + ".type");
            if (type == null) {
                return null;
            } else {
                Map<Integer, Buyable> items = new HashMap();
                Iterator var5 = this.config.getConfigurationSection("Shop." + name).getKeys(false).iterator();

                while(var5.hasNext()) {
                    String key = (String)var5.next();
                    int slot = NumberConversions.toInt(key.replace("Slot-", ""));
                    if (slot > 0) {
                        String path = "Shop." + name + "." + key;
                        ShopItemType itemType = ShopItemType.fromString(this.config.getString(path + ".item-type"));
                        if (itemType != null && itemType != ShopItemType.CUSTOM) {
                            Buyable buyable = null;
                            if (itemType == ShopItemType.TIERED) {
                                buyable = this.getTieredShopItem(path, mode);
                            } else {
                                buyable = this.getShopItem(path, mode);
                            }

                            if (buyable != null) {
                                items.put(slot - 1, buyable);
                                this.putInItems(mode, (Buyable)buyable, path);
                            }
                        }
                    }
                }

                return new ShopCategory(name, type.parseItem(), items);
            }
        }
    }

    private Item getShopItem(String path, GameMode mode) {
        if (path == null) {
            return null;
        } else {
            ShopItemType itemType = ShopItemType.fromString(this.config.getString(path + ".item-type"));
            if (itemType == null) {
                return null;
            } else {
                ItemCost cost = this.utils.getCost(path + ".cost", mode);
                if (cost == null) {
                    return null;
                } else {
                    String name = this.config.getString(path + ".name", "BedWars Item");
                    ItemDescription desc = this.utils.getDescription(path + ".description");
                    if (itemType == ShopItemType.ITEM) {
                        return this.getShopItem(path, name, cost, desc, this.config.getBoolean(path + ".permanent"));
                    } else if (itemType == ShopItemType.ARMOR) {
                        return this.getArmorItem(path, name, cost, desc);
                    } else {
                        return itemType == ShopItemType.POTION ? this.getPotionItem(path, name, cost, desc) : null;
                    }
                }
            }
        }
    }

    private TieredItem getTieredShopItem(String path, GameMode mode) {
        if (path != null && this.config.isConfigurationSection(path + ".tiers")) {
            ShopItemType itemType = ShopItemType.fromString(this.config.getString(path + ".item-type"));
            if (itemType != null && itemType == ShopItemType.TIERED) {
                List<ShopItem> items = new ArrayList();
                Iterator var5 = this.config.getConfigurationSection(path + ".tiers").getKeys(false).iterator();

                while(var5.hasNext()) {
                    String key = (String)var5.next();
                    Item item = this.getShopItem(path + ".tiers." + key, mode);
                    if (item instanceof ShopItem) {
                        items.add((ShopItem)item);
                    }
                }

                return new TieredShopItem(items);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private ShopItem getShopItem(String path, String name, ItemCost cost, ItemDescription desc, boolean permanent) {
        ItemStack raw = this.getItem(path);
        if (raw == null) {
            return null;
        } else {
            ItemStack display = this.getItem(path + ".Display-Item");
            Iterator var8 = this.getEnchantments(path + ".enchants").entrySet().iterator();

            while(var8.hasNext()) {
                Map.Entry<Enchantment, Integer> entry = (Map.Entry)var8.next();
                raw.addUnsafeEnchantment((Enchantment)entry.getKey(), (Integer)entry.getValue());
                if (display != null) {
                    display.addUnsafeEnchantment((Enchantment)entry.getKey(), (Integer)entry.getValue());
                }
            }

            Material replace = this.utils.getMaterial(path + ".replace");
            return new ShopItem(name, raw, display, cost, desc, replace, permanent);
        }
    }

    private ItemStack getItem(String path) {
        XMaterial material = this.utils.getXMaterial(path + ".type");
        if (material == null) {
            return null;
        } else {
            ItemManager manager = new ItemManager(material.parseItem());
            String name = this.config.getString(path + ".name");
            if (name != null) {
                manager.setName(ChatColor.RESET + name);
            }

            manager.setAmount(this.config.getInt(path + ".amount", 1));
            return manager.getItem();
        }
    }

    private Map<Enchantment, Integer> getEnchantments(String path) {
        Map<Enchantment, Integer> result = new HashMap(4);
        List<String> enchantsList = this.config.getStringList(path);
        Iterator var4 = enchantsList.iterator();

        while(var4.hasNext()) {
            String string = (String)var4.next();
            String[] values = string.split(":");
            if (values.length >= 2) {
                Enchantment ench = this.getEnchantment(values[0]);
                if (ench != null) {
                    int level = NumberConversions.toInt(values[1]);
                    if (level > 0) {
                        result.put(ench, level);
                    }
                }
            }
        }

        return result;
    }

    private Enchantment getEnchantment(String name) {
        Optional<XEnchantment> optional = XEnchantment.matchXEnchantment(name);
        return optional.isPresent() ? ((XEnchantment)optional.get()).getEnchant() : null;
    }

    private ArmorShopItem getArmorItem(String path, String name, ItemCost cost, ItemDescription desc) {
        ArmorType type = ArmorType.getByName(this.config.getString(path + ".armor"));
        if (type == null) {
            return null;
        } else {
            ItemStack display = this.getItem(path + ".Display-Item");
            return new ArmorShopItem(name, display, type, cost, desc);
        }
    }

    private PotionShopItem getPotionItem(String path, String name, ItemCost cost, ItemDescription desc) {
        PotionEffect effect = this.getEffect(path);
        if (effect == null) {
            return null;
        } else {
            ItemStack display = this.getItem(path + ".Display-Item");
            return new PotionShopItem(name, display, effect, cost, desc);
        }
    }

    private PotionEffect getEffect(String path) {
        PotionEffectType type = PotionEffectType.getByName(this.config.getString(path + ".effect", ""));
        if (type == null) {
            return null;
        } else {
            int duration = this.config.getInt(path + ".duration", 0);
            if (duration <= 0) {
                return null;
            } else {
                int level = this.config.getInt(path + ".level", 0);
                return level <= 0 ? null : new PotionEffect(type, duration * 20, level - 1);
            }
        }
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            Bedwars.getInstance().saveResource("Shop.yml", false);
        }

    }

    public static String getItemPath(GameMode mode, Buyable buyable) {
        Map<Buyable, String> items = (Map)ITEMS_PATHS.get(mode);
        return items != null ? (String)items.get(buyable) : null;
    }

    public static Buyable getPathItem(GameMode mode, String path) {
        Map<String, Buyable> items = (Map)PATHS_ITEMS.get(mode);
        return items != null ? (Buyable)items.get(path) : null;
    }

    public static ShopConfig getInstance() {
        if (instance == null) {
            instance = new ShopConfig();
        }

        return instance;
    }
}
