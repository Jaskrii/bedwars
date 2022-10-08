package me.jaskri.bedwars;

import jdk.internal.platform.Metrics;
import me.jaskri.API.Entity.GameEntityManager;
import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.arena.Arena;
import me.jaskri.API.arena.Region;
import me.jaskri.API.bedwars.BedwarsPlugin;
import me.jaskri.API.bedwars.UpgradesManager;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Game.GamePhase;
import me.jaskri.API.Generator.GeneratorSpeed;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.Shop.Shop;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Trap.TrapTarget;
import me.jaskri.API.Upgrade.Shop.UpgradeShop;
import me.jaskri.API.Upgrade.TieredUpgrade;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.API.User.User;
import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Bedwarss.settings.BedwarsSettings;
import me.jaskri.Bedwarss.settings.GameSettings;
import me.jaskri.Bedwarss.settings.MapForgeSettings;
import me.jaskri.Bedwarss.settings.TeamForgeSettings;
import me.jaskri.Listener.GameEntityListener;
import me.jaskri.Listener.GameMechanicsListener;
import me.jaskri.Listener.GamePlayerChatListener;
import me.jaskri.Listener.GamePlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.util.NumberConversions;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class Bedwars extends JavaPlugin implements BedwarsPlugin {

    private static Bedwars instance;
    private Map<UUID, BedwarsUser> loaded_users = new ConcurrentHashMap();
    private ScoreboardConfig scoreboardConfig;
    private PrestigeConfig prestigeConfig;
    private UpgradeShopConfig upgradeConfig;
    private ShopConfig shopConfig;
    private Database database;
    private BedwarsSettings settings;
    private GameSettings gameSettings;
    private MapForgeSettings mapForgeSettings;
    private TeamForgeSettings teamForgeSettings;
    private UpgradesManager upgradeManager;
    private String prefix;
    private Version version;
    private GameEntityManager gameEntityManager;
    private NPCManager npcmanager;

    public Bedwars() {
    }

    public void onLoad() {
        if ((this.version = Version.getVersion()) == Version.UNSUPPORTED) {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage("§cUnsupported version detected: §e" + Version.getVersionName());
            console.sendMessage("§cDisabling!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void onEnable() {
        instance = this;
        this.prefix = ChatColor.GOLD + "[" + ChatColor.AQUA + "Bedwars" + ChatColor.GOLD + "] " + ChatColor.RESET;
        this.init();
        this.initMetrics();
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        try {
            this.initNPCManager();
        } catch (Exception var4) {
            console.sendMessage("§cCould not initialize NPC Manager (§e" + this.version + "§c)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            this.initEntityManager();
        } catch (Exception var3) {
            console.sendMessage("§cCould not initialize Entity Manager (§e" + this.version + "§c)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        console.sendMessage(this.prefix + ChatColor.GRAY + "Registering commands!");
        this.registerCommands();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Registering listeners!");
        this.registerListeners();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Registering upgrades!");
        this.registerUpgrades();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Registering game phases!");
        this.registerGamePhases();
        this.registerConfiguration();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading bedwars settings!");
        this.loadSettings();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading game settings!");
        this.loadGameSettings();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading team forge settings!");
        this.loadTeamForgeSettings();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading map forge settings!");
        this.loadMapForgeSettings();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading database info!");
        this.loadDatabase();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading prestiges!");
        this.loadPrestiges();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading arenas!");
        this.loadArenas();
        console.sendMessage(this.prefix + ChatColor.GRAY + "Loading users!");
        this.loadUsers();
        console.sendMessage(this.prefix + "§6Detected NMS Version: " + this.version);
        console.sendMessage(this.prefix + "§aBedwars has been enabled!");
    }

    public void onDisable() {
        Iterator var1 = Bukkit.getScheduler().getActiveWorkers().iterator();

        while(var1.hasNext()) {
            BukkitWorker worker = (BukkitWorker)var1.next();
            if (worker.getOwner().equals(this)) {
                try {
                    worker.getThread().start();
                } catch (Throwable var4) {
                }
            }
        }

        Bukkit.getScheduler().cancelTasks(this);
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(this.prefix + "§cShutting down all available games!");
        Iterator var6 = AbstractGame.getGames().entrySet().iterator();

        while(var6.hasNext()) {
            Map.Entry<Arena, Game> entry = (Map.Entry)var6.next();
            ((Game)entry.getValue()).stopGame();
        }

        console.sendMessage(this.prefix + "§cSaving loaded arenas!");
        var6 = BedwarsArena.getArenas().iterator();

        while(var6.hasNext()) {
            Arena arena = (Arena)var6.next();
            arena.saveArena();
        }

        console.sendMessage(this.prefix + "§cSaving loaded users data!");
        var6 = this.loaded_users.values().iterator();

        while(var6.hasNext()) {
            BedwarsUser user = (BedwarsUser)var6.next();
            user.saveInDatabase();
            user.saveData();
        }

        if (this.database != null) {
            this.database.disconnect();
        }

        Bukkit.getConsoleSender().sendMessage(this.prefix + "§cBedwars has been disabled!");
    }

    private void init() {
        this.saveDefaultConfig();
        this.scoreboardConfig = ScoreboardConfig.getInstance();
        this.prestigeConfig = PrestigeConfig.getInstance();
        this.upgradeConfig = UpgradeShopConfig.getInstance();
        this.shopConfig = ShopConfig.getInstance();
        this.settings = BedwarsSettings.getInstance();
        this.gameSettings = GameSettings.getInstance();
        this.teamForgeSettings = TeamForgeSettings.getInstance();
        this.mapForgeSettings = MapForgeSettings.getInstance();
        this.upgradeManager = UpgradesManager.getInstance();
    }

    private void initMetrics() {
        Metrics metrics = new Metrics(this, 14317);
        metrics.addCustomChart(new Metrics.AdvancedPie("popular_modes", new Callable<Map<String, Integer>>() {
            public Map<String, Integer> call() throws Exception {
                Map<String, Integer> result = new HashMap(4);
                result.put("Solo", this.getModesPlayersCount(GameMode.SOLO));
                result.put("Doubles", this.getModesPlayersCount(GameMode.DUO));
                result.put("3v3v3v3", this.getModesPlayersCount(GameMode.TRIO));
                result.put("4v4v4v4", this.getModesPlayersCount(GameMode.QUATUOR));
                return result;
            }

            private int getModesPlayersCount(GameMode mode) {
                int result = 0;
                Iterator var3 = AbstractGame.getGames().values().iterator();

                while(var3.hasNext()) {
                    Game game = (Game)var3.next();
                    if (game.getMode() == mode) {
                        result += game.getGamePlayers().size();
                    }
                }

                return result;
            }
        }));
    }

    private void loadArenas() {
        Iterator var1 = BedwarsArena.getArenasNameList().iterator();

        while(var1.hasNext()) {
            String name = (String)var1.next();
            Arena arena = new BedwarsArena(name);
            arena.reloadArena();
            Region region = arena.getRegion();
            if (region != null) {
                region.getWorld().setAutoSave(false);
            }
        }

    }

    private void loadUsers() {
        LobbyScoreBoard board = this.getLobbyScoreboard();
        Iterator var2 = Bukkit.getOnlinePlayers().iterator();

        while(var2.hasNext()) {
            Player player = (Player)var2.next();
            User user = this.loadUser(player);
            if (user != null) {
                BedwarsLevel.setForPlayer(player, user.getLevel());
                user.setScoreboard(board);
                UserListener.addPlayerToUpdatingBoard(player);
            }
        }

    }

    private void registerCommands() {
        this.getCommand("Bedwars").setExecutor(new BedwarsCommand());
        this.getCommand("Rejoin").setExecutor(new RejoinCommand());
        this.getCommand("Play").setExecutor(new PlayCommand());
        this.getCommand("Shout").setExecutor(new ShoutCommand());
    }

    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new GameEntityListener(), this);
        manager.registerEvents(new GameMechanicsListener(), this);
        manager.registerEvents(new GamePlayerChatListener(), this);
        manager.registerEvents(new GamePlayerListener(), this);
        manager.registerEvents(new HologramListener(), this);
        manager.registerEvents(new ShopListener(), this);
        manager.registerEvents(new UserListener(), this);
        manager.registerEvents(new MapListener(), this);
    }

    private void registerConfiguration() {
    }

    private void registerUpgrades() {
        Upgrade sharpness = new EnchantmentUpgrade("Sharpnened Swords", Enchantment.DAMAGE_ALL, 1, false);
        this.upgradeManager.registerUpgrade("Sharpness", sharpness);
        Upgrade healpool = new HealPoolUpgrade();
        this.upgradeManager.registerUpgrade("Heal Pool", healpool);
        this.upgradeManager.registerUpgrade("Heal_Pool", healpool);
        TieredUpgrade protection = new TieredEnchantmentUpgrade("Protection", Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        this.upgradeManager.registerTieredUpgrade("Protection", protection);
        TieredUpgrade forge = new ForgeUpgrade();
        this.upgradeManager.registerTieredUpgrade("Forge", forge);
        PotionEffect haste1 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0);
        PotionEffect haste2 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
        TieredUpgrade fast_miner = new TieredEffectUpgrade("Maniac Miner", Arrays.asList(haste1, haste2));
        this.upgradeManager.registerTieredUpgrade("Maniac Miner", fast_miner);
        this.upgradeManager.registerTieredUpgrade("Maniac_Miner", fast_miner);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 300, 1);
        PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, 300, 1);
        Trap counter = new EffectTrap("Counter Offensive", TrapTarget.PLAYER_TEAM, 15, new PotionEffect[]{speed, jump});
        this.upgradeManager.registerTrapUpgrade("Counter_Offensive", counter);
        this.upgradeManager.registerTrapUpgrade("Counter-Offensive", counter);
        this.upgradeManager.registerTrapUpgrade("Counter Offensive", counter);
        PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 160, 0);
        Trap blindness = new EffectTrap("Blindness", TrapTarget.ENEMY, 8, new PotionEffect[]{blind});
        this.upgradeManager.registerTrapUpgrade("Blindness", blindness);
        PotionEffect slow_dig = new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0);
        Trap fatigue = new EffectTrap("Mining Fatigue", TrapTarget.ENEMY, 10, new PotionEffect[]{slow_dig});
        this.upgradeManager.registerTrapUpgrade("Mining Fatigue", fatigue);
        this.upgradeManager.registerTrapUpgrade("Mining_Fatigue", fatigue);
    }

    private void registerGamePhases() {
        this.registerGamePhase(new DiamondUpgradePhase_2(360));
        this.registerGamePhase(new DiamondUpgradePhase_3(360));
        this.registerGamePhase(new EmeraldUpgradePhase_2(360));
        this.registerGamePhase(new EmeraldUpgradePhase_3(360));
        this.registerGamePhase(new BedBreakPhase(600));
        this.registerGamePhase(new GameEndPhase(600));
    }

    private void loadPrestiges() {
        PrestigeConfig.getInstance().loadPrestiges();
    }

    private void loadSettings() {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Bedwars-Settings.Minimum-Players");
        int level;
        if (section != null) {
            Iterator var3 = section.getKeys(false).iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                GameMode mode = GameMode.getByName(key);
                if (mode != null) {
                    level = config.getInt(section.getCurrentPath() + "." + key);
                    if (level > mode.getGameMax()) {
                        level = mode.getGameMax() - mode.getTeamMax();
                    }

                    this.settings.setMinimumPlayers(mode, level >= 2 ? level : 2);
                }
            }
        }

        this.settings.setGameCountdown(config.getInt("Bedwars-Settings.Countdown"));
        this.settings.setDefaultLevelUpExp(config.getInt("Bedwars-Settings.LevelUp-Exp.default"));
        ConfigurationSection section2 = config.getConfigurationSection("Bedwars-Settings.LevelUp-Exp.levels");
        if (section2 != null) {
            Iterator var8 = section2.getKeys(false).iterator();

            while(var8.hasNext()) {
                String key = (String)var8.next();
                level = NumberConversions.toInt(key);
                if (level > 0) {
                    this.settings.setLevelUpExpFor(level, config.getInt(section2.getCurrentPath() + "." + key));
                }
            }
        }

        int respawnTime = config.getInt("Bedwars-Settings.Respawn-Time");
        int reconnectRespawnTime = config.getInt("Bedwars-Settings.Reconnect.respawn-time");
        this.settings.setRespawnTime(respawnTime);
        this.settings.setReconnectRespawnTime(reconnectRespawnTime);
        boolean autoReconnect = config.getBoolean("Bedwars-Settings.Reconnect.auto-reconnect");
        this.settings.setAutoReconnect(autoReconnect);
    }

    private void loadGameSettings() {
        FileConfiguration config = this.getConfig();
        List<GamePhase> phases = new ArrayList();
        Iterator var3 = config.getStringList("Game-Settings.Game-Phases").iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            GamePhase phase = GamePhase.getByName(key);
            if (phase == null) {
                this.getLogger().log(Level.WARNING, "Could not recognize game phase with the name of " + key);
            } else {
                phases.add(phase);
            }
        }

        this.gameSettings.setDefaultGamePhases(phases);
        float tntPower = (float)config.getDouble("Game-Settings.Game-Mechanics.TNT.power");
        float tntKb = (float)config.getDouble("Game-Settings.Game-Mechanics.TNT.kb");
        this.gameSettings.setTNTExplosionPower(tntPower);
        this.gameSettings.setTNTExplosionKb(tntKb);
        this.gameSettings.setTNTFuseTicks(config.getInt("Game-Settings.Game-Mechanics.TNT.fuse-ticks"));
        this.gameSettings.setShowTNTFuseTicks(config.getBoolean("Game-Settings.Game-Mechanics.TNT.show-fuse-ticks"));
        float fbPower = (float)config.getDouble("Game-Settings.Game-Mechanics.Fireball.power");
        float fbSpeed = (float)config.getDouble("Game-Settings.Game-Mechanics.Fireball.speed");
        float fbKb = (float)config.getDouble("Game-Settings.Game-Mechanics.Fireball.kb");
        this.gameSettings.setFireballExplosionPower(fbPower);
        this.gameSettings.setFireballExplosionKb(fbKb);
        this.gameSettings.setFireballSpeed(fbSpeed);
        long timePlayedForExp = config.getLong("Game-Settings.Time-Played-Rewards.Exp.time-played");
        int expReward = config.getInt("Game-Settings.Time-Played-Rewards.Exp.amount");
        long timePlayedForCoins = config.getLong("Game-Settings.Time-Played-Rewards.Coins.time-played");
        int coinsReward = config.getInt("Game-Settings.Time-Played-Rewards.Coins.amount");
        this.gameSettings.setTimeForExpReward(timePlayedForExp);
        this.gameSettings.setExpReward(expReward);
        this.gameSettings.setTimeForCoinsReward(timePlayedForCoins);
        this.gameSettings.setCoinsReward(coinsReward);
    }

    private void loadTeamForgeSettings() {
        FileConfiguration config = this.getConfig();
        String path = "Forge-Settings.Team-Forge";
        this.teamForgeSettings.setResourceSplitting(config.getBoolean(path + ".Resource-Splitting.enabled"));
        this.teamForgeSettings.setSplitRadius(config.getDouble(path + ".Resource-Splitting.radius"));
        ConfigurationSection limitSection = config.getConfigurationSection(path + ".Drop-Limit");
        if (limitSection != null) {
            Iterator var4 = limitSection.getKeys(false).iterator();

            label82:
            while(true) {
                String limitKey;
                Resource resource;
                do {
                    if (!var4.hasNext()) {
                        break label82;
                    }

                    limitKey = (String)var4.next();
                    resource = Resource.getByName(limitKey);
                } while(resource == null);

                GameMode[] var7 = GameMode.values();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    GameMode mode = var7[var9];
                    this.teamForgeSettings.setDropLimit(mode, resource, limitSection.getInt(limitKey));
                }
            }
        }

        ConfigurationSection speedSection = config.getConfigurationSection(path + ".Speeds");
        if (speedSection != null) {
            Iterator var15 = speedSection.getKeys(false).iterator();

            while(var15.hasNext()) {
                String speedKey = (String)var15.next();
                GeneratorSpeed speed = new GeneratorSpeed(speedKey);
                ConfigurationSection resourceSection = speedSection.getConfigurationSection(speedKey);
                if (resourceSection != null) {
                    Iterator var24 = resourceSection.getKeys(false).iterator();

                    while(var24.hasNext()) {
                        String resourceKey = (String)var24.next();
                        Resource resource = Resource.getByName(resourceKey);
                        if (resource != null) {
                            int value = resourceSection.getInt(resourceKey);
                            if (value > 0) {
                                speed.setDropsPerMinute(resource, value);
                            }
                        }
                    }
                } else {
                    Resource[] var23 = Resource.values();
                    int var25 = var23.length;

                    for(int var11 = 0; var11 < var25; ++var11) {
                        Resource rsc = var23[var11];
                        int value = speedSection.getInt(speedKey);
                        if (value > 0) {
                            speed.setDropsPerMinute(rsc, value);
                        }
                    }
                }

                this.teamForgeSettings.setGeneratorSpeed(speed);
                GeneratorSpeed.registerSpeed(speed);
            }
        }

        Set<Resource> drops = new HashSet();
        Iterator var18 = config.getStringList(path + ".Drops").iterator();

        while(var18.hasNext()) {
            String s = (String)var18.next();
            Resource resource = Resource.getByName(s);
            if (resource != null) {
                drops.add(resource);
            }
        }

        if (!drops.isEmpty()) {
            this.teamForgeSettings.setTeamDrops(drops);
        }

    }

    private void loadMapForgeSettings() {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection("Forge-Settings.Map-Forge");
        if (section != null) {
            Iterator var3 = section.getKeys(false).iterator();

            while(true) {
                Resource resource;
                HashMap limit;
                int var10;
                ConfigurationSection tiersSection;
                do {
                    String key;
                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        key = (String)var3.next();
                        resource = Resource.getByName(key);
                    } while(resource == null);

                    limit = new HashMap();
                    ConfigurationSection limitSection = section.getConfigurationSection(key + ".limit");
                    if (limitSection != null) {
                        Iterator var19 = limitSection.getKeys(false).iterator();

                        while(var19.hasNext()) {
                            String limitKey = (String)var19.next();
                            GameMode mode = GameMode.getByName(limitKey);
                            if (mode != null) {
                                limit.put(mode, limitSection.getInt(limitKey, 0));
                            }
                        }
                    } else {
                        GameMode[] var8 = GameMode.values();
                        int var9 = var8.length;

                        for(var10 = 0; var10 < var9; ++var10) {
                            GameMode mode = var8[var10];
                            limit.put(mode, section.getInt(key + ".limit", 0));
                        }
                    }

                    tiersSection = section.getConfigurationSection(key + ".tiers");
                } while(tiersSection == null);

                GameMode[] var22 = GameMode.values();
                var10 = var22.length;

                for(int var24 = 0; var24 < var10; ++var24) {
                    GameMode mode = var22[var24];
                    List<GeneratorTier> tiers = new ArrayList();
                    Iterator var14 = tiersSection.getKeys(false).iterator();

                    while(var14.hasNext()) {
                        String tierKey = (String)var14.next();
                        String title = tiersSection.getString(tierKey + ".title");
                        if (title != null) {
                            int time = tiersSection.getInt(tierKey + ".drop-time");
                            if (time > 0) {
                                Integer dropLimit = (Integer)limit.get(mode);
                                if (dropLimit != null && dropLimit > 0) {
                                    tiers.add(new GeneratorTier(title, time, dropLimit));
                                }
                            }
                        }
                    }

                    this.mapForgeSettings.setGeneratorTiers(mode, resource, tiers);
                }
            }
        }
    }

    private void loadDatabase() {
        FileConfiguration config = this.getConfig();
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        boolean enabled = config.getBoolean("Database.enabled");
        if (!enabled) {
            console.sendMessage(ChatUtils.info("Connecting to database is disabled! "));
            console.sendMessage(ChatUtils.info("You can always enable database at config.yml"));
        } else {
            String name = config.getString("Database.name", "");
            String password = config.getString("Database.password", "");
            String url = config.getString("Database.url", "");
            this.database = new Database(name, password, url);
            this.database.connect();
            if (!this.database.isConnected()) {
                console.sendMessage(ChatUtils.error("Could not connect to database! Please check your database info at config.yml"));
            } else {
                console.sendMessage(ChatUtils.success("Connected to database!"));
            }

            if (this.database.isConnected()) {
                this.database.createCoinsTable();
                GameMode[] var7 = GameMode.values();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    GameMode mode = var7[var9];
                    this.database.createStatsTable(mode);
                }

                if (config.getBoolean("save-userdata")) {
                    (new BukkitRunnable() {
                        public void run() {
                            try {
                                Iterator var1 = Bedwars.this.loaded_users.values().iterator();

                                while(var1.hasNext()) {
                                    BedwarsUser user = (BedwarsUser)var1.next();
                                    user.saveInDatabase();
                                }

                                Bedwars.this.getLogger().info("Saved users data to database!");
                            } catch (Exception var3) {
                                Bedwars.this.getLogger().severe("An error occured while saving users data!");
                            }

                        }
                    }).runTaskTimerAsynchronously(this, 0L, 36000L);
                }

            }
        }
    }

    private void initNPCManager() throws Exception {
        this.npcmanager = (NPCManager)Class.forName("com.slyvr." + this.version + ".npc.NPCManager").newInstance();
    }

    private void initEntityManager() throws Exception {
        this.gameEntityManager = (GameEntityManager)Class.forName("com.slyvr." + this.version + ".entity.GameEntityManager").newInstance();
    }

    public static Bedwars getInstance() {
        return instance;
    }

    public Version getVersion() {
        return this.version;
    }

    public String getPluginPrefix() {
        return this.prefix;
    }

    public Database getDataBase() {
        return this.database;
    }

    public Prestige getDefaultPrestige() {
        return this.prestigeConfig.getDefaultPrestige();
    }

    public PrestigeConfig getPrestigeConfig() {
        return this.prestigeConfig;
    }

    public BedwarsSettings getSettings() {
        return this.settings;
    }

    public GameSettings getGameSettings() {
        return this.gameSettings;
    }

    public TeamForgeSettings getTeamForgeSettings() {
        return this.teamForgeSettings;
    }

    public MapForgeSettings getMapForgeSettings() {
        return this.mapForgeSettings;
    }

    public ScoreboardConfig getScoreboardConfig() {
        return this.scoreboardConfig;
    }

    public GameScoreboard getGameScoreboard(GameMode mode) {
        return this.scoreboardConfig.getScoreboard(mode);
    }

    public WaitingScoreboard getWaitingScoreboard() {
        return this.scoreboardConfig.getWaitingScoreboard();
    }

    public LobbyScoreBoard getLobbyScoreboard() {
        return this.scoreboardConfig.getLobbyScoreboard();
    }

    public GamePhase getGamePhase(String name) {
        return GamePhase.getByName(name);
    }

    public void registerGamePhase(GamePhase phase) {
        GamePhase.registerGamePhase(phase);
    }

    public NPCManager getNPCManager() {
        return this.npcmanager;
    }

    public GameEntityManager getEntityManager() {
        return this.gameEntityManager;
    }

    public Shop getTeamShop(GameMode mode) {
        return this.shopConfig.getShop(mode);
    }

    public UpgradeShop getTeamUpgradeShop(GameMode mode) {
        return this.upgradeConfig.getUpgradeShop(mode);
    }

    public UpgradesManager getUpgradesManager() {
        return this.upgradeManager;
    }

    public ShopConfig getShopConfig() {
        return this.shopConfig;
    }

    public UpgradeShopConfig getUpgradeShopConfig() {
        return this.upgradeConfig;
    }

    public User loadUser(Player player) {
        if (player == null) {
            return null;
        } else {
            BedwarsUser user = (BedwarsUser)this.loaded_users.get(player.getUniqueId());
            if (user != null) {
                return user;
            } else {
                user = new BedwarsUser(player);
                user.loadData();
                this.loaded_users.put(player.getUniqueId(), user);
                return user;
            }
        }
    }

    public User getUser(Player player) {
        return this.loadUser(player);
    }

    public Arena getArena(String name) {
        return BedwarsArena.getArena(name);
    }

    public Game addPlayerToRandomGame(Player player, GameMode mode) {
        if (mode != null) {
            Iterator var3 = AbstractGame.getGames().values().iterator();

            while(var3.hasNext()) {
                Game game = (Game)var3.next();
                if (game.getMode().equals(mode) && !game.hasStarted() && game.addPlayer(player)) {
                    return game;
                }
            }
        }

        Game game = BedwarsGame.randomGame(mode);
        return game != null && game.addPlayer(player) ? game : null;
    }

    public Game addPlayerToRandomGame(Player player) {
        return this.addPlayerToRandomGame(player, (GameMode)null);
    }

    public Game getRandomGame(GameMode mode) {
        if (mode != null) {
            Iterator var2 = AbstractGame.getGames().values().iterator();

            while(var2.hasNext()) {
                Game game = (Game)var2.next();
                if (game.getMode().equals(mode) && !game.hasStarted()) {
                    return game;
                }
            }
        }

        return BedwarsGame.randomGame(mode);
    }

    public Game getRandomGame() {
        return this.getRandomGame((GameMode)null);
    }

    public Game getPlayerGame(Player player) {
        return AbstractGame.getPlayerGame(player);
    }

    public boolean inGame(Player player) {
        return AbstractGame.inGame(player);
    }

    public boolean inRunningGame(Player player) {
        return AbstractGame.inRunningGame(player);
    }

    public boolean sendPlayerToLobby(Game game, Player player, String message) {
        if (game != null && player != null) {
            Arena arena = game.getArena();
            Location lobby = arena.getLobbySpawnPoint();
            return lobby != null ? player.teleport(lobby) : true;
        } else {
            return false;
        }
    }
}
