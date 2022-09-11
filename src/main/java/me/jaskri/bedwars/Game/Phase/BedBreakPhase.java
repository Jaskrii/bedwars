package me.jaskri.bedwars.Game.Phase;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GamePhase;
import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.API.PACKAGE.arena.Arena;

import java.util.Iterator;

public class BedBreakPhase extends GamePhase {


    public BedBreakPhase(int duration) {
        super("Bed Break", duration);
    }

    public boolean apply(Game game) {
        if (game == null) {
            return false;
        } else {
            Arena arena = game.getArena();
            Iterator var3 = arena.getTeams().iterator();

            while(var3.hasNext()) {
                Team team = (Team)var3.next();
                game.breakTeamBed(team);
            }

            return true;
        }
    }
}
