package me.jaskri.bedwars.API.PACKAGE.Entityy;

import me.jaskri.bedwars.API.PACKAGE.Entity.GameEntity;
import org.bukkit.event.Event;

public class GameEntityEvenet extends Event {

    private final GameEntity entity;

    public GameEntityEvent(GameEntity entity) {
        this.entity = entity;
    }

    public final GameEntity getEntity() {
        return this.entity;
    }
}
