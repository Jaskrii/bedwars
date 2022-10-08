package me.jaskri.Game;

import me.jaskri.API.Generator.*;
import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.arena.BedwarsBed;
import me.jaskri.API.arena.Region;
import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Game.GameState;
import me.jaskri.API.Game.player.ArmorType;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
import me.jaskri.bedwars.API.Generator.*;
import me.jaskri.API.Group.Group;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.Shop.Item.TieredItemStack;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Team.Team;
import me.jaskri.API.Upgrade.Upgrade;
import me.jaskri.API.User.User;
import me.jaskri.API.User.UserStatistics;
import me.jaskri.API.bed.BedBreakEvent;
import me.jaskri.API.events.Player.GamePlayerDisconnectEvent;
import me.jaskri.API.events.Player.GamePlayerEliminateEvent;
import me.jaskri.API.events.Player.GamePlayerReconnectEvent;
import me.jaskri.API.events.Player.GamePlayerRespawnEvent;
import me.jaskri.API.events.game.GameEndEvent;
import me.jaskri.API.events.game.GameJoinEvent;
import me.jaskri.API.events.game.GameQuitEvent;
import me.jaskri.API.events.team.TeamEliminationEvent;
import me.jaskri.Arena.BedwarsArena;
import me.jaskri.bedwars.Bedwars;
import me.jaskri.bedwars.BedwarsItems;
import me.jaskri.bedwars.settings.TeamForgeSettings;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.function.Predicate;

public final class BedwarsGame extends AbstractGame{

    private final Map<Resource, List<TieredGenerator>> generators = new HashMap<>();
    private final Map<Team, TeamData> team_data = new EnumMap<Team, TeamData>(Team.class);
    private final Map<Team, TeamData> eliminated = new EnumMap(Team.class);
    private final Map<UUID, PlayerData> player_data = new HashMap<>();
    private final List<Shopkeeper> shopkeepers = new ArrayList();
    private final List<Upgrade> upgraders = new ArrayList();
    private final List<Hologram> holograms = new ArrayList();
    private final Set<Group> groups = new HashSet();
    private final Set<Player> players = new HashSet();
    private final Map<Block, Material> broken_beds = new HashMap();
    private final Map<Block, BlockFace> beds_faces = new HashMap();
    private Map<Block, Material> waiting_blocks;
    private GameCountdown gameCountdown = new GameCountdown(this);
    private BedwarsGameManager gameManager = new BedwarsGameManager(this);
    private UUID uuid = UUID.randomUUID();
    private boolean isLocked;

    public BedwarsGame(BedwarsArena arena) {
        super(arena);
    }

    private boolean canStart() {
        return !this.isLocked && !this.hasStarted && this.players.size() >= Bedwars.getInstance().getSettings().getMinimumPlayers(this.mode);
    }

    public boolean startGame() {
        if (!this.canStart()) {
            return false;
        } else {
            TeamForgeSettings settings = Bedwars.getInstance().getTeamForgeSettings();
            Set<DropItem> items = new HashSet();
            Iterator var3 = settings.getTeamDrops().iterator();

            while(var3.hasNext()) {
                Resource rsc = (Resource)var3.next();
                GeneratorSpeed speed = this.arena.getGeneratorSpeed();
                ItemStack item = new ItemStack(rsc.getMaterial());
                items.add(new DropItem(item, speed.getDropsPerMinute(rsc), settings.getDropLimit(this.mode, rsc)));
            }

            List<Team> emptyTeams = new ArrayList();
            Iterator<Team> teams = this.arena.getTeams().iterator();
            Iterator<Player> players = this.players.iterator();
            Iterator<Group> groups = this.groups.iterator();

            while(teams.hasNext()) {
                Team team = (Team)teams.next();
                Location loc = this.arena.getTeamSpawnPoint(team);
                Set<GamePlayer> team_players = new HashSet(this.mode.getTeamMax());
                if (groups.hasNext()) {
                    Group group = (Group)groups.next();
                    Iterator var11 = group.getPlayers().iterator();

                    while(var11.hasNext()) {
                        Player player = (Player)var11.next();
                        if (this.players.contains(player)) {
                            team_players.add(this.createGamePlayer(player, team, loc));
                        }
                    }
                } else {
                    for(int i = 0; i < this.mode.getTeamMax() && players.hasNext(); ++i) {
                        team_players.add(this.createGamePlayer((Player)players.next(), team, loc));
                    }
                }

                Location genLoc = this.arena.getTeamGenLocation(team);
                TeamGenerator gen = null;
                if (genLoc != null) {
                    gen = new TeamResourceGenerator(genLoc, items);
                    gen.start();
                }

                Location teamShop = this.arena.getTeamShop(team);
                if (teamShop != null) {
                    this.spawnTeamShop(teamShop);
                }

                Location teamUpgr = this.arena.getTeamUpgrade(team);
                if (teamUpgr != null) {
                    this.spawnTeamUpgrade(teamUpgr);
                }

                this.team_data.put(team, new TeamData(new BedwarsTeam(this, team), team_players, this.arena.getTeamBed(team), gen, loc));
                if (team_players.isEmpty()) {
                    emptyTeams.add(team);
                }
            }

            Bukkit.getScheduler().runTaskLater(Bedwars.getInstance(), () -> {
                Iterator var2 = emptyTeams.iterator();

                while(var2.hasNext()) {
                    Team team = (Team)var2.next();
                    if (this.getTeamPlayers(team).isEmpty()) {
                        this.breakTeamBed(team);
                    }
                }

            }, 2400L);
            Resource[] var21 = Resource.values();
            int var22 = var21.length;

            for(int var23 = 0; var23 < var22; ++var23) {
                Resource resource = var21[var23];
                Collection<Location> drop_loc = this.arena.getResourceGenLocations(resource);
                if (drop_loc != null && !drop_loc.isEmpty()) {
                    List<TieredGenerator> gens = new ArrayList(drop_loc.size());
                    List<GeneratorTier> tiers = Bedwars.getInstance().getMapForgeSettings().getGeneratorTiers(this.mode, resource);
                    Iterator var14 = drop_loc.iterator();

                    while(var14.hasNext()) {
                        Location loc = (Location)var14.next();
                        TieredGenerator gen = new MapResourceGenerator(resource, loc, tiers);
                        gen.start();
                        gens.add(gen);
                    }

                    this.generators.put(resource, gens);
                }
            }

            this.destroyWaitingRoom();
            this.gameManager.start();
            this.hasStarted = true;
            this.state = GameState.RUNNING;
            return true;
        }
    }

