package me.jaskri.bedwars.API.PACKAGE.events.Generator;

import org.bukkit.event.HandlerList;

public class GeneratorUpgradeEvent extends GeneratorEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private String upgradeMessage;

    public GeneratorUpgradeEvent(ItemGenerator generator, String upgradeMessage) {
        super(generator);
        this.upgradeMessage = upgradeMessage;
    }

    public String getUpgradeMessage() {
        return this.upgradeMessage;
    }

    public void setUpgradeMessage(String message) {
        this.upgradeMessage = message;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
