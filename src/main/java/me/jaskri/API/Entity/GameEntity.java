package me.jaskri.API.Entity;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

public interface GameEntity {
    Game getGame();

    Team getGameTeam();

    GamePlayer getOwner();

    GameEntityTipe getGameEntityType();

    Entity getEntity();

    Entity spawn();

    void remove ();

    boolean hasSpawned();



}
