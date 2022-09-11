package me.jaskri.bedwars.API.PACKAGE.events.Generator;

import org.bukkit.event.HandlerList;

public class GeneratorLimitReachEvent extends GeneratorEvent{

    private static final HandlerList HANDLERS = new HandlerList();

    public GeneratorLimitReachEvent(ItemGenerator generator) {
        super(generator);
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
