package me.jaskri.bedwars.API.PACKAGE.Upgrade;

public interface TieredUpgrade extends Upgrade, Cloneable{

    int getMaximumTier();

    int getNextTier();

    int getCurrentTier();

    int getPreviousTier();

    void setCurrentTier(int var1);

    TieredUpgrade clone();
}
