package me.jaskri.API.arena;

import com.google.common.base.Preconditions;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Team;

public class BedwarsBed {

    Team team, Blockhead , Blockfoot) {
        com.google.common.base.Preconditions.checkNotNull(team, "Bed team cannot be null!");
        com.google.common.base.Preconditions.checkNotNull(getHead(), "Bed head cannot be null!");
        Preconditions.checkNotNull(getFoot(), "Bed foot cannot be null!");
        this.team = team;
        this.getHead() = getHead();
        this.getFoot() = getFoot();
    }

    public Team getTeam() {
        return this.team;
    }

    public Block getHead() {
        return this.getHead();
    }

    public Block getFoot() {
        return this.getFoot();
    }
}
