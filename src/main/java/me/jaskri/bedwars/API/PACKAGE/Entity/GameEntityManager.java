package me.jaskri.bedwars.API.PACKAGE.Entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

public interface GameEntityManager {
    BedBug createBedBug(Game var1, Team var2, GamePlayer var3, Location var4);

    BedBug getBedBug(Entity var1);

    boolean isBedBug(Entity var1);

    BodyGuard createBodyGuard(Game var1, Team var2, GamePlayer var3, Location var4);

    BodyGuard getBodyGuard(Entity var1);

    boolean isBodyGuard(Entity var1);

    Dragon createDragon(Game var1, Team var2, GamePlayer var3, Location var4);

    Dragon getDragon(Entity var1);

    boolean isDragon(Entity var1);

    GameEntity getGameEntity(Entity var1);

    boolean isGameEntity(Entity var1);
}
