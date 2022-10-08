package me.jaskri.Trap;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Team.Team;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Trap.TrapTarget;
import me.jaskri.Listener.GamePlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.Iterator;

public class EffectTrap extends AbstractTrap implements Trap {

    private PotionEffect[] effects;

    public EffectTrap(String name, TrapTarget target, int duration, PotionEffect... effects) {
        super(name, target, duration);
        Preconditions.checkNotNull(effects, "Effect cannot be null!");
        this.effects = effects;
    }

    public boolean onTrigger(GamePlayer gp, Team team) {
        if (gp != null && team != null) {
            Game game = gp.getGame();
            Player player = gp.getPlayer();
            if (GamePlayerListener.isTrapSafe(player)) {
                return false;
            } else {
                switch (this.target) {
                    case ENEMY:
                        this.applyEffects(gp);
                        return true;
                    case ENEMY_TEAM:
                        this.applyEffects(game, gp.getTeam());
                        return true;
                    case PLAYER_TEAM:
                        this.applyEffects(game, team);
                        return true;
                    default:
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    private void applyEffects(GamePlayer gp) {
        PotionEffect[] var2 = this.effects;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            PotionEffect effect = var2[var4];
            effect.apply(gp.getPlayer());
        }

    }

    private void applyEffects(Game game, Team team) {
        Iterator var3 = game.getTeamPlayers(team).iterator();

        while(var3.hasNext()) {
            GamePlayer gp = (GamePlayer)var3.next();
            this.applyEffects(gp);
        }

    }

    public int hashCode() {
        return super.hashCode() * 31 + Arrays.hashCode(this.effects);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (super.equals(obj) && obj instanceof EffectTrap) {
            EffectTrap other = (EffectTrap)obj;
            return Arrays.equals(this.effects, other.effects);
        } else {
            return false;
        }
    }
}
