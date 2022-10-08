package me.jaskri.Upgrade;

import me.jaskri.API.Upgrade.TieredUpgrade;

public abstract class AbstractTieredUpgrade implements TieredUpgrade {

    protected String name;
    protected int min;
    protected int max;
    protected int current;

    public AbstractTieredUpgrade(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.current = min;
    }

    public String getName() {
        return this.name;
    }

    public int getMaximumTier() {
        return this.max;
    }

    public int getNextTier() {
        int next = this.current + 1;
        return next <= this.max ? next : this.max;
    }

    public int getCurrentTier() {
        return this.current;
    }

    public int getPreviousTier() {
        int previous = this.current - 1;
        return previous >= this.min ? previous : this.min;
    }

    public void setCurrentTier(int tier) {
        if (tier >= this.min && tier <= this.max) {
            this.current = tier;
        }

    }

    public TieredUpgrade clone() {
        try {
            return (AbstractTieredUpgrade)super.clone();
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
