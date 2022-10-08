package me.jaskri.API.events.Player;

import org.bukkit.event.entity.EntityDamageEvent;

public class GamePlayerDeathByGamePlayerEvent extends GamePlayerDeathEvent{

    private GamePlayer killer;

    public GamePlayerDeathByGamePlayerEvent(GamePlayer player, GamePlayer killer, EntityDamageEvent.DamageCause cause, String message) {
        super(player, cause, message);
        this.killer = killer;
    }

    public GamePlayer getKiller() {
        return this.killer;
    }
}
