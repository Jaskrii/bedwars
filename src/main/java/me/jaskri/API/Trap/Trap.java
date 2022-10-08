package me.jaskri.API.Trap;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Team.Team;

public interface Trap {

    String getName();

    TrapTarget getTarget();

    int getDuration();

    boolean onTrigger(GamePlayer var1, Team var2);
}
