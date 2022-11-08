package me.jaskri.Listener;

import me.jaskri.API.Entity.BedBug;
import me.jaskri.API.Entity.BodyGuard;
import me.jaskri.API.Entity.GameEntity;
import me.jaskri.API.Game.player.GameInventory;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Team.Team;
import me.jaskri.API.arena.Arena;
import me.jaskri.API.arena.BedwarsBed;
import me.jaskri.API.events.Player.*;
import me.jaskri.Game.AbstractGame;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Generator.TeamGenerator;
import me.jaskri.API.Generator.TieredGenerator;
import me.jaskri.Util.*;
import me.jaskri.bedwars.API.events.Player.*;
import me.jaskri.bedwars.Bedwars;
import me.jaskri.bedwars.BedwarsItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GamePlayerListener implements Listener {

    private static final Map<UUID, Integer> TRAP_SAFE = new HashMap();
    private static final Effect FOOTSTEP;
    private static final Effect CLOUD;
    public static final MetadataValue EMPTY = new FixedMetadataValue(Bedwars.getInstance(), (Object)null);

    public GamePlayerListener() {
    }

    @EventHandler
    public void onGamePlayerBedBreak(BlockBreakEvent event) {
        if (BedUtils.isBed(event.getBlock())) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && !game.isSpectator(player) && !game.isEliminated(player)) {
                BedwarsBed bed = BedUtils.getArenaBed(game.getArena(), event.getBlock());
                if (bed != null) {
                    game.breakTeamBed(bed.getTeam(), player);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerBedInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking() && BedUtils.isBed(event.getClickedBlock())) {
            if (AbstractGame.inGame(event.getPlayer())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onGamePlayerReconnect(PlayerJoinEvent event) {
        if (Bedwars.getInstance().getSettings().isAutoReconnect()) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getDisconnectedPlayerGame(player);
            if (game != null) {
                game.reconnect(player);
                event.setJoinMessage((String)null);
            }
        }
    }

    @EventHandler
    public void onGamePlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null) {
            game.disconnect(player);
            event.setQuitMessage((String)null);
        }
    }

    @EventHandler
    public void onGamePlayerItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null) {
            ItemStack drop = event.getItemDrop().getItemStack();
            GamePlayer gp = game.getGamePlayer(player);
            GameInventory inv = gp.getInventory();
            if (!inv.contains(drop, (item) -> {
                return item.getType() == drop.getType() && item.getAmount() == drop.getAmount();
            })) {
                checkForSword(gp);
                updateTeam(gp);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGamePlayerArmorClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            if (AbstractGame.inGame((Player)event.getWhoClicked())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onGamePlayerSwordPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null && game.hasStarted() && !game.isSpectator(player)) {
            if (isOtherSword(event.getItem().getItemStack().getType())) {
                player.getInventory().remove(BedwarsItems.getInstance().getSword().getType());
            }

            updateTeam(game.getGamePlayer(player));
        }
    }

    private static boolean isOtherSword(Material type) {
        if (type != Material.STONE_SWORD && type != Material.IRON_SWORD && type != Material.DIAMOND_SWORD) {
            return Version.getVersion().isNewAPI() ? type == Material.GOLDEN_SWORD : type == Material.getMaterial("GOLD_SWORD");
        } else {
            return true;
        }
    }

    public static void updateTeam(GamePlayer gp) {
        Game game = gp.getGame();
        GameTeam team = game.getGameTeam(gp.getTeam());
        if (Team!= null) {
            team.getUpgradeManager().apply(gp);
        }
    }

    public static void checkForSword(GamePlayer gp) {
        Inventory inv = gp.getPlayer().getInventory();
        ItemStack sword = BedwarsItems.getInstance().getSword();
        boolean hasOtherSword = false;
        int sword_index = -1;
        ItemStack[] content = inv.getContents();

        for(int i = 0; i < content.length; ++i) {
            ItemStack item = content[i];
            if (item != null) {
                if (item.getType() == sword.getType()) {
                    if (sword_index != -1) {
                        content[sword_index] = null;
                    }

                    sword_index = i;
                } else if (isOtherSword(item.getType())) {
                    if (sword_index != -1) {
                        content[sword_index] = null;
                    }

                    hasOtherSword = true;
                }
            }
        }

        if (!hasOtherSword && sword_index == -1) {
            inv.addItem(new ItemStack[]{BedwarsItems.getInstance().getSword()});
        }

    }

    @EventHandler
    public void onGamePlayerItemPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null && game.hasStarted()) {
            if (game.isSpectator(player)) {
                event.setCancelled(true);
            } else if (Bedwars.getInstance().getTeamForgeSettings().isResourceSplitting()) {
                Item item = event.getItem();
                if (item.hasMetadata("bedwars")) {
                    ItemStack stack = item.getItemStack();
                    GamePlayer gp = game.getGamePlayer(player);
                    Team team = gp.getTeam();
                    double radius = Bedwars.getInstance().getTeamForgeSettings().getSplitRadius();
                    Iterator var10 = item.getNearbyEntities(radius, radius, radius).iterator();

                    while(var10.hasNext()) {
                        Entity nearby = (Entity)var10.next();
                        if (nearby instanceof Player && !nearby.getUniqueId().equals(player.getUniqueId())) {
                            Player p = (Player)nearby;
                            GamePlayer nearbyGP = game.getGamePlayer(p);
                            if (nearbyGP != null && nearbyGP.getTeam() == team && !game.isSpectator(p)) {
                                p.getInventory().addItem(new ItemStack[]{stack});
                            }
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerPermItemCheck(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        if (inv != null && inv.getType() != InventoryType.PLAYER) {
            Player player = (Player)event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted()) {
                GamePlayer gp = game.getGamePlayer(player);
                GameInventory game_inv = gp.getInventory();

                for(int i = 0; i < inv.getSize(); ++i) {
                    ItemStack item = inv.getItem(i);
                    if (item != null && game_inv.contains(item, (inv_item) -> {
                        return inv_item.getType() == item.getType();
                    }) && !player.getInventory().contains(item.getType())) {
                        inv.setItem(i, (ItemStack)null);
                        player.getInventory().addItem(new ItemStack[]{item});
                    }
                }

                checkForSword(gp);
                updateTeam(gp);
            }
        }
    }

    @EventHandler
    public void onGamePlayerInvisConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTION) {
            final Player player = event.getPlayer();
            final Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted() && !game.isSpectator(player)) {
                final PotionMeta meta = (PotionMeta)event.getItem().getItemMeta();
                if (meta.hasCustomEffect(PotionEffectType.INVISIBILITY)) {
                    GamePlayer gp = game.getGamePlayer(player);
                    Team team = gp.getTeam();
                    Iterator var7 = game.getGamePlayers().iterator();

                    while(var7.hasNext()) {
                        GamePlayer other = (GamePlayer)var7.next();
                        if (other.getTeam() != team) {
                            other.getPlayer().hidePlayer(player);
                        }
                    }

                    if (!Version.getVersion().isNewAPI()) {
                        (new BukkitRunnable() {
                            Location loc = player.getLocation();
                            int time = 0;
                            int duration = this.getDuration();

                            private int getDuration() {
                                Iterator var1 = meta.getCustomEffects().iterator();

                                PotionEffect effect;
                                do {
                                    if (!var1.hasNext()) {
                                        return 0;
                                    }

                                    effect = (PotionEffect)var1.next();
                                } while(effect.getType() != PotionEffectType.INVISIBILITY);

                                return effect.getDuration();
                            }

                            public void run() {
                                if (this.time < this.duration && !game.isSpectator(player)) {
                                    this.time += 20;
                                    player.getWorld().playEffect(this.loc, GamePlayerListener.FOOTSTEP, 0);
                                } else {
                                    this.cancel();
                                }
                            }
                        }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 20L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerBridgeEggThrow(ProjectileLaunchEvent event) {
        if (event.getEntityType() == EntityType.EGG) {
            Projectile projectile = event.getEntity();
            if (projectile.getShooter() instanceof Player) {
                Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
                    Player player = (Player)projectile.getShooter();
                    final Game game = AbstractGame.getPlayerGame(player);
                    if (game != null && game.hasStarted() && !game.isSpectator(player)) {
                        final GamePlayer gp = game.getGamePlayer(player);
                        (new BukkitRunnable() {
                            Collection<Block> blocks = new ArrayList();
                            XMaterial wool = TeamUtils.getTeamColoredWool(gp.getTeam());
                            int amount = 0;

                            public void run() {
                                if (!projectile.isDead() && this.amount < 35) {
                                    Location loc = projectile.getLocation();
                                    Block center = loc.getBlock();
                                    Block north = center.getRelative(BlockFace.NORTH);
                                    Block south = center.getRelative(BlockFace.SOUTH);
                                    Block east = center.getRelative(BlockFace.EAST);
                                    Block west = center.getRelative(BlockFace.WEST);
                                    ++this.amount;
                                    Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), () -> {
                                        if (center.getType() == Material.AIR) {
                                            XBlock.setType(center, this.wool);
                                            this.blocks.add(center);
                                        }

                                        if (north.getType() == Material.AIR) {
                                            XBlock.setType(north, this.wool);
                                            this.blocks.add(north);
                                        }

                                        if (south.getType() == Material.AIR) {
                                            XBlock.setType(south, this.wool);
                                            this.blocks.add(south);
                                        }

                                        if (east.getType() == Material.AIR) {
                                            XBlock.setType(east, this.wool);
                                            this.blocks.add(east);
                                        }

                                        if (west.getType() == Material.AIR) {
                                            XBlock.setType(west, this.wool);
                                            this.blocks.add(west);
                                        }

                                    }, 1L);
                                    loc.getWorld().playSound(loc, Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 100.0F, 0.0F);
                                } else {
                                    this.cancel();
                                    MapListener.addBlocks(game, this.blocks);
                                }
                            }
                        }).runTaskTimerAsynchronously(Bedwars.getInstance(), 2L, 1L);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onGamePlayerMagicMilkConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            Player player = event.getPlayer();
            if (AbstractGame.inRunningGame(player)) {
                player.getInventory().setItemInHand((ItemStack)null);
                TRAP_SAFE.put(player.getUniqueId(), 30);
                Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
                    TRAP_SAFE.remove(player.getUniqueId());
                }, 600L);
            }
        }
    }

    @EventHandler
    public void onGamePlayerSpongePlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SPONGE) {
            Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
                Player player = event.getPlayer();
                final Block block = event.getBlock();
                Game game = AbstractGame.getPlayerGame(player);
                if (game != null && game.hasStarted() && !game.isSpectator(player)) {
                    block.setMetadata("bedwars-sponge", new LazyMetadataValue(Bedwars.getInstance(), () -> {
                        return game;
                    }));
                    final Location center = block.getLocation().add(0.5, 0.0, 0.5);
                    (new BukkitRunnable() {
                        private World world = center.getWorld();
                        private int radius = 1;
                        private int time = 0;

                        public void run() {
                            if (this.time == 50) {
                                this.cancel();
                                Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                                    block.setType(Material.AIR);
                                });
                            } else {
                                this.time += 10;
                                ++this.radius;
                                if (Version.getVersion().isNewAPI()) {
                                    this.world.spawnParticle(Particle.CLOUD, center, this.radius * this.time, (double)this.radius, (double)this.radius, (double)this.radius);
                                } else {
                                    this.world.playEffect(center, GamePlayerListener.CLOUD, this.radius * this.time, this.radius);
                                }

                                XSound.BLOCK_NOTE_BLOCK_PLING.play(center, 1.0F, 0.0F);
                            }
                        }
                    }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 10L);
                }
            });
        }
    }

    @EventHandler
    public void onGamePlayerSpongeBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SPONGE && event.getBlock().hasMetadata("bedwars-sponge")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGamePlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player)event.getDamager();
            Game game = AbstractGame.getPlayerGame(damager);
            if (game != null) {
                if (game.hasStarted() && !game.isSpectator(damager)) {
                    if (game.isInvincible(damager)) {
                        game.setInvincible(damager, false);
                        damager.sendMessage(ChatUtils.format("&cYou attacked someone and lost your invincibility!"));
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null) {
                if (game.hasStarted() && !game.isSpectator(player) && !game.isInvincible(player)) {
                    String deathMessage = null;
                    GamePlayer damaged = game.getGamePlayer(player);
                    GamePlayer damager;
                    if (event.getDamager() instanceof Player) {
                        if ((damager = game.getGamePlayer((Player)event.getDamager())) == null) {
                            return;
                        }

                        GamePlayerDamageByGamePlayerEvent bwEvent = new GamePlayerDamageByGamePlayerEvent(damaged, damager, event.getCause());
                        Bukkit.getPluginManager().callEvent(bwEvent);
                        if (bwEvent.isCancelled()) {
                            event.setCancelled(true);
                            return;
                        }

                        if (player.getHealth() - event.getFinalDamage() > 0.0) {
                            return;
                        }

                        String message = this.getDeathMessage(damaged, damager);
                        GamePlayerDeathByGamePlayerEvent bwEvent2 = new GamePlayerDeathByGamePlayerEvent(damaged, damager, event.getCause(), message);
                        Bukkit.getPluginManager().callEvent(bwEvent2);
                        deathMessage = bwEvent2.getDeathMessage();
                        this.checkDropsAndEnderChestContent(game, damaged, damager);
                    } else {
                        GameEntity entity = Bedwars.getInstance().getEntityManager().getGameEntity(event.getDamager());
                        if (entity == null) {
                            return;
                        }

                        GamePlayerDamageByGameEntityEvent bwEvent = new GamePlayerDamageByGameEntityEvent(damaged, entity, event.getCause());
                        Bukkit.getPluginManager().callEvent(bwEvent);
                        if (bwEvent.isCancelled()) {
                            event.setCancelled(true);
                            return;
                        }

                        if (player.getHealth() - event.getFinalDamage() > 0.0) {
                            return;
                        }

                        String message = this.getDeathMessage(damaged, entity.getOwner());
                        GamePlayerDeathByGameEntityEvent bwEvent2 = new GamePlayerDeathByGameEntityEvent(damaged, entity, event.getCause(), message);
                        Bukkit.getPluginManager().callEvent(bwEvent2);
                        deathMessage = bwEvent2.getDeathMessage();
                        this.checkDropsAndEnderChestContent(game, damaged, damager = entity.getOwner());
                    }

                    if (!game.hasBed(damaged.getTeam())) {
                        damager.getStatisticManager().incrementStatistic(GameStatistic.FINAL_KILLS, 1);
                    } else {
                        damager.getStatisticManager().incrementStatistic(GameStatistic.KILLS, 1);
                    }

                    game.killPlayer(player, deathMessage);
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK && (!Bedwars.getInstance().getVersion().isNewAPI() || cause != DamageCause.ENTITY_SWEEP_ATTACK)) {
                Player player = (Player)event.getEntity();
                Game game = AbstractGame.getPlayerGame(player);
                if (game != null) {
                    if (game.hasStarted() && !game.isSpectator(player) && !game.isInvincible(player)) {
                        GamePlayer gp = game.getGamePlayer(player);
                        if (!(player.getHealth() - event.getFinalDamage() > 0.0)) {
                            Player killer = player.getKiller();
                            GamePlayer gpKiller = null;
                            if (killer != null) {
                                GamePlayer gpKiller2 = game.getGamePlayer(killer);
                                if (gpKiller2 != null && game.isSpectator(killer)) {
                                    gpKiller = gpKiller2;
                                }
                            }

                            String message = this.getDisplayName(gp) + " §7died!";
                            switch (cause) {
                                case FALL:
                                    if (gpKiller != null) {
                                        message = this.getDisplayName(gp) + " §7died from fall damage while running from " + this.getDisplayName(gpKiller);
                                    }
                                case BLOCK_EXPLOSION:
                                case ENTITY_EXPLOSION:
                                case CONTACT:
                                case CUSTOM:
                                case FALLING_BLOCK:
                                case FIRE:
                                case FIRE_TICK:
                                case LAVA:
                                case PROJECTILE:
                                case SUICIDE:
                                case THORNS:
                                default:
                                    break;
                                case DROWNING:
                                    message = this.getDisplayName(gp) + " §7drowned!";
                                    break;
                                case SUFFOCATION:
                                    message = this.getDisplayName(gp) + "§7suffocated!";
                            }

                            GamePlayerDeathEvent bwEvent = new GamePlayerDeathEvent(gp, cause, message);
                            Bukkit.getPluginManager().callEvent(bwEvent);
                            this.checkDropsAndEnderChestContent(game, gp, gpKiller);
                            game.killPlayer(player, bwEvent.getDeathMessage());
                            if (gpKiller != null) {
                                if (game.hasBed(gp.getTeam())) {
                                    gpKiller.getStatisticManager().incrementStatistic(GameStatistic.KILLS, 1);
                                } else {
                                    gpKiller.getStatisticManager().incrementStatistic(GameStatistic.FINAL_KILLS, 1);
                                    gpKiller.getStatisticManager().getCoinsReward(2).increment();
                                    killer.sendMessage("§6+5 coins! (Final Kill)");
                                }
                            }

                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerVoidFall(PlayerMoveEvent event) {
        if (!(event.getTo().getY() > 0.0)) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null) {
                if (!game.hasStarted()) {
                    player.teleport(game.getArena().getWaitingRoomSpawnPoint());
                } else if (game.isSpectator(player)) {
                    player.teleport(game.getArena().getSpectatorSpawnPoint());
                } else {
                    GamePlayer gp = game.getGamePlayer(player);
                    StringBuilder message = (new StringBuilder()).append(this.getDisplayName(gp));
                    GamePlayer killer = game.getGamePlayer(player.getKiller());
                    if (killer == null) {
                        message.append(" §7fell into the void!");
                    } else {
                        message.append(" §7was thrown into the void by ").append(this.getDisplayName(killer)).append("§7!");
                    }

                    this.checkDropsAndEnderChestContent(game, gp, killer);
                    GamePlayerDeathEvent bwEvent = new GamePlayerDeathEvent(killer, EntityDamageEvent.DamageCause.VOID, message.toString());
                    Bukkit.getPluginManager().callEvent(bwEvent);
                    game.killPlayer(player, bwEvent.getDeathMessage());
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerChestInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Chest) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted()) {
                if (game.isSpectator(player)) {
                    event.setCancelled(true);
                } else {
                    Arena arena = game.getArena();
                    Team chestTeam = null;
                    Team[] var6 = Team.values();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Team team = var6[var8];
                        Chest chest = arena.getTeamChest(team);
                        if (chest != null && chest.getLocation().equals(event.getClickedBlock().getLocation())) {
                            chestTeam = team;
                            break;
                        }
                    }

                    GamePlayer gp = game.getGamePlayer(player);
                    if (chestTeam != null && chestTeam != gp.getTeam() && !game.isEliminated(chestTeam)) {
                        String message = "§cYou can't open " + chestTeam.getColoredString() + "team §cchest! Team is not eliminated!";
                        player.sendMessage(message);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGamePlayerBodyGuardSpawn(PlayerInteractEvent event) {
        if (event.hasBlock() && event.getMaterial().toString().contains("_SPAWN_EGG")) {
            Player player = event.getPlayer();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted() && !game.isSpectator(player)) {
                GamePlayer gp = game.getGamePlayer(player);
                BodyGuard guard = Bedwars.getInstance().getEntityManager().createBodyGuard(game, gp.getTeam(), gp, event.getClickedBlock().getLocation().add(0.0, 1.0, 0.0));
                guard.getSpawnCategory();
                player.sendMessage("§aYou spawned a Body Guard! It will be removed after 60 seconds!");
                Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
                    guard.remove();
                }, 1200L);
            }
        }
    }

    @EventHandler
    public void onGamePlayerHungerLost(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (AbstractGame.inGame((Player)event.getEntity())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onGamePlayerItemCraft(CraftItemEvent event) {
        if (AbstractGame.inGame((Player)event.getWhoClicked())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onGamePlayerPortalEnter(PlayerPortalEvent event) {
        if (AbstractGame.inGame(event.getPlayer())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onGamePlayerBedBugSpawn(ProjectileHitEvent event) {
        if (event.getEntityType() == EntityType.SNOWBALL && event.getEntity().getShooter() instanceof Player) {
            Projectile projectile = event.getEntity();
            Player player = (Player)projectile.getShooter();
            Game game = AbstractGame.getPlayerGame(player);
            if (game != null && game.hasStarted() && !game.isSpectator(player)) {
                GamePlayer gp = game.getGamePlayer(player);
                BedBug bedbug = Bedwars.getInstance().getEntityManager().createBedBug(game, gp.getTeam(), gp, projectile.getLocation());
                bedbug.getSpawnCategory();
                player.sendMessage("§aYou spawned a Bed Bug! It will be removed after 15 seconds!");
                Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
                    bedbug.remove();
                }, 300L);
            }
        }
    }

    @EventHandler
    public void onGamePlayerPlaceBlockNearbyGenerator(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Game game = AbstractGame.getPlayerGame(player);
        if (game != null) {
            Block block = event.getBlock();
            Resource[] var5 = Resource.values();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Resource rsc = var5[var7];
                Collection<TieredGenerator> gens = game.getMapResourceGenerator(rsc);
                if (gens != null && !gens.isEmpty()) {
                    Iterator var10 = gens.iterator();

                    while(var10.hasNext()) {
                        TieredGenerator gen = (TieredGenerator)var10.next();
                        if (!(gen.getDropLocation().getBlock().getLocation().distanceSquared(block.getLocation()) > 9.0)) {
                            player.sendMessage("§cYou can't place blocks here!");
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }

        }
    }

    public static boolean isTrapSafe(Player player) {
        return player != null && TRAP_SAFE.containsKey(player.getUniqueId());
    }

    private void checkDropsAndEnderChestContent(Game game, GamePlayer deadgp, GamePlayer killergp) {
        Player dead = deadgp.getPlayer();
        Player killer = killergp != null ? killergp.getPlayer() : null;
        Map<Resource, Integer> drops = killer != null ? this.checkDrops(dead, killer) : null;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
            if (!game.hasBed(deadgp.getTeam())) {
                TeamGenerator gen = game.getTeamGenerator(deadgp.getTeam());
                if (gen == null) {
                    return;
                }

                Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                    this.dropEnderChestContent(dead, gen.getDropLocation());
                });
                if (killer != null) {
                    killer.sendMessage("§aContents of " + dead.getDisplayName() + "'s Ender Chest have been dropped in their fountain.");
                }
            }

            if (killer != null) {
                Inventory toInv = killer.getInventory();
                Iterator var7 = drops.entrySet().iterator();

                while(var7.hasNext()) {
                    Map.Entry<Resource, Integer> entry = (Map.Entry)var7.next();
                    Resource rsc = (Resource)entry.getKey();
                    int amount = (Integer)entry.getValue();
                    toInv.addItem(new ItemStack[]{new ItemStack(rsc.getMaterial(), amount)});
                    killer.sendMessage(rsc.getChatColor() + "+" + amount + " " + rsc.toString());
                }
            }

        }, 1L);
    }

    private Map<Resource, Integer> checkDrops(Player from, Player to) {
        Map<Resource, Integer> result = new HashMap(4);
        ListIterator var4 = from.getInventory().iterator();

        while(var4.hasNext()) {
            ItemStack item = (ItemStack)var4.next();
            if (item != null) {
                Resource rsc = Resource.getByMaterial(item.getType());
                if (rsc != null) {
                    Integer amount = (Integer)result.get(rsc);
                    if (amount == null) {
                        result.put(rsc, item.getAmount());
                    } else {
                        result.put(rsc, amount + item.getAmount());
                    }
                }
            }
        }

        return result;
    }

    private void dropEnderChestContent(Player player, Location loc) {
        World world = loc.getWorld();
        ListIterator var4 = player.getEnderChest().iterator();

        while(var4.hasNext()) {
            ItemStack item = (ItemStack)var4.next();
            if (item != null) {
                world.dropItemNaturally(loc, item);
            }
        }

    }

    private String getDeathMessage(GamePlayer dead, GamePlayer killer) {
        return this.getDisplayName(dead) + " §7was killed by " + this.getDisplayName(killer) + "§7!";
    }

    private String getDisplayName(GamePlayer gp) {
        return gp.getTeam().getColor() + gp.getPlayer().getDisplayName();
    }

    static {
        if (!Version.getVersion().isNewAPI()) {
            FOOTSTEP = (Effect)Enum.valueOf(Effect.class, "FOOTSTEP");
            CLOUD = (Effect)Enum.valueOf(Effect.class, "CLOUD");
        } else {
            FOOTSTEP = null;
            CLOUD = null;
        }

    }
}
