package me.jaskri.Upgrade;

import me.jaskri.API.Game.player.GamePlayer;

public final class DragonBuffUpgrade extends AbstractUpgrade{

    public DragonBuffUpgrade() {
        super("Dragon Buff");
    }

    public String getName() {
        return "Dragon Buff";
    }

    public boolean apply(GamePlayer gp) {
        return true;
    }

    public boolean equals(Object obj) {
        return obj instanceof DragonBuffUpgrade;
    }
}