    private GamePlayer createGamePlayer(Player player, Team team, Location loc) {
        BedwarsPlayer gp = new BedwarsPlayer(player, this, team);
        gp.setArmorType(ArmorType.LEATHER);
        gp.getInventory().addItem(BedwarsItems.getInstance().getSword());
        PlayerManager.clear(player);
        PlayerManager.resetHealth(player);
        TeamUtils.setPlayerArmor(player, team, gp.getArmorType());
        this.player_data.put(player.getUniqueId(), new PlayerData(gp, team));
        player.getInventory().addItem(new ItemStack[]{BedwarsItems.getInstance().getSword()});
        player.teleport(loc);
        player.setGameMode(GameMode.SURVIVAL);
        return gp;
    }

    private void spawnTeamShop(Location loc) {
        Shopkeeper shopkeeper = Bedwars.getInstance().getNPCManager().createShopKeeper(this, loc);
        shopkeeper.spawn();
        Hologram hologram = new BedwarsHologram(loc.add(0.0, 1.0, 0.0), 0.3);
        hologram.addLine("&e&lRIGHT CLICK");
        hologram.addLine("&bITEM SHOP");
        this.shopkeepers.add(shopkeeper);
        this.holograms.add(hologram);
    }

    private void spawnTeamUpgrade(Location loc) {
         upgrader = Bedwars.getInstance().getNPCManager().createUpgrader(this, loc);
        upgrader.spawn();
        Hologram hologram = new BedwarsHologram(loc.add(0.0, 1.0, 0.0), 0.3);
        hologram.addLine("&e&lRIGHT CLICK");
        hologram.addLine("&bSOLO UPGRADES");
        this.upgraders.add(upgrader);
        this.holograms.add(hologram);
    }

