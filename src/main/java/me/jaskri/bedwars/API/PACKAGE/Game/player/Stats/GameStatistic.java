package me.jaskri.bedwars.API.PACKAGE.Game.player.Stats;

import java.util.HashMap;
import java.util.Map;

public enum GameStatistic {

    KILLS("Kills"),
    DEATHS("Deaths"),
    FINAL_KILLS("Final Kills"),
    FINAL_DEATHS("Final Deaths"),
    BED_BROKEN("Bed Broken"),
    BED_LOST("Bed Lost");

    private static final Map<String, GameStatistic> BY_NAME = new HashMap();
    private final String name;

    private GameStatistic(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static GameStatistic fromString(String string) {
        return string != null ? (GameStatistic)BY_NAME.get(string.toLowerCase()) : null;
    }

    static {
        GameStatistic[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            GameStatistic stat = var0[var2];
            BY_NAME.put(stat.toString().toLowerCase(), stat);
        }

    }
}
