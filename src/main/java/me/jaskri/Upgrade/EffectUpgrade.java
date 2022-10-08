package me.jaskri.Upgrade;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.Iterator;

public class EffectUpgrade extends AbstractUpgrade{

    private PotionEffect effect;

    public EffectUpgrade(String name, PotionEffect effect) {
        super(name);
    }

    public boolean apply(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            Game game = gp.getGame();
            if (game == null) {
                return false;
            } else {
                Collection<GamePlayer> team_players = game.getTeamPlayers(gp.getTeam());
                if (team_players.isEmpty()) {
                    return false;
                } else {
                    Iterator var4 = team_players.iterator();

                    while(var4.hasNext()) {
                        GamePlayer inGame = (GamePlayer)var4.next();
                        Player player = inGame.getPlayer();
                        if (!game.isEliminated(player) && !game.isSpectator(player)) {
                            player.addPotionEffect(this.effect);
                        }
                    }

                    return true;
                }
            }
        }
    }
}
