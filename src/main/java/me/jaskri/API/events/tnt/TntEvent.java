package me.jaskri.API.events.tnt;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;

public class TntEvent extends Event {

    private final GamePlayer owner;
    private final TNTPrimed tnt;

    public TNTEvent(GamePlayer owner, TNTPrimed tnt) {
        this.owner = owner;
        this.tnt = tnt;
    }

    public GamePlayer getOwner() {
        return this.owner;
    }

    public TNTPrimed getTNT() {
        return this.tnt;
    }
}
