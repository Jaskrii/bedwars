package me.jaskri.API.events.trap;

import org.bukkit.event.Event;

public class TrapEvent extends Event {
    private Trap trap;

    public TrapEvent(Trap trap) {
        this.trap = trap;
    }

    public final Trap getTrap() {
        return this.trap;
    }
}
