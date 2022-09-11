package me.jaskri.bedwars.API.PACKAGE.Upgrade;

import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;

public interface Upgrade {

    String getName();

    boolean apply(GamePlayer var1);
}
