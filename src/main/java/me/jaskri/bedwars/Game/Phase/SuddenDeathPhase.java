package me.jaskri.bedwars.Game.Phase;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GamePhase;
import me.jaskri.bedwars.API.PACKAGE.Game.player.GamePlayer;
import me.jaskri.bedwars.API.PACKAGE.Team.GameTeam;
import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.Bedwars.Bedwars;
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
