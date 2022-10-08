package me.jaskri.Listener;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Game.Game;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class GameMechanicsListener implements Listener {

    private static final Material FIREBALL;

    public GameMechanicsListener() {
    }

    @EventHandler
    public void onGamePlayerTNTPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.TNT) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null) {
                event.setCancelled(true);
                if (game.hasStarted() && !game.isSpectator(player)) {
                    Bedwars instance = Bedwars.getInstance();
                    Location loc = event.getBlock().getLocation().add(0.5, 0.0, 0.5);
                    final TNTPrimed tnt = (TNTPrimed)loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
                    tnt.setMetadata("player", new FixedMetadataValue(instance, game.getGamePlayer(player)));
                    tnt.setMetadata("game", new FixedMetadataValue(instance, game));
                    tnt.setYield(instance.getGameSettings().getTNTExplosionPower());
                    tnt.setFuseTicks(instance.getGameSettings().getTNTFuseTicks());
                    if (Bedwars.getInstance().getGameSettings().showTNTFuseTicks()) {
                        tnt.setCustomNameVisible(true);
                        (new BukkitRunnable() {
                            int ticks = tnt.getFuseTicks();

                            public void run() {
                                if (tnt.isDead()) {
                                    this.cancel();
                                } else {
                                    ChatColor color = ChatColor.GREEN;
                                    if (this.ticks < 40) {
                                        color = ChatColor.GOLD;
                                    } else if (this.ticks < 20) {
                                        color = ChatColor.RED;
                                    }

                                    tnt.setCustomName(color + Integer.toString(--this.ticks));
                                }
                            }
                        }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 1L);
                    }

                    ItemStack item = event.getItemInHand();
                    item.setAmount(item.getAmount() - 1);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerFireballLaunch(PlayerInteractEvent event) {
        if (event.getMaterial() == FIREBALL && event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted()) {
                GamePlayer gp = game.getGamePlayer(player);
                Fireball fb = (Fireball)player.launchProjectile(Fireball.class, player.getEyeLocation().getDirection().normalize().multiply(Bedwars.getInstance().getGameSettings().getFireballSpeed()));
                fb.setMetadata("player", new FixedMetadataValue(Bedwars.getInstance(), gp));
                fb.setMetadata("game", new FixedMetadataValue(Bedwars.getInstance(), game));
                fb.setYield(Bedwars.getInstance().getGameSettings().getFireballExplosionPower());
                ItemStack item = event.getItem();
                item.setAmount(item.getAmount() - 1);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }

    static {
        FIREBALL = XMaterial.FIRE_CHARGE.parseMaterial();
    }
}
