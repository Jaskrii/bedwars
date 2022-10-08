package me.jaskri.API.Upgrade;

public interface TieredUpgrade extends Upgrade, Cloneable{

    int getMaximumTier();

    int getNextTier();

    int getCurrentTier();

    int getPreviousTier();

    void setCurrentTier(int var1);

    TieredUpgrade clone();
}
