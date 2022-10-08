package me.jaskri.API.bed.fireball;

import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.event.Event;

public class FireBallEvent extends Event {

    private final GamePlayer owner;
    private final Fireball fireball;

    public FireballEvent(GamePlayer owner, Fireball fireball) {
        this.owner = owner;
        this.fireball = fireball;
    }

    public final GamePlayer getOwner() {
        return this.owner;
    }

    public final Fireball getFireball() {
        return this.fireball;
    }
}
