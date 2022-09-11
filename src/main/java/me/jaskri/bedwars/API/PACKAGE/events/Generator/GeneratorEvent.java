package me.jaskri.bedwars.API.PACKAGE.events.Generator;

import org.bukkit.event.Event;

public abstract class GeneratorEvent extends Event {

    protected ItemGenerator generator;

    public GeneratorEvent(ItemGenerator generator) {
        this.generator = generator;
    }

    public ItemGenerator getGenerator() {
        return this.generator;
    }
}
