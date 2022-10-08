package me.jaskri.Team;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.API.Upgrade.UpgradeManager;

import javax.sql.rowset.Predicate;
import java.util.*;

public class TeamUpgradeManager implements UpgradeManager {

    private Map<String, Upgrade> upgrades = new HashMap(4);

    public TeamUpgradeManager() {
    }

    public Set<Upgrade> getUpgrades() {
        return new HashSet(this.upgrades.values());
    }

    public Upgrade getUpgrade(String name) {
        return name != null ? (Upgrade)this.upgrades.get(name.toLowerCase()) : null;
    }

    public void add(Upgrade upgrade) {
        if (upgrade != null) {
            this.upgrades.put(upgrade.getName().toLowerCase(), upgrade);
        }

    }

    public void remove(Upgrade upgrade) {
        if (upgrade != null) {
            this.upgrades.remove(upgrade.getName().toLowerCase());
        }

    }

    public boolean contains(Upgrade upgrade) {
        return upgrade != null ? this.upgrades.containsKey(upgrade.getName().toLowerCase()) : false;
    }

    public void apply(GamePlayer gp) {
        this.apply(gp, (Predicate)null);
    }

    public void apply(GamePlayer gp, Predicate<Upgrade> predicate) {
        if (gp != null) {
            Iterator var3 = this.upgrades.values().iterator();

            while(true) {
                Upgrade upgrade;
                do {
                    if (!var3.hasNext()) {
                        return;
                    }

                    upgrade = (Upgrade)var3.next();
                } while(predicate != null && !predicate.test(upgrade));

                upgrade.apply(gp);
            }
        }
    }
}
