package me.jaskri.bedwars.API.PACKAGE.Trap;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;
import me.jaskri.bedwars.API.PACKAGE.Team.Team;

public interface Trap {

    String getName();

    TrapTarget getTarget();

    int getDuration();

    boolean onTrigger(GamePlayer var1, Team var2);
}
