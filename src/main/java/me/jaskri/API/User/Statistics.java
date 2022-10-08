package me.jaskri.API.User;

import org.bukkit.Statistic;

import java.util.HashMap;
import java.util.Map;

public enum Statistics {

    GAME_PLAYED("Game Played"),
    BED_BROKEN("Bed Broken"),
    BED_LOSSES("Bed Losses"),
    KILLS("Kills"),
    DEATHS("Deaths"),
    FINAL_KILLS("Final Kills"),
    FINAL_DEATHS("Final Deaths"),
    WINS("Wins"),
    LOSSES("Losses"),
    WINSTREAK("Winstreak");

    private static final Map<String, Statistic> BY_NAME = new HashMap();
    private final String name;

    private Statistic(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static Statistic getByName(String name) {
        return name != null ? (Statistic)BY_NAME.get(name.toLowerCase()) : null;
    }

    static {
        Statistic[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Statistic stat = var0[var2];
            BY_NAME.put(stat.name().toLowerCase(), stat);
            BY_NAME.put(stat.name().toLowerCase(), stat);
        }

    }
}
