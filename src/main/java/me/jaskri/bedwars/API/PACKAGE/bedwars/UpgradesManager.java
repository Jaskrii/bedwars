package me.jaskri.bedwars.API.PACKAGE.bedwars;

import java.util.HashMap;
import java.util.Map;

public class UpgradesManager {
    private Map<String, TieredUpgrade> tiered = new HashMap<>();
    private Map<String, Upgrade> upgrades = new HashMap();
    private Map<String, Trap> traps = new HashMap();
    private static UpgradesManager instance;

    private UpgradesManager() {
    }

    public Upgrade getUpgrade(String name) {
        return name != null ? (Upgrade)this.upgrades.get(name.toLowerCase()) : null;
    }

    public void registerUpgrade(String name, Upgrade upgrade) {
        this.register(this.upgrades, name, upgrade);
    }

    public TieredUpgrade getTieredUpgrade(String name) {
        if (name == null) {
            return null;
        } else {
            TieredUpgrade upgrade = (TieredUpgrade)this.tiered.get(name.toLowerCase());
            return upgrade != null ? upgrade.clone() : null;
        }
    }

    public void registerTieredUpgrade(String name, TieredUpgrade upgrade) {
        this.register(this.tiered, name, upgrade);
    }

    public Trap getTrapUpgrade(String name) {
        return name != null ? (Trap)this.traps.get(name.toLowerCase()) : null;
    }

    public void registerTrapUpgrade(String name, Trap trap) {
        this.register(this.traps, name, trap);
    }

    private <V> void register(Map<String, V> map, String key, V value) {
        if (key != null && value != null) {
            if (this.traps.containsKey(key.toLowerCase())) {
                throw new IllegalStateException("TrapUpgrade is already registered");
            } else {
                map.put(key.toLowerCase(), value);
            }
        }
    }

    public static UpgradesManager getInstance() {
        if (instance == null) {
            instance = new UpgradesManager();
        }

        return instance;
    }
}
