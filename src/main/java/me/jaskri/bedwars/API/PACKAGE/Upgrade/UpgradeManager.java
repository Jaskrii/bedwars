package me.jaskri.bedwars.API.PACKAGE.Upgrade;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;

import java.util.Set;
import java.util.function.Predicate;

public interface UpgradeManager {

    Set<Upgrade> getUpgrades();

    Upgrade getUpgrade(String var1);

    void add(Upgrade var1);

    void remove(Upgrade var1);

    boolean contains(Upgrade var1);

    void apply(GamePlayer var1);

    void apply(GamePlayer var1, Predicate<Upgrade> var2);
}
