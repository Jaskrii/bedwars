package me.jaskri.Game.Phase;

import me.jaskri.API.arena.Arena;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GamePhase;
import me.jaskri.API.Team.Team;

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
