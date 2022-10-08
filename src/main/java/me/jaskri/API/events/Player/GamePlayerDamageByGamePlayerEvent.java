package me.jaskri.API.events.Player;

import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDamageByGamePlayerEvent extends GamePlayerDamageEvent{

    private GamePlayer damager;

    public GamePlayerDamageByGamePlayerEvent(GamePlayer player, GamePlayer damager, EntityDamageEvent.DamageCause cause) {
        super(player, cause);
        this.damager = damager;
    }

    public GamePlayer getDamager() {
        return this.damager;
    }
}
