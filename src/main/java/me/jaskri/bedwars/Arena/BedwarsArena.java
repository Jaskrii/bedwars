package me.jaskri.bedwars.Arena;


import com.google.common.base.Preconditions;
import me.jaskri.bedwars.API.PACKAGE.Game.GameMode;
import me.jaskri.bedwars.API.PACKAGE.Generator.GeneratorSpeed;
import me.jaskri.bedwars.API.PACKAGE.Generator.Resource;
import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.API.PACKAGE.arena.Arena;
import me.jaskri.bedwars.API.PACKAGE.arena.BedwarsBed;
import me.jaskri.bedwars.API.PACKAGE.arena.Region;
import me.jaskri.bedwars.Bedwarss;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public final class BedwarsArena extends Configuration {

    private static final Map<String, BedwarsArena> ARENAS = new HashMap();
    private Map<Resource, List<Location>> resource_gen = new HashMap();
    private Map<Team, BedwarsBed> team_bed = new EnumMap(Team.class);
    private Map<Team, Location> team_spawn = new EnumMap(Team.class);
    private Map<Team, Location> team_shop = new EnumMap(Team.class);
    private Map<Team, Location> team_upgr = new EnumMap(Team.class);
    private Map<Team, Location> team_gen = new EnumMap(Team.class);
    private Map<Team, Chest> team_chest = new EnumMap(Team.class);
    private Set<Team> teams = EnumSet.noneOf(Team.class);
    private Location spectator;
    private Location waiting;
    private Location dragon;
    private Location lobby;
    private Region waitingRegion;
    private Region arenaRegion;
    private GeneratorSpeed speed;
    private GameMode mode;
    private String mapName;
    private String name;
    private int time;
    private boolean enabled;
    private ConfigUtils utils;

    public BedwarsArena(String name) {
        super(new File(Bedwarss.getInstance().getDataFolder() + "/Arenas", name + ".yml"));
        Preconditions.checkNotNull(name, "Arena name cannot be null!");
        this.name = name;
        this.initArena();
    }

    private void initArena() {
        String name = this.name.toLowerCase();
        BedwarsArena arena = (BedwarsArena)ARENAS.get(name);
        if (arena == null) {
            ARENAS.put(name, this);
        } else {
            this.resource_gen = arena.resource_gen;
            this.team_bed = arena.team_bed;
            this.team_spawn = arena.team_spawn;
            this.team_shop = arena.team_shop;
            this.team_upgr = arena.team_upgr;
            this.team_gen = arena.team_gen;
            this.team_chest = arena.team_chest;
            this.teams = arena.teams;
            this.spectator = arena.spectator;
            this.waiting = arena.waiting;
            this.dragon = arena.dragon;
            this.lobby = arena.lobby;
            this.waitingRegion = arena.waitingRegion;
            this.arenaRegion = arena.arenaRegion;
            this.speed = arena.speed;
            this.mode = arena.mode;
            this.mapName = arena.mapName;
            this.name = arena.name;
            this.time = arena.time;
            this.enabled = arena.enabled;
            this.config = arena.config;
            this.utils = arena.utils;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getMapName() {
        return this.mapName;
    }

    public int getTime() {
        return this.time;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public Set<Team> getTeams() {
        return new HashSet(this.teams);
    }

    public List<Location> getResourceGenLocations(Resource resource) {
        if (resource == null) {
            return new ArrayList(0);
        } else {
            List<Location> locations = (List)this.resource_gen.get(resource);
            if (locations == null) {
                return new ArrayList(0);
            } else {
                List<Location> result = new ArrayList();
                Iterator var4 = locations.iterator();

                while(var4.hasNext()) {
                    Location loc = (Location)var4.next();
                    result.add(loc.clone());
                }

                return result;
            }
        }
    }

    public GeneratorSpeed getGeneratorSpeed() {
        return this.speed;
    }

    public Location getTeamShop(Team team) {
        if (team == null) {
            return null;
        } else {
            Location result = (Location)this.team_shop.get(team);
            return result != null ? result.clone() : null;
        }
    }

    public Location getTeamUpgrade(Team team) {
        if (team == null) {
            return null;
        } else {
            Location result = (Location)this.team_upgr.get(team);
            return result != null ? result.clone() : null;
        }
    }

    public Location getTeamGenLocation(Team team) {
        if (team == null) {
            return null;
        } else {
            Location result = (Location)this.team_gen.get(team);
            return result != null ? result.clone() : null;
        }
    }

    public Location getTeamSpawnPoint(Team team) {
        if (team == null) {
            return null;
        } else {
            Location result = (Location)this.team_spawn.get(team);
            return result != null ? result.clone() : null;
        }
    }

    public BedwarsBed getTeamBed(Team team) {
        return team != null ? (BedwarsBed)this.team_bed.get(team) : null;
    }

    public Chest getTeamChest(Team team) {
        return (Chest)this.team_chest.get(team);
    }

    public Location getDragonSpawnPoint() {
        return this.dragon != null ? this.dragon.clone() : null;
    }

    public Location getLobbySpawnPoint() {
        return this.lobby != null ? this.lobby.clone() : null;
    }

    public Location getSpectatorSpawnPoint() {
        return this.spectator != null ? this.spectator.clone() : null;
    }

    public Location getWaitingRoomSpawnPoint() {
        return this.waiting != null ? this.waiting.clone() : null;
    }

    public Region getWaitingRoomRegion() {
        return this.waitingRegion;
    }

    public Region getRegion() {
        return this.arenaRegion;
    }

    public void setMapName(String name) {
        this.mapName = name;
    }

    public void setArenaTime(int time) {
        this.time = time;
    }

    public void setMode(GameMode mode) {
        if (mode != null) {
            this.mode = mode;
        }

    }

    public void setTeamShop(Team team, Location loc) {
        if (team != null && loc != null) {
            this.team_shop.put(team, loc.clone());
        }

    }

    public void setTeamUpgrade(Team team, Location loc) {
        if (team != null && loc != null) {
            this.team_upgr.put(team, loc.clone());
        }

    }

    public void addResourceGenerator(Resource resource, Location loc) {
        if (resource != null && loc != null) {
            List<Location> locations = (List)this.resource_gen.get(resource);
            if (locations == null) {
                this.resource_gen.put(resource, locations = new ArrayList());
            }

            ((List)locations).add(loc.clone());
        }
    }

    public boolean removeResourceGenerator(Resource resource, int index) {
        if (resource != null && index >= 0) {
            List<Location> locations = (List)this.resource_gen.get(resource);
            if (locations != null && index < locations.size()) {
                return locations.remove(index) != null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void setGeneratorSpeed(GeneratorSpeed speed) {
        if (speed != null) {
            this.speed = speed;
        }

    }

    public void setTeamGenLocation(Team team, Location loc) {
        if (team != null && loc != null) {
            this.team_gen.put(team, loc.clone());
        }

    }

    public void setTeamSpawnPoint(Team team, Location loc) {
        if (team != null && loc != null) {
            this.team_spawn.put(team, loc.clone());
        }

    }

    public void setTeamBed(BedwarsBed bed) {
        if (bed != null) {
            this.team_bed.put(bed.getTeam(), bed);
        }

    }

    public void setTeamChest(Team team, Chest chest) {
        if (team != null && chest != null) {
            this.team_chest.put(team, chest);
        }

    }

    public void setDragonSpawnPoint(Location loc) {
        if (loc != null) {
            this.dragon = loc.clone();
        }

    }

    public void setLobbySpawnPoint(Location loc) {
        if (loc != null) {
            this.lobby = loc.clone();
        }

    }

    public void setSpectatorSpawnPoint(Location loc) {
        if (loc != null) {
            this.spectator = loc.clone();
        }

    }

    public void setWaitingRoomLocation(Location loc) {
        if (loc != null) {
            this.waiting = loc.clone();
        }

    }

    public void setWaitingRoomRegion(Region region) {
        if (region != null) {
            this.waitingRegion = region;
        }

    }

    public void setArenaRegion(Region region) {
        if (region != null) {
            this.arenaRegion = region;
        }

    }

    public void reloadArena() {
        this.reloadConfig();
        ConfigUtils utils = this.getConfigUtils();
        ConfigurationSection section = this.config.getConfigurationSection("Teams");
        if (section != null) {
            Iterator var3 = section.getKeys(false).iterator();

            while(var3.hasNext()) {
                String teamKey = (String)var3.next();
                Team team = Team.getByName(teamKey);
                if (team != null) {
                    Location spawnPoint = utils.getLocation("Teams." + team + ".team-spawn");
                    if (spawnPoint != null) {
                        this.team_spawn.put(team, spawnPoint);
                    }

                    Location upgr = utils.getLocation("Teams." + team + ".team-upgrade");
                    if (upgr != null) {
                        this.team_upgr.put(team, upgr);
                    }

                    Location shop = utils.getLocation("Teams." + team + ".team-shop");
                    if (shop != null) {
                        this.team_shop.put(team, shop);
                    }

                    Location generator = utils.getLocation("Teams." + team + ".generator");
                    if (generator != null) {
                        this.team_gen.put(team, generator);
                    }

                    Location chestLoc = utils.getLocation("Teams." + team + ".team-chest");
                    if (chestLoc != null) {
                        Block block = chestLoc.getBlock();
                        if (block instanceof Chest) {
                            this.team_chest.put(team, (Chest)block);
                        }
                    }

                    Location bedhead = utils.getLocation("Teams." + team + ".bed.head");
                    Location bedfoot = utils.getLocation("Teams." + team + ".bed.foot");
                    if (bedhead != null && bedfoot != null) {
                        this.team_bed.put(team, new BedwarsBed(team, bedhead.getBlock(), bedfoot.getBlock()));
                    }

                    this.teams.add(team);
                }
            }
        }

        ConfigurationSection genSection = this.config.getConfigurationSection("Generators");
        if (genSection != null) {
            Iterator var15 = genSection.getKeys(false).iterator();

            while(var15.hasNext()) {
                String genKey = (String)var15.next();
                Resource resource = Resource.getByName(genKey);
                if (resource != null && resource != Resource.FREE) {
                    this.resource_gen.put(resource, utils.getLocationList("Generators." + genKey));
                }
            }
        }

        this.waiting = utils.getLocation("Arena-info.Spawns.Waiting-room");
        this.spectator = utils.getLocation("Arena-info.Spawns.Spectator");
        this.dragon = utils.getLocation("Arena-info.Spawns.Dragon");
        this.lobby = utils.getLocation("Arena-info.Spawn.Lobby");
        this.waitingRegion = utils.getRegion("Arena-info.Regions.Waiting-room");
        this.arenaRegion = utils.getRegion("Arena-info.Regions.Map");
        this.speed = GeneratorSpeed.getByName(this.config.getString("Arena-settings.Generator-speed"));
        this.mode = GameMode.getByName(this.config.getString("Arena-settings.Mode"));
        this.time = this.config.getInt("Arena-settings.Time", 1000);
        this.mapName = this.config.getString("Arena-settings.Map-name");
        this.enabled = this.config.getBoolean("Arena-settings.Enabled");
    }

    public void saveArena() {
        this.config = new YamlConfiguration();
        ConfigurationSection info = this.config.createSection("Arena-info");
        if (this.waitingRegion != null) {
            info.set("Regions.Waiting-room.pos-1", LocationUtils.serialize(this.waitingRegion.getFirstPosition(), false));
            info.set("Regions.Waiting-room.pos-2", LocationUtils.serialize(this.waitingRegion.getSecondPosition(), false));
        }

        if (this.arenaRegion != null) {
            info.set("Regions.Map.pos-1", LocationUtils.serialize(this.arenaRegion.getFirstPosition(), false));
            info.set("Regions.Map.pos-2", LocationUtils.serialize(this.arenaRegion.getSecondPosition(), false));
        }

        if (this.waiting != null) {
            info.set("Spawns.Waiting-room", LocationUtils.serialize(this.waiting, true));
        }

        if (this.spectator != null) {
            info.set("Spawns.Spectator", LocationUtils.serialize(this.spectator, true));
        }

        if (this.dragon != null) {
            info.set("Spawns.Dragon", LocationUtils.serialize(this.dragon, true));
        }

        ConfigurationSection settings = this.config.createSection("Arena-settings");
        if (this.speed != null) {
            settings.set("Generator-speed", this.speed.getName());
        }

        if (this.mapName != null) {
            settings.set("Map-name", this.mapName);
        }

        if (this.mode != null) {
            settings.set("Mode", this.mode.getName());
        }

        settings.set("Time", this.time);
        settings.set("Enabled", this.enabled);
        Team[] var3 = Team.values();
        int var4 = var3.length;

        Location loc;
        for(int var5 = 0; var5 < var4; ++var5) {
            Team team = var3[var5];
            Location spawn = (Location)this.team_spawn.get(team);
            if (spawn != null) {
                this.config.set("Teams." + team + ".team-spawn", LocationUtils.serialize(spawn, true));
            }

            loc = (Location)this.team_upgr.get(team);
            if (loc != null) {
                this.config.set("Teams." + team + ".team-upgrade", LocationUtils.serialize(loc, true));
            }

            Location shop = (Location)this.team_shop.get(team);
            if (shop != null) {
                this.config.set("Teams." + team + ".team-shop", LocationUtils.serialize(shop, true));
            }

            Chest chest = (Chest)this.team_chest.get(team);
            if (chest != null) {
                this.config.set("Teams." + team + ".chest", LocationUtils.serialize(chest.getLocation(), false));
            }

            Location gen = (Location)this.team_gen.get(team);
            if (gen != null) {
                this.config.set("Teams." + team + ".generator", LocationUtils.serialize(gen, false));
            }

            BedwarsBed bed = (BedwarsBed)this.team_bed.get(team);
            if (bed != null) {
                this.config.set("Teams." + team + ".bed.head", LocationUtils.serialize(bed.getHead().getLocation(), false));
                this.config.set("Teams." + team + ".bed.foot", LocationUtils.serialize(bed.getFoot().getLocation(), false));
            }
        }

        Iterator var13 = this.resource_gen.entrySet().iterator();

        while(var13.hasNext()) {
            Map.Entry<Resource, List<Location>> entry = (Map.Entry)var13.next();
            List<Location> list = (List)entry.getValue();
            List<String> locations = new ArrayList(list.size());
            Iterator var17 = list.iterator();

            while(var17.hasNext()) {
                loc = (Location)var17.next();
                locations.add(LocationUtils.serialize(loc, false));
            }

            this.config.set("Generators." + ((Resource)entry.getKey()).getName(), locations);
        }

        this.saveConfig();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public boolean remove() {
        if (!this.file.delete()) {
            return false;
        } else {
            ARENAS.remove(this.name.toLowerCase());
            return true;
        }
    }

    public boolean isReady() {
        if (this.enabled && this.exists() && this.mode != null && this.speed != null) {
            if (this.spectator == null) {
                return false;
            } else if (this.waiting == null) {
                return false;
            } else {
                return this.arenaRegion == null ? false : this.isTeamsReady();
            }
        } else {
            return false;
        }
    }

    private boolean isTeamsReady() {
        if (this.teams.size() < 2) {
            return false;
        } else {
            int readyCount = 0;
            Iterator var2 = this.teams.iterator();

            while(var2.hasNext()) {
                Team team = (Team)var2.next();
                if (this.isTeamReady(team)) {
                    ++readyCount;
                    if (readyCount >= 2) {
                        return true;
                    }
                }
            }

            return readyCount >= 2;
        }
    }

    private boolean isTeamReady(Team team) {
        if (this.team_spawn.get(team) != null && this.team_shop.get(team) != null && this.team_upgr.get(team) != null && this.team_gen.get(team) != null) {
            return this.team_bed.get(team) != null;
        } else {
            return false;
        }
    }

    public void saveDefaultConfig() {
    }

    private ConfigUtils getConfigUtils() {
        if (this.utils == null) {
            this.utils = new ConfigUtils(this.getConfig());
        }

        return this.utils;
    }

    public String toString() {
        return "BedwarsArena [Name=" + this.name + "]";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name.toLowerCase()});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BedwarsArena)) {
            return false;
        } else {
            BedwarsArena other = (BedwarsArena)obj;
            return this.name.equalsIgnoreCase(other.name);
        }
    }

    public static Set<String> getArenasNameList() {
        Set<String> result = new HashSet();
        File directory = new File(Bedwarss.getInstance().getDataFolder() + "/Arenas");
        String[] list = directory.list();
        if (list != null) {
            String[] var3 = list;
            int var4 = list.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String name = var3[var5];
                if (name.endsWith(".yml")) {
                    result.add(name.substring(0, name.length() - 4));
                }
            }
        }

        return result;
    }

    public static Set<Arena> getReadyArenas() {
        Set<Arena> result = new HashSet(8);
        Iterator var1 = ARENAS.values().iterator();

        while(var1.hasNext()) {
            BedwarsArena arena = (BedwarsArena)var1.next();
            if (arena.isReady()) {
                result.add(arena);
            }
        }

        return result;
    }

    public static Set<BedwarsArena> getArenas() {
        return new HashSet(ARENAS.values());
    }

    public static BedwarsArena getArena(String name) {
        return name != null ? (BedwarsArena)ARENAS.get(name.toLowerCase()) : null;
    }
}