    private void destroyWaitingRoom() {
        Region region = this.arena.getWaitingRoomRegion();
        if (region != null) {
            World world = region.getWorld();
            Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                int minX = (int)region.getMinX();
                int minY = (int)region.getMinY();
                int minZ = (int)region.getMinZ();
                int maxX = (int)region.getMaxX();
                int maxY = (int)region.getMaxY();
                int maxZ = (int)region.getMaxZ();
                int size = (maxX - minX) * (maxY - minY) * (maxZ - minZ);
                Map<Block, Material> blocks = new HashMap(size);

                for(int x = minX; x < maxX; ++x) {
                    for(int y = minY; y < maxY; ++y) {
                        for(int z = minZ; z < maxZ; ++z) {
                            Block block = world.getBlockAt(x, y, z);
                            if (block.getType() != Material.AIR) {
                                blocks.put(block, block.getType());
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }

                this.waiting_blocks = blocks;
            });
        }
    }

    public boolean stopGame() {
        if (!this.isLocked && this.hasStarted) {
            this.setState(GameState.ENDED);
            this.gameManager.stop();
            this.stop();
            return true;
        } else {
            return false;
        }
    }

    private void stop() {
        Bedwars instance = Bedwars.getInstance();
        List<GamePlayer> winners = new ArrayList();
        List<GamePlayer> losers = new ArrayList();
        Team winner = this.isGameEnded() ? (Team)this.team_data.keySet().iterator().next() : null;
        GameSummary summary = new GameSummary(this, winner);
        summary.send();
        Iterator var6 = this.player_data.values().iterator();

        while(var6.hasNext()) {
            PlayerData data = (PlayerData)var6.next();
            GamePlayer gp = data.getOwner();
            Player player = gp.getPlayer();
            User user = Bedwars.getInstance().getUser(player);
            UserStatistics userStats = user.getStatistics(this.mode);
            if (userStats == null) {
                userStats = new UserStatistics();
            }

            GameStatisticManager stats = gp.getStatisticManager();
            userStats.incrementStatistics(stats);
            user.setCoinsBalance(user.getCoinsBalance() + stats.getCoinsReward().getAmount());
            BedwarsLevel level = user.getLevel();
            Prestige next = LevelUtils.levelUp(level, stats.getExpReward().getAmount());
            user.setLevel(level);
            user.setPrestige(next);
            user.saveData();
            if (this.players.contains(player)) {
                if (this.isGameEnded() && gp.getTeam() == winner) {
                    Titles.sendTitle(player, 10, 80, 10, "§6§lVICTORY!", "");
                    winners.add(gp);
                } else {
                    Titles.sendTitle(player, 10, 80, 10, "§cGAME OVER!", "");
                    losers.add(gp);
                }

                player.setGameMode(GameMode.ADVENTURE);
                if (instance.isEnabled()) {
                    Bukkit.getScheduler().runTaskLater(instance, () -> {
                        this.remove(player);
                    }, 600L);
                } else {
                    this.remove(player);
                }
            }
        }

        GameEndEvent event = new GameEndEvent(this, winners, losers);
        Bukkit.getPluginManager().callEvent(event);
        Iterator var16 = this.shopkeepers.iterator();

        while(var16.hasNext()) {
            Shopkeeper shopKeeper = (Shopkeeper)var16.next();
            shopKeeper.remove();
        }

        this.shopkeepers.clear();
        var16 = this.upgraders.iterator();

        while(var16.hasNext()) {
            Upgrader upgrader = (Upgrader) var16.next();
            upgrader.remove();
        }

        this.upgraders.clear();
        var16 = this.holograms.iterator();

        while(var16.hasNext()) {
            Hologram hologram = (Hologram)var16.next();
            hologram.remove();
        }

        this.holograms.clear();
        var16 = this.team_data.values().iterator();

        TeamData eliminated_team;
        while(var16.hasNext()) {
            eliminated_team = (TeamData)var16.next();
            if (eliminated_team.gen != null) {
                eliminated_team.gen.stop();
            }
        }

        this.team_data.clear();
        var16 = this.eliminated.values().iterator();

        while(var16.hasNext()) {
            eliminated_team = (TeamData)var16.next();
            if (eliminated_team.gen != null) {
                eliminated_team.gen.stop();
            }
        }

        this.eliminated.clear();
        var16 = this.generators.values().iterator();

        while(var16.hasNext()) {
            List<TieredGenerator> generators = (List)var16.next();
            Iterator var22 = generators.iterator();

            while(var22.hasNext()) {
                TieredGenerator gen = (TieredGenerator)var22.next();
                gen.stop();
            }

            generators.clear();
        }

        this.generators.clear();
        this.isLocked = true;
        this.setState(GameState.RESETTING);
        Runnable reset = () -> {
            this.hasStarted = false;
            this.resetArena();
            this.isLocked = false;
            this.setState(GameState.WAITING);
        };
        if (instance.isEnabled()) {
            Bukkit.getScheduler().runTaskLater(instance, reset, 800L);
        } else {
            reset.run();
        }

    }

    public void resetArena() {
        MapListener.resetArena(this);
        Iterator var1;
        Map.Entry entry;
        if (this.waiting_blocks != null) {
            var1 = this.waiting_blocks.entrySet().iterator();

            while(var1.hasNext()) {
                entry = (Map.Entry)var1.next();
                ((Block)entry.getKey()).setType((Material)entry.getValue());
            }

            this.waiting_blocks.clear();
        }

        var1 = this.broken_beds.entrySet().iterator();

        while(var1.hasNext()) {
            entry = (Map.Entry)var1.next();
            Block block = (Block)entry.getKey();
            BedUtils.placeBed(block, (BlockFace)this.beds_faces.get(block), (Material)entry.getValue());
        }

        this.broken_beds.clear();
        this.beds_faces.clear();
    }

    public boolean addPlayer(Player player) {
        if (!this.canAddPlayer(player)) {
            return false;
        } else {
            String message = "§7" + player.getDisplayName() + " §ehas joined (§b" + (this.size() + 1) + "§e/&b" + this.mode.getGameMax() + "§e)!";
            GameJoinEvent event = new GameJoinEvent(this, player, message);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return false;
            } else {
                Game oldGame = getPlayerGame(player);
                if (oldGame != null) {
                    oldGame.removePlayer(player);
                }

                PlayerManager.clear(player);
                PlayerManager.resetHealth(player);
                PlayerManager.resetFoodLevel(player);
                PlayerManager.resetScoreboard(player);
                player.teleport(this.arena.getWaitingRoomSpawnPoint());
                player.setGameMode(GameMode.ADVENTURE);
                player.setPlayerTime((long)this.arena.getTime(), false);
                player.getEnderChest().clear();
                BedwarsLevel.setForPlayer(player, Bedwars.getInstance().getUser(player).getDisplayLevel());
                this.gameCountdown.addPlayer(player);
                this.players.add(player);
                this.broadcastMessage(event.getJoinMessage());
                PLAYERS_GAME.put(player.getUniqueId(), this);
                return true;
            }
        }
    }

