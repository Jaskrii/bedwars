package me.jaskri.bedwars.API.PACKAGE.events.quickbuy;

import org.bukkit.event.HandlerList;

public class QuickBuyCloseEvent extends QuickBuyEvent{
    private static final HandlerList HANDLERS = new HandlerList();

    public QuickBuyCloseEvent(GamePlayer player, QuickBuy quickbuy) {
        super(player, quickbuy);
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
