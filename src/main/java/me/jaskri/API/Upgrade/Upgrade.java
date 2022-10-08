package me.jaskri.API.Upgrade;

import me.jaskri.API.Game.player.GamePlayer;

public interface Upgrade {

    String getName();

    boolean apply(GamePlayer var1);
}
