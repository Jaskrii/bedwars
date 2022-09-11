package me.jaskri.bedwars.API.PACKAGE.arena;

import jdk.internal.util.Preconditions;
import org.bukkit.scoreboard.Team;

public class BedwarsBed {
    (
    Team team, Block head , Block foot) {
        Preconditions.checkNotNull(team, "Bed team cannot be null!");
        Preconditions.checkNotNull(head, "Bed head cannot be null!");
        Preconditions.checkNotNull(foot, "Bed foot cannot be null!");
        this.team = team;
        this.getHead() = head;
        this.foot = foot;
    }

    public Team getTeam() {
        return this.team;
    }

    public Block getHead() {
        return this.head;
    }

    public Block getFoot() {
        return this.foot;
    }
}