    public boolean canAddPlayer(Player player) {
        return !this.isLocked && !this.hasStarted && player != null && !this.contains(player);
    }

    private void remove(Player player) {
        PlayerManager.clear(player);
        PlayerManager.resetScoreboard(player);
        PlayerManager.resetHealth(player);
        PlayerManager.resetLevel(player);
        PlayerManager.resetTime(player);
        player.getEnderChest().clear();
        Bedwars.getInstance().sendPlayerToLobby(this, player, "Removed from game");
        this.players.remove(player);
        PLAYERS_GAME.remove(player.getUniqueId());
    }

    public boolean removePlayer(Player player) {
        if (!this.isLocked && this.contains(player)) {
            String message = ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + "has quit!";
            GameQuitEvent event = new GameQuitEvent(this, player, message);
            Bukkit.getPluginManager().callEvent(event);
            this.gameCountdown.removePlayer(player);
            this.remove(player);
            this.broadcastMessage(event.getQuitMessage());
            return true;
        } else {
            return false;
        }
    }

    public boolean addGroup(Group group) {
        if (!this.canAddGroup(group)) {
            return false;
        } else {
            Iterator var2 = group.getPlayers().iterator();

            while(var2.hasNext()) {
                Player player = (Player)var2.next();
                this.addPlayer(player);
            }

            return this.groups.add(group);
        }
    }

    public boolean canAddGroup(Group group) {
        if (!this.isLocked && !this.hasStarted && group != null && !this.isFull()) {
            return group.size() + this.players.size() <= this.mode.getGameMax();
        } else {
            return false;
        }
    }

    public boolean removeGroup(Group group) {
        if (!this.isLocked && group != null) {
            Iterator var2 = group.getPlayers().iterator();

            while(var2.hasNext()) {
                Player player = (Player)var2.next();
                this.removePlayer(player);
            }

            return this.groups.remove(group);
        } else {
            return false;
        }
    }

    public boolean killPlayer(Player player, String message, int respawn) {
        if (player != null && respawn >= 0 && this.contains(player)) {
            GamePlayer gpDead = this.getGamePlayer(player);
            gpDead.getStatisticManager().incrementStatistic(GameStatistic.DEATHS, 1);
            if (message == null) {
                message = this.getDisplayName(gpDead) + " §7died!";
            }

            boolean hasBed = this.hasBed(gpDead.getTeam());
            if (!hasBed) {
                message = message + " §b§lFINAL KILL!";
            }

            this.broadcastMessage(message);
            if (!hasBed) {
                this.eliminate(gpDead);
            } else {
                this.setRespawnSpectator(player, respawn);
            }

            this.checkTeamElimination(gpDead.getTeam());
            return true;
        } else {
            return false;
        }
    }

    public boolean killPlayer(Player player, String message) {
        return this.killPlayer(player, message, Bedwars.getInstance().getSettings().getRespawnTime());
    }

    public boolean killPlayer(Player player) {
        return this.killPlayer(player, (String)null);
    }

    public boolean eliminatePlayer(Player player) {
        GamePlayer gp = this.getGamePlayer(player);
        if (gp == null) {
            return false;
        } else {
            this.eliminate(gp);
            this.checkTeamElimination(gp.getTeam());
            return true;
        }
    }

    private void checkTeamElimination(Team team) {
        TeamData data = (TeamData)this.team_data.get(team);
        if (this.isTeamEmpty(data)) {
            this.eliminate(data.players, team);
        }

    }

    public boolean isEliminated(Player player) {
        if (player == null) {
            return false;
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            return data != null ? data.isEliminated : true;
        }
    }

    public boolean eliminateTeam(Team team) {
        TeamData data = (TeamData)this.team_data.get(team);
        if (data == null) {
            return false;
        } else {
            this.eliminate(this.getTeamPlayers(team), team);
            return true;
        }
    }

