package me.jaskri.bedwars.API.PACKAGE.events.Player;

import me.jaskri.bedwars.API.PACKAGE.Entity.GameEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDamageByGameEntityEvent extends GamePlayerDamageEvent implements Cancellable {

    private GameEntity damager;

    public GamePlayerDamageByGameEntityEvent(GamePlayer player, GameEntity entity, EntityDamageEvent.DamageCause cause) {
        super(player, cause);
        this.damager = entity;
    }

    public GameEntity getDamager() {
        return this.damager;
    }
}
