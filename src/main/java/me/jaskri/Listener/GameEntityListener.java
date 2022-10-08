package me.jaskri.Listener;

import me.jaskri.API.Entity.GameEntity;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Entityy.GameEntityDamageByGamePlayerEvent;
import me.jaskri.API.Entityy.GameEntityDeathEvent;
import me.jaskri.API.Game.Game;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GameEntityListener implements Listener {

    public GameEntityListener() {
    }

    @EventHandler
    public void onGamePlayerDamageGameEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && event.getDamager() instanceof Player) {
            GameEntity damaged = Bedwars.getInstance().getEntityManager().getGameEntity(event.getEntity());
            if (damaged != null) {
                Player damager = (Player)event.getDamager();
                Game game = AbstractGame.getPlayerGame(damager);
                if (game != null && game.hasStarted()) {
                    GamePlayer gp = game.getGamePlayer(damager);
                    if (damaged.getGameTeam() == gp.getTeam()) {
                        event.setCancelled(true);
                    } else {
                        GameEntityDamageByGamePlayerEvent bwEvent = new GameEntityDamageByGamePlayerEvent(damaged, gp);
                        Bukkit.getPluginManager().callEvent(bwEvent);
                        if (bwEvent.isCancelled()) {
                            event.setCancelled(true);
                        } else if (!(((LivingEntity)event.getEntity()).getHealth() - event.getFinalDamage() > 0.0)) {
                            GameEntityDeathEvent bwEvent2 = new GameEntityDeathEvent(damaged, gp);
                            Bukkit.getPluginManager().callEvent(bwEvent2);
                            damaged.remove();
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