    public boolean isEliminated(Team team) {
        return this.eliminated.containsKey(team);
    }

    public boolean breakTeamBed(Team team, Player destroyer) {
        if (team == null) {
            return false;
        } else {
            TeamData teamData = (TeamData)this.team_data.get(team);
            if (teamData == null) {
                return false;
            } else {
                BedwarsBed bed = teamData.bed;
                if (bed != null && BedUtils.isBed(bed)) {
                    this.putInBeds(teamData.bed);
                    if (destroyer == null) {
                        BedUtils.breakBed(bed);
                        this.alertBedDestruction(team, (String)null, (String)null);
                    } else {
                        PlayerData data = (PlayerData)this.player_data.get(destroyer.getUniqueId());
                        if (data == null || data.isSpectator) {
                            return false;
                        }

                        GamePlayer owner = data.getOwner();
                        if (owner.getTeam() == team) {
                            destroyer.sendMessage("§cYou can't destroy your own bed!");
                            return false;
                        }

                        String message = this.getBedDestructionMessage(owner, team);
                        BedBreakEvent bwEvent = new BedBreakEvent(owner, bed, message);
                        Bukkit.getPluginManager().callEvent(bwEvent);
                        if (bwEvent.isCancelled()) {
                            return false;
                        }

                        BedUtils.breakBed(bed);
                        owner.getStatisticManager().incrementStatistic(GameStatistic.BED_BROKEN, 1);
                        String breakMessage = "§lBED DESTRUCTION > " + bwEvent.getBreakMessage();
                        String lostMessage = "§lBED DESTRUCTION > §7Your bed was destroyed by " + this.getDisplayName(owner);
                        this.alertBedDestruction(team, breakMessage, lostMessage);
                        owner.getStatisticManager().getCoinsReward().increment(20);
                        destroyer.sendMessage("§6+20 coins! (Bed Destroyed)");
                    }

                    if (this.isTeamEmpty(teamData)) {
                        this.eliminate(teamData.players, team);
                    }

                    return false;
                } else {
                    return false;
                }
            }
        }
    }

    private void putInBeds(BedwarsBed bed) {
        Block foot = bed.getFoot();
        if (BedUtils.isBedHead(foot)) {
            foot = bed.getHead();
        }

        this.broken_beds.put(foot, foot.getType());
        this.beds_faces.put(foot, XBlock.getDirection(foot));
    }

    private boolean isTeamEmpty(TeamData data) {
        Iterator var2 = data.players.iterator();

        GamePlayer gp;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            gp = (GamePlayer)var2.next();
        } while(this.isEliminated(gp.getPlayer()));

