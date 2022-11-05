package me.jaskri.Listener;

import me.jaskri.API.Entity.GameEntityManager;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.NPC.NpcManager;
import me.jaskri.API.arena.Region;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Game.Game;
import me.jaskri.API.bed.fireball.FireBallExplodeEvent;
import me.jaskri.API.events.Player.GamePlayerBlockBreakEvent;
import me.jaskri.API.events.Player.GamePlayerBlockPlaceEvent;
import me.jaskri.API.events.tnt.TNTExplodeEvent;
import me.jaskri.Util.BedUtils;
import me.jaskri.Util.ChatUtils;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.*;

public interface MapListener implements Listener {

    private static final Map<Game, Set<Block>> GAME_BLOCKS = new HashMap();

    public MapListener() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onGamePlayerMapBlockPlace(BlockPlaceEvent event) {
        Material type = event.getBlock().getType();
        if (type != Material.TNT && type != Material.SPONGE && !BedUtils.isBed(event.getBlock())) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null) {
                if (game.hasStarted() && !game.isSpectator(player)) {
                    Block block = event.getBlock();
                    Region region = game.getArena().getRegion();
                    if ((double)block.getY() > region.getMaxY()) {
                        player.sendMessage("§cYou have reached the build limit!");
                        event.setCancelled(true);
                    } else if (!region.isInside(block)) {
                        player.sendMessage("§cYou can't place a block here!");
                        event.setCancelled(true);
                    } else if (this.callEvent(new GamePlayerBlockPlaceEvent(game.getGamePlayer(player), block)).isCancelled()) {
                        event.setCancelled(true);
                    } else {
                        addBlock(game, event.getBlock());
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onGamePlayerMapBlockBreak(BlockBreakEvent event) {
        if (!BedUtils.isBed(event.getBlock().getType())) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null) {
                if (!game.isSpectator(player) && !game.isEliminated(player)) {
                    Block block = event.getBlock();
                    if (block.hasMetadata("bedwars")) {
                        if (this.callEvent(new GamePlayerBlockBreakEvent(game.getGamePlayer(player), block)).isCancelled()) {
                            event.setCancelled(true);
                        } else {
                            block.removeMetadata("bedwars", Bedwars.getInstance());
                        }
                    } else {
                        ChatUtils.sendMessage(player, "&cYou can only break blocks placed by a player!");
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Item) && !(entity instanceof Projectile) && !(entity instanceof Explosive) && !(entity instanceof ArmorStand)) {
            if (!(entity instanceof SplashPotion)) {
                NpcManager manager = Bedwars.getInstance().getNPCManager();
                if (!manager.isNPC(event.getEntity())) {
                    GameEntityManager manager2 = Bedwars.getInstance().getEntityManager();
                    if (!manager2.isGameEntity(event.getEntity())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.PRIMED_TNT) {
            Entity entity = event.getEntity();
            if (entity.hasMetadata("game") && entity.hasMetadata("player")) {
                Game game = (Game)((MetadataValue)entity.getMetadata("game").get(0)).value();
                GamePlayer owner = (GamePlayer)((MetadataValue)entity.getMetadata("player").get(0)).value();
                Iterator<Block> iterator = event.blockList().iterator();

                while(true) {
                    Block block;
                    do {
                        if (!iterator.hasNext()) {
                            Collection<Entity> entities = entity.getNearbyEntities(5.0, 5.0, 5.0);
                            Cancellable cancellable = this.callEvent(new TNTExplodeEvent(owner, (TNTPrimed)entity, entities));
                            if (cancellable.isCancelled()) {
                                event.setCancelled(true);
                                return;
                            }

                            float kb = Bedwars.getInstance().getGameSettings().getTNTExplosionKb();
                            Iterator var9 = entities.iterator();

                            while(var9.hasNext()) {
                                Entity nearby = (Entity)var9.next();
                                if (nearby.getType() != EntityType.DROPPED_ITEM) {
                                    Location entityLoc = entity.getLocation();
                                    Vector vec = null;
                                    if (nearby.getLocation().distanceSquared(entityLoc) > 1.0) {
                                        vec = nearby.getLocation().toVector().subtract(entityLoc.toVector()).normalize().multiply(kb);
                                    } else {
                                        vec = new Vector(0, 1, 0);
                                    }

                                    if (!(nearby instanceof Player)) {
                                        nearby.setVelocity(vec);
                                    } else {
                                        Player player = (Player)nearby;
                                        GamePlayer gp = game.getGamePlayer(player);
                                        if (gp != null && game.isSpectator(player)) {
                                            player.setVelocity(vec);
                                        }
                                    }
                                }
                            }

                            return;
                        }

                        block = (Block)iterator.next();
                    } while(block.hasMetadata("bedwars") && !this.isStainedGlass(block));

                    iterator.remove();
                }
            }
        }
    }

    @EventHandler
    public void onFireballExplosion(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.FIREBALL) {
            Entity entity = event.getEntity();
            if (entity.hasMetadata("game") && entity.hasMetadata("player")) {
                Game game = (Game)((MetadataValue)entity.getMetadata("game").get(0)).value();
                GamePlayer owner = (GamePlayer)((MetadataValue)entity.getMetadata("player").get(0)).value();
                Iterator<Block> iterator = event.blockList().iterator();

                while(true) {
                    Block block;
                    do {
                        if (!iterator.hasNext()) {
                            Collection<Entity> entities = entity.getNearbyEntities(5.0, 5.0, 5.0);
                            Cancellable cancellable = this.callEvent(new FireBallExplodeEvent(owner, (Fireball)entity, entities));
                            if (cancellable.isCancelled()) {
                                event.setCancelled(true);
                                return;
                            }

                            float kb = Bedwars.getInstance().getGameSettings().getFireballExplosionKb();
                            Iterator var9 = entities.iterator();

                            while(var9.hasNext()) {
                                Entity nearby = (Entity)var9.next();
                                if (nearby.getType() != EntityType.DROPPED_ITEM) {
                                    Location entityLoc = entity.getLocation();
                                    Vector vec = null;
                                    if (nearby.getLocation().distanceSquared(entityLoc) > 1.0) {
                                        vec = nearby.getLocation().toVector().subtract(entityLoc.toVector()).normalize().multiply(kb);
                                    } else {
                                        vec = new Vector(0, 1, 0);
                                    }

                                    if (!(nearby instanceof Player)) {
                                        nearby.setVelocity(vec);
                                    } else {
                                        Player player = (Player)nearby;
                                        GamePlayer gp = game.getGamePlayer(player);
                                        if (gp != null && game.isSpectator(player)) {
                                            player.setVelocity(vec);
                                        }
                                    }
                                }
                            }

                            return;
                        }

                        block = (Block)iterator.next();
                    } while(block.hasMetadata("bedwars") && !this.isStainedGlass(block));

                    iterator.remove();
                }
            }
        }
    }

    private Cancellable callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
        return (Cancellable)event;
    }

    @EventHandler
    public void onBlockFire(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(true);
        }

    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onEntitySpawn(ItemSpawnEvent event) {
        if (BedUtils.isBed(event.getEntity().getItemStack().getType())) {
            event.setCancelled(true);
        }

    }

    public static void addBlock(Game game, Block block) {
        Set<Block> blocks = (Set)GAME_BLOCKS.get(game);
        if (blocks == null) {
            GAME_BLOCKS.put(game, blocks = new HashSet());
        }

        block.setMetadata("bedwars", GamePlayerListener.EMPTY);
        ((Set)blocks).add(block);
    }

    public static void addBlocks(Game game, Collection<Block> blocks) {
        Set<Block> blocks1 = (Set)GAME_BLOCKS.get(game);
        if (blocks1 == null) {
            GAME_BLOCKS.put(game, blocks1 = new HashSet());
        }

        Iterator var3 = blocks.iterator();

        while(var3.hasNext()) {
            Block block = (Block)var3.next();
            block.setMetadata("bedwars", GamePlayerListener.EMPTY);
            ((Set)blocks1).add(block);
        }

    }

    public static void resetArena(Game game) {
        Set<Block> blocks = (Set)GAME_BLOCKS.get(game);
        if (blocks != null) {
            Bedwars instance = Bedwars.getInstance();

            Block block;
            for(Iterator var3 = blocks.iterator(); var3.hasNext(); block.removeMetadata("bedwars", instance)) {
                block = (Block)var3.next();
                if (block.getType() != Material.AIR) {
                    block.setType(Material.AIR);
                }
            }

            blocks.clear();
        }

    }

    private boolean isStainedGlass(Block block) {
        return block.getType().toString().contains("STAINED_GLASS");
    }
}
