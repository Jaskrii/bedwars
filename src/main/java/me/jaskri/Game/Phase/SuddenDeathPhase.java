package me.jaskri.Game.Phase;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GamePhase;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Team.Team;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Location;

import java.util.Iterator;

public class SuddenDeathPhase extends GamePhase {

    public SuddenDeathPhase(int duration) {
        super("Sudden Death", duration);
    }

    public boolean apply(Game game) {
        if (game == null) {
            return false;
        } else {
            Location loc = game.getArena().getDragonSpawnPoint();
            if (loc == null) {
                return false;
            } else {
                Iterator var3 = game.getTeams().iterator();

                while(var3.hasNext()) {
                    GameTeam team = (GameTeam)var3.next();
                    this.spawnDragon(game, team.getTeam(), loc);
                    if (team.getUpgradeManager().contains(Bedwars.getInstance().getUpgradesManager().getUpgrade("Dragon Buff"))) {
                        this.spawnDragon(game, team.getTeam(), loc);
                    }
                }

                return true;
            }
        }
    }

    private void spawnDragon(Game game, Team team, Location loc) {
        Bedwars.getInstance().getEntityManager().createDragon(game, team, (GamePlayer) null, loc).spawn();
    }
}