        return false;
    }

    private void alertBedDestruction(Team team, String breakMessage, String lostMessage) {
        Iterator var4 = this.player_data.values().iterator();

        while(var4.hasNext()) {
            PlayerData data = (PlayerData)var4.next();
            if (!data.isEliminated) {
                GamePlayer owner = data.getOwner();
                Player p = owner.getPlayer();
                if (data.team == team) {
                    owner.getStatisticManager().incrementStatistic(GameStatistic.BED_LOST, 1);
                    if (!data.isDisconnected) {
                        if (lostMessage != null) {
                            p.sendMessage((String)null);
                            p.sendMessage(lostMessage);
                            p.sendMessage((String)null);
                        }

                        XSound.ENTITY_WITHER_DEATH.play(p, 1.0F, 1.0F);
                        Titles.sendTitle(p, 5, 30, 5, "§c§lBED DESTROYED!", "You will no longer respawn!");
                    }
                } else if (!data.isDisconnected && breakMessage != null) {
                    XSound.ENTITY_ENDER_DRAGON_GROWL.play(p, 1.0F, 1.0F);
                    p.sendMessage((String)null);
                    p.sendMessage(breakMessage);
                    p.sendMessage((String)null);
                }
            }
        }

    }

    public boolean breakTeamBed(Team team) {
        return this.breakTeamBed(team, (Player)null);
    }

    private String getBedDestructionMessage(GamePlayer destroyer, Team bedTeam) {
        StringBuilder builder = (new StringBuilder()).append(bedTeam.getColoredString()).append(" Bed§r §7was destroyed by ").append(this.getDisplayName(destroyer)).append("§7!");
        return builder.toString();
    }

    private String getDisplayName(GamePlayer gp) {
        return gp.getTeam().getChatColor() + gp.getPlayer().getDisplayName();
    }

    public boolean hasBed(Team team) {
        if (team == null) {
            return false;
        } else {
            BedwarsBed bed = this.arena.getTeamBed(team);
            return bed != null && BedUtils.isBed(bed);
        }
    }

    public boolean disconnect(Player player) {
        if (this.isLocked) {
            return false;
        } else if (!this.hasStarted) {
            return this.removePlayer(player);
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            if (data != null && !data.isDisconnected) {
                GamePlayer gp = data.getOwner();
                PlayerManager.clear(player);
                PlayerManager.resetHealth(player);
                PlayerManager.resetScoreboard(player);
                Bedwars.getInstance().sendPlayerToLobby(this, player, "Disconnected from game!");
                String message = this.getDisplayName(gp) + " &edisconnected!";
                GamePlayerDisconnectEvent event = new GamePlayerDisconnectEvent(gp, ChatUtils.format(message));
                Bukkit.getPluginManager().callEvent(event);
                final Team team = gp.getTeam();
                (new BukkitRunnable() {
                    public void run() {
                        Collection<GamePlayer> players = BedwarsGame.this.getTeamPlayers(team);
                        if (players.isEmpty()) {
                            BedwarsGame.this.breakTeamBed(team);
                        }
                    }
                }).runTaskLater(Bedwars.getInstance(), 2400L);
                this.players.remove(player);
                DISCONNECTED.put(player.getUniqueId(), this);
                this.broadcastMessage(event.getDisconnectMessage());
                if (!this.hasBed(gp.getTeam())) {
                    this.eliminatePlayer(player);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isDisconnected(Player player) {
        if (player == null) {
            return false;
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            return data != null ? data.isDisconnected : false;
        }
    }

    public boolean reconnect(Player player) {
        if (!this.isLocked && player != null) {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            if (data != null && data.isDisconnected) {
                BedwarsPlayer gp = data.getOwner();
                gp.initPlayer();
                player.setPlayerTime((long)this.arena.getTime(), false);
                if (this.isEliminated(player)) {
                    return this.setSpectator(player, true);
                } else {
                    String message = this.getDisplayName(data.getOwner()) + " &ereconnected!";
                    GamePlayerReconnectEvent event = new GamePlayerReconnectEvent(gp, ChatUtils.format(message));
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return false;
                    } else {
                        this.setRespawnSpectator(player, Bedwars.getInstance().getSettings().getReconnectRespawnTime());
                        this.broadcastMessage(event.getReconnectMessage());
                        this.players.add(player);
                        DISCONNECTED.remove(player.getUniqueId());
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean broadcastMessage(String message) {
        return this.broadcastMessage(message, (Predicate)null);
    }

    public boolean broadcastMessage(String message, Predicate<Player> predicate) {
        if (message == null) {
            return false;
        } else {
            message = ChatUtils.format(message);
            Iterator var3 = this.player_data.values().iterator();

            while(var3.hasNext()) {
                PlayerData data = (PlayerData)var3.next();
                if (!data.isDisconnected) {
                    data.getOwner().getPlayer().sendMessage(message);
                }
            }

            return true;
        }
    }

    public GameManager getManager() {
        return this.gameManager;
    }

    public GamePlayer getGamePlayer(Player player) {
        if (player == null) {
            return null;
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            return data != null ? data.getOwner() : null;
        }
    }

    public void setInvincible(Player player, boolean invincible) {
        if (player != null) {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            if (data != null) {
                data.setInvincible(invincible);
            }

        }
    }

    public boolean isInvincible(Player player) {
        if (player == null) {
            return false;
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            return data != null ? data.isInvincible : false;
        }
    }

    public boolean isSpectator(Player player) {
        if (player == null) {
            return false;
        } else {
            PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
            return data != null ? data.isSpectator : false;
        }
    }

    public GameTeam getGameTeam(Team team) {
        TeamData data = (TeamData)this.team_data.get(team);
        return data != null ? data.owner : null;
    }

    public TeamGenerator getTeamGenerator(Team team) {
        TeamData data = (TeamData)this.team_data.get(team);
        return data != null ? data.gen : null;
    }

    public Collection<TieredGenerator> getMapResourceGenerator(Resource resource) {
        List<TieredGenerator> gens = (List)this.generators.get(resource);
        return gens != null ? new ArrayList(gens) : new ArrayList(0);
    }

    public Collection<Player> getPlayers() {
        return new HashSet(this.players);
    }

    public Collection<GamePlayer> getGamePlayers() {
        Set<GamePlayer> result = new HashSet(this.player_data.size());
        Iterator var2 = this.player_data.values().iterator();

        while(var2.hasNext()) {
            PlayerData gp = (PlayerData)var2.next();
            if (!gp.isDisconnected) {
                result.add(gp.owner);
            }
        }

        return result;
    }

    public Collection<GamePlayer> getTeamPlayers(Team team) {
        if (team == null) {
            return null;
        } else {
            TeamData data = (TeamData)this.team_data.get(team);
            return data != null ? new HashSet(data.players) : new HashSet(0);
        }
    }

    public Collection<GameTeam> getTeams() {
        Set<GameTeam> result = new HashSet();
        Iterator var2 = this.team_data.values().iterator();

        while(var2.hasNext()) {
            TeamData data = (TeamData)var2.next();
            result.add(data.owner);
        }

        return result;
    }

    public boolean isFull() {
        if (!this.hasStarted) {
            return this.players.size() == this.mode.getGameMax();
        } else {
            return this.player_data.size() == this.mode.getGameMax();
        }
    }

    public boolean contains(Player player) {
        return this.players.contains(player);
    }

    public int size() {
        return this.players.size();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.uuid});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BedwarsGame)) {
            return false;
        } else {
            BedwarsGame other = (BedwarsGame)obj;
            return this.uuid.equals(other.uuid);
        }
    }

    public static BedwarsGame randomGame(Player player, GameMode mode) {
        return null;
    }

    public static BedwarsGame randomGame(GameMode mode) {
        BedwarsArena arena = randomArena(mode);
        return arena != null ? new BedwarsGame(arena) : null;
    }

    private void hidePlayer(Player player) {
        Iterator var2 = this.player_data.values().iterator();

        while(var2.hasNext()) {
            PlayerData data = (PlayerData)var2.next();
            if (data.isSpectator) {
                data.getOwner().getPlayer().hidePlayer(player);
            }
        }

    }

    private boolean setSpectator(Player player, boolean eliminated) {
        PlayerManager.clear(player);
        PlayerManager.resetHealth(player);
        PlayerManager.resetFoodLevel(player);
        PlayerManager.setFlying(player, true);
        PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
        data.setInvincible(true);
        data.setSpectator(true);
        if (!eliminated) {
            player.setPlayerListName(ChatColor.GRAY + player.getDisplayName());
        } else {
            data.setEliminated(true);
            player.setPlayerListName(ChatColor.WHITE + player.getDisplayName());
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        player.teleport(this.arena.getSpectatorSpawnPoint());
        player.setCanPickupItems(false);
        this.hidePlayer(player);
        return true;
    }

    private void setRespawnSpectator(final Player player, final int time) {
        this.setSpectator(player, false);
        (new BukkitRunnable() {
            int cd = time;

            public void run() {
                if (this.cd != 0) {
                    String subTitle = "§eYou will respawn in §c" + this.cd-- + " §eseconds";
                    Titles.sendTitle(player, 5, 20, 5, "§cYOU DIED!", subTitle);
                    player.sendMessage(subTitle);
                } else {
                    this.cancel();
                    PlayerData data = (PlayerData)BedwarsGame.this.player_data.get(player.getUniqueId());
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTask(Bedwars.getInstance(), () -> {
                        BedwarsGame.this.spawn(player);
                        PlayerManager.setFlying(player, false);
                        GamePlayerRespawnEvent event = new GamePlayerRespawnEvent(data.owner, "§eYou have respawned!");
                        Bukkit.getPluginManager().callEvent(event);
                        Titles.sendTitle(player, 5, 20, 5, "§aRESPAWNED!", "");
                        player.sendMessage(event.getRespawnMessage());
                    });
                    player.setCanPickupItems(true);
                    data.setSpectator(false);
                    scheduler.runTaskLaterAsynchronously(Bedwars.getInstance(), () -> {
                        data.setInvincible(false);
                    }, 100L);
                }
            }
        }).runTaskTimerAsynchronously(Bedwars.getInstance(), 0L, 20L);
    }

    private void spawn(Player player) {
        PlayerManager.clear(player);
        PlayerManager.resetHealth(player);
        PlayerData data = (PlayerData)this.player_data.get(player.getUniqueId());
        GamePlayer gp = data.getOwner();
        Team team = gp.getTeam();
        player.setPlayerListName(team.getColoredChar() + " " + player.getDisplayName());
        TeamUtils.setPlayerArmor(player, team, gp.getArmorType());
        Iterator var5 = gp.getInventory().getPermanentItems().iterator();

        while(var5.hasNext()) {
            ItemStack item = (ItemStack)var5.next();
            player.getInventory().addItem(new ItemStack[]{item});
        }

        var5 = gp.getInventory().getTieredItems().iterator();

        while(var5.hasNext()) {
            TieredItemStack tiered = (TieredItemStack)var5.next();
            if (tiered.hasPrevious()) {
                tiered.setCurrentTier(tiered.getPreviousTier());
            }

            ItemStack current = tiered.current();
            if (current != null) {
                player.getInventory().addItem(new ItemStack[]{current});
            }
        }

        TeamData teamData = (TeamData)this.team_data.get(team);
        if (teamData == null) {
            teamData = (TeamData)this.eliminated.get(team);
        }

        if (teamData != null) {
            GameTeam gameTeam = teamData.owner;
            gameTeam.getUpgradeManager().apply(gp);
            player.teleport(teamData.spawn);
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    private void eliminate(Collection<GamePlayer> players, Team team) {
        String message = team.getColoredString() + " Team §chas been eliminated!";
        TeamEliminationEvent event = new TeamEliminationEvent(this, team, message);
        Bukkit.getPluginManager().callEvent(event);
        Iterator var5 = players.iterator();

        while(var5.hasNext()) {
            GamePlayer gp = (GamePlayer)var5.next();
            if (!this.isEliminated(gp.getPlayer())) {
                this.eliminate(gp);
            }
        }

        var5 = this.player_data.values().iterator();

        while(var5.hasNext()) {
            PlayerData data = (PlayerData)var5.next();
            if (!data.isDisconnected) {
                Player player = data.getOwner().getPlayer();
                player.sendMessage((String)null);
                player.sendMessage("§lTEAM ELIMINATED > §r" + event.getEliminationMessage());
                player.sendMessage((String)null);
            }
        }

        this.eliminated.put(team, (TeamData)this.team_data.remove(team));
        if (this.isGameEnded()) {
            this.stopGame();
        }

    }

    private void eliminate(GamePlayer gp) {
        GamePlayerEliminateEvent event = new GamePlayerEliminateEvent(gp);
        Bukkit.getPluginManager().callEvent(event);
        gp.getStatisticManager().incrementStatistic(GameStatistic.FINAL_DEATHS, 1);
        this.setSpectator(gp.getPlayer(), true);
        gp.getPlayer().sendMessage("§cYou have been eliminated!");
    }

    private boolean isGameEnded() {
        return this.team_data.size() == 1;
    }

    static class TeamData {
        private GameTeam owner;
        private TeamGenerator gen;
        private Collection<GamePlayer> players;
        private BedwarsBed bed;
        private Location spawn;

        public TeamData(GameTeam owner, Collection<GamePlayer> players, BedwarsBed bed, TeamGenerator gen, Location spawn) {
            this.owner = owner;
            this.players = players;
            this.bed = bed;
            this.gen = gen;
            this.spawn = spawn;
        }
    }

    static class PlayerData {
        private BedwarsPlayer owner;
        private Team team;
        private boolean isDisconnected;
        private boolean isSpectator;
        private boolean isInvincible;
        private boolean isEliminated;

        public PlayerData(BedwarsPlayer owner, Team team) {
            this.owner = owner;
            this.team = team;
        }

        public BedwarsPlayer getOwner() {
            return this.owner;
        }

        public Team getTeam() {
            return this.team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public void setDisconnected(boolean isDisconnected) {
            this.isDisconnected = isDisconnected;
        }

        public boolean isDisconnected() {
            return this.isDisconnected;
        }

        public void setSpectator(boolean isSpectator) {
            this.isSpectator = isSpectator;
        }

        public boolean isSpectator() {
            return this.isSpectator;
        }

        public void setInvincible(boolean isInvincible) {
            this.isInvincible = isInvincible;
        }

        public boolean isInvincible() {
            return this.isInvincible;
        }

        public void setEliminated(boolean isEliminated) {
            this.isEliminated = isEliminated;
        }

        public boolean isEliminated() {
            return this.isEliminated;
        }
    }

    public static class PlayerData {

        public PlayerData(BedwarsPlayer owner, Team team) {
            this.getOwner() = owner;
            this.getTeam() = team;
        }

        public BedwarsPlayer getOwner() {
            return this.getOwner();
        }

        public Team getTeam() {
            return this.getTeam();
        }

        public void setTeam(Team team) {
            this.getTeam() = team;
        }

        public void setDisconnected(boolean isDisconnected) {
            this.isDisconnected() = isDisconnected;
        }

        public boolean isDisconnected() {
            return this.isDisconnected();
        }

        public void setSpectator(boolean isSpectator) {
            this.isSpectator() = isSpectator;
        }

        public boolean isSpectator() {
            return this.isSpectator();
        }

        public void setInvincible(boolean isInvincible) {
            this.isInvincible() = isInvincible;
        }

        public boolean isInvincible() {
            return this.isInvincible();
        }

        public void setEliminated(boolean isEliminated) {
            this.isEliminated() = isEliminated;
        }

        public boolean isEliminated() {
            return this.isEliminated();
        }
    }

    static class TeamData {

        private GameTeam owner;
        private TeamGenerator gen;
        private Collection<GamePlayer> players;
        private BedwarsBed bed;
        private Location spawn;

        public TeamData(GameTeam owner, Collection<GamePlayer> players, BedwarsBed bed, TeamGenerator gen, Location spawn) {
            this.owner = owner;
            this.players = players;
            this.bed = bed;
            this.gen = gen;
            this.spawn = spawn;
        }
    }
}
