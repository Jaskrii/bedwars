package me.jaskri.Upgrade;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TieredEffectUpgrade extends AbstractTieredUpgrade{

    private List<PotionEffect> effects;

    public TieredEffectUpgrade(String name, List<PotionEffect> effects) {
        super(name, 0, effects.size());
        Preconditions.checkNotNull(effects, "Effects cannot be null!");
        List<PotionEffect> result = new ArrayList(effects.size());
        Iterator var4 = effects.iterator();

        while(var4.hasNext()) {
            PotionEffect effect = (PotionEffect)var4.next();
            if (effect != null) {
                result.add(effect);
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Effects cannot be empty!");
        } else {
            this.effects = effects;
        }
    }

    public boolean apply(GamePlayer gp) {
        if (this.current != 0 && gp != null) {
            Game game = gp.getGame();
            Collection<GamePlayer> team_players = game.getTeamPlayers(gp.getTeam());
            if (team_players.isEmpty()) {
                return false;
            } else {
                PotionEffect effect = (PotionEffect)this.effects.get(this.current - 1);
                Iterator var5 = team_players.iterator();

                while(var5.hasNext()) {
                    GamePlayer inGame = (GamePlayer)var5.next();
                    Player player = inGame.getPlayer();
                    if (!game.isEliminated(player) && !game.isSpectator(player)) {
                        player.addPotionEffect(effect);
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
