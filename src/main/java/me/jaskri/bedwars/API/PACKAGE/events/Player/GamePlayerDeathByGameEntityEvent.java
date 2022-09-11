package me.jaskri.bedwars.API.PACKAGE.events.Player;

import me.jaskri.bedwars.API.PACKAGE.Entity.GameEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDeathByGameEntityEvent extends GamePlayerDeathEvent{

    private GameEntity entity;

    public GamePlayerDeathByGameEntityEvent(GamePlayer player, GameEntity entity, EntityDamageEvent.DamageCause cause, String message) {
        super(player, cause, message);
        this.entity = entity;
    }

    public GameEntity getKiller() {
        return this.entity;
    }

    public GamePlayer getOwner() {
        return this.hasOwner() ? this.entity.getOwner() : null;
    }

    public boolean hasOwner() {
        return this.entity.getOwner() != null;
    }
}
