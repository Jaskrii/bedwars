package me.jaskri.Upgrade;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.arena.Region;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Iterator;

public class HealPoolUpgrade extends AbstractUpgrade{

    private static final PotionEffect EFFECT;

    public HealPoolUpgrade() {
        super("Heal Pool");
    }

    public boolean apply(final GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            final Game game = gp.getGame();
            if (game.isEliminated(gp.getTeam())) {
                return false;
            } else {
                GameTeam team = game.getGameTeam(gp.getTeam());
                if (team == null) {
                    return false;
                } else {
                    final Location teamSpawn = game.getArena().getTeamSpawnPoint(gp.getTeam());
                    if (teamSpawn == null) {
                        return false;
                    } else {
                        (new BukkitRunnable() {
                            private Collection<GamePlayer> players = game.getTeamPlayers(gp.getTeam());
                            private Region region = HealPoolUpgrade.this.getRegionByPoint(teamSpawn, 20.0);

                            public void run() {
                                if (game.hasStarted() && !game.isEliminated(gp.getTeam())) {
                                    Iterator var1 = this.players.iterator();

                                    while(var1.hasNext()) {
                                        GamePlayer gpx = (GamePlayer)var1.next();
                                        Player p = gpx.getPlayer();
                                        if (!game.isDisconnected(p) && !game.isSpectator(p) && this.region.isInside(p)) {
                                            p.addPotionEffect(HealPoolUpgrade.EFFECT);
                                        }
                                    }

                                } else {
                                    this.cancel();
                                }
                            }
                        }).runTaskTimer(Bedwars.getInstance(), 0L, 20L);
                        return true;
                    }
                }
            }
        }
    }

    private Region getRegionByPoint(Location point, double radius) {
        Location pos1 = point.clone();
        pos1.add(radius, radius, radius);
        Location pos2 = point.clone();
        pos2.subtract(radius, radius, radius);
        return new Region(pos1, pos2);
    }

    static {
        EFFECT = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0);
    }
}
