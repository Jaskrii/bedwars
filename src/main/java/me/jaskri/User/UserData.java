package me.jaskri.User;

import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.User.UserStatistics;
import me.jaskri.Configuration.Configuration;
import me.jaskri.Shop.ShopConfig;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.NumberConversions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class UserData extends Configuration {

    private Map<GameMode, UserStatistics> stats = new LinkedHashMap();
    private Map<GameMode, QuickBuy> quick_buys = new LinkedHashMap();
    private OfflinePlayer player;
    private BedwarsLevel level;
    private Prestige prestige;
    private int balance;

    public UserData(OfflinePlayer player) {
        super(new File(Bedwars.getInstance().getDataFolder() + "/Userdata", player.getUniqueId() + ".yml"));
        this.player = player;
        this.saveDefaultConfig();
    }

    public BedwarsLevel getLevel() {
        return this.level != null ? this.level.clone() : null;
    }

    public void setLevel(BedwarsLevel level) {
        if (level != null) {
            this.level = level.clone();
        }

    }

    public Prestige getPrestige() {
        return this.prestige;
    }

    public void setPrestige(Prestige prestige) {
        if (prestige != null) {
            this.prestige = prestige;
        }

    }

    public UserStatistics getOverallStats() {
        UserStatistics result = new UserStatistics();
        Statistic[] values = Statistic.values();
        Iterator var3 = this.stats.values().iterator();

        while(var3.hasNext()) {
            UserStatistics user_stats = (UserStatistics)var3.next();
            Statistic[] var5 = values;
            int var6 = values.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Statistic stat = var5[var7];
                result.incrementStatistic(stat, user_stats.getStatistic(stat));
            }
        }

        return result;
    }

    public UserStatistics getStats(GameMode mode) {
        UserStatistics result = (UserStatistics)this.stats.get(mode);
        return result != null ? result.clone() : null;
    }

    public void setStats(GameMode mode, UserStatistics stats) {
        if (mode != null && stats != null) {
            this.stats.put(mode, stats.clone());
        }

    }

    public QuickBuy getQuickBuy(GameMode mode) {
        return mode != null ? (QuickBuy)this.quick_buys.get(mode) : null;
    }

    public void setQuickBuy(GameMode mode, QuickBuy qb) {
        if (mode != null && qb != null) {
            this.quick_buys.put(mode, qb);
        }

    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        if (balance >= 0) {
            this.balance = balance;
        }

    }

    public void loadData() {
        this.reloadConfig();
        int level = this.config.getInt("Level.level", 1);
        if (level <= 0) {
            level = 1;
        }

        int exp = this.config.getInt("Level.exp", 0);
        if (exp < 0) {
            exp = 0;
        }

        this.level = new BedwarsLevel(level, exp, Bedwars.getInstance().getSettings().getLevelUpExpFor(level));
        this.balance = this.config.getInt("Coins", 0);
        if (this.balance < 0) {
            this.balance = 0;
        }

        this.prestige = Prestige.getByName(this.config.getString("Prestige"));
        if (this.prestige == null) {
            this.prestige = Prestige.DEFAULT;
        }

        this.initUserStats();
        this.initQuickBuy();
    }

    public void saveData() {
        FileConfiguration config = new YamlConfiguration();
        if (this.level != null) {
            config.set("Level.level", this.level.getLevel());
            config.set("Level.exp", this.level.getProgressExp());
        }

        if (this.prestige != null) {
            config.set("Prestige", this.prestige.getName());
        }

        config.set("Coins", this.balance);
        Statistic[] values = Statistic.values();
        Iterator var3 = this.stats.entrySet().iterator();

        Map.Entry entry;
        GameMode mode;
        int slot;
        while(var3.hasNext()) {
            entry = (Map.Entry)var3.next();
            mode = (GameMode)entry.getKey();
            UserStatistics stats = (UserStatistics)entry.getValue();
            Statistic[] var7 = values;
            int var8 = values.length;

            for(slot = 0; slot < var8; ++slot) {
                Statistic stat = var7[slot];
                config.set("Statistics." + mode.getName() + "." + stat, stats.getStatistic(stat));
            }
        }

        var3 = this.quick_buys.entrySet().iterator();

        while(var3.hasNext()) {
            entry = (Map.Entry)var3.next();
            mode = (GameMode)entry.getKey();
            QuickBuy qb = (QuickBuy)entry.getValue();
            Iterator var13 = qb.getItems().entrySet().iterator();

            while(var13.hasNext()) {
                Map.Entry<Integer, Buyable> qbEntry = (Map.Entry)var13.next();
                slot = (Integer)qbEntry.getKey();
                String path = ShopConfig.getItemPath(mode, (Buyable)qbEntry.getValue());
                if (path != null) {
                    config.set("Quick-Buy." + mode.getName() + ".Slot-" + slot, path);
                }
            }
        }

        this.save(config, this.file, "Could not save user data " + this.player.getUniqueId() + "!");
    }

    public void initUserStats() {
        ConfigurationSection statsSection = this.config.getConfigurationSection("Statistics");
        if (statsSection != null) {
            Iterator var2 = statsSection.getKeys(false).iterator();

            while(true) {
                GameMode mode;
                ConfigurationSection section;
                do {
                    String statsSectionKey;
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }

                        statsSectionKey = (String)var2.next();
                        mode = GameMode.fromString(statsSectionKey);
                    } while(mode == null);

                    section = statsSection.getConfigurationSection(statsSectionKey);
                } while(section == null);

                UserStatistics stats = new UserStatistics();
                Iterator var7 = section.getKeys(false).iterator();

                while(var7.hasNext()) {
                    String statsKey = (String)var7.next();
                    Statistic stat = Statistic.getByName(statsKey);
                    if (stat != null) {
                        int value = Math.abs(section.getInt(statsKey, 0));
                        stats.setStatistic(stat, value);
                    }
                }

                this.stats.put(mode, stats);
            }
        }
    }

    public void initQuickBuy() {
        ConfigurationSection section = this.config.getConfigurationSection("Quick-Buy");
        if (section != null) {
            GameMode[] var2 = GameMode.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                GameMode mode = var2[var4];
                QuickBuy qb = new me.jaskri.QuickBuy.QuickBuy();
                Iterator var7 = section.getKeys(false).iterator();

                while(var7.hasNext()) {
                    String slotKey = (String)var7.next();
                    int slot = NumberConversions.toInt(slotKey.toLowerCase().replace("Slot-", ""));
                    if (slot > 0) {
                        Buyable buyable = ShopConfig.getPathItem(mode, section.getString(slotKey));
                        if (buyable != null) {
                            qb.setItem(slot, buyable);
                        }
                    }
                }

                this.quick_buys.put(mode, qb);
            }

        }
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            InputStream stream = Bedwars.getInstance().getResource("DefaultUser.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
            this.save(config, this.file, "Could not save default user for " + this.player.getName());
        }
    }

    private void save(FileConfiguration config, File file, String message) {
        try {
            config.save(file);
        } catch (IOException var5) {
            Bukkit.getLogger().log(Level.SEVERE, message);
        }

    }
}
