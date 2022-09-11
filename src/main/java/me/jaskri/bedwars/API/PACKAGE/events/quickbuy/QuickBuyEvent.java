package me.jaskri.bedwars.API.PACKAGE.events.quickbuy;

import org.bukkit.event.Event;

public abstract class QuickBuyEvent extends Event {

    protected GamePlayer player;
    protected QuickBuy quickbuy;

    public QuickBuyEvent(GamePlayer owner, QuickBuy quickbuy) {
        this.player = owner;
        this.quickbuy = quickbuy;
    }

    public final GamePlayer getOwner() {
        return this.player;
    }

    public QuickBuy getQuickBuy() {
        return this.quickbuy;
    }
}
