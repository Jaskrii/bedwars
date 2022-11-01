package me.jaskri.Util;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.bedwars.Bedwars;
import me.jaskri.bedwars.settings.BedwarsSettings;

public class LevelUtils {

    public LevelUtils() {
    }

    public static Prestige levelUp(BedwarsLevel level, int amount) {
        Prestige next = null;
        int lvl = level.getLevel();

        while(level.isLeveling(amount)) {
            BedwarsSettings var10001 = Bedwars.getInstance().getSettings();
            ++lvl;
            level.setExpToLevel(var10001.getLevelUpExpFor(lvl));
            amount -= level.getExpToLevelUp();
            Prestige lvlPrestige = Prestige.getByStartLevel(lvl);
            if (lvlPrestige != null) {
                next = lvlPrestige;
            }
        }

        level.setLevel(lvl);
        level.incrementProgressExp(amount);
        return next;
    }
}
