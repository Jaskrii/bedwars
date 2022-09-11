package me.jaskri.bedwars.API.PACKAGE.Entity;

import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

public interface GameEntity {
    Game getGame();

    Team getGameTeam();

    GamePlayer getOwner();

    GameEntityType getGameEntityType();

    Entity getEntity();

    Entity spawn();

    void remove ();

    boolean hasSpawned();



}
