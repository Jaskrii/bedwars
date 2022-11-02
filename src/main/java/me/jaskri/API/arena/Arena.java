package me.jaskri.API.arena;

import me.jaskri.API.Generator.GeneratorSpeed;
import me.jaskri.API.Generator.Resource;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.scoreboard.Team;

import javax.swing.plaf.synth.Region;
import java.util.List;
import java.util.Set;

public interface Arena {
    String getName();

    String getMapName();

    int getTime();

    GameMode getMode();

    Set<Team> getTeams();

    List<Location> getResourceGenLocations(Resource var1);

    GeneratorSpeed getGeneratorSpeed();

    Location getTeamShop(Team var1);

    Location getTeamUpgrade(Team var1);

    Location getTeamGenLocation(Team var1);

    Location getTeamSpawnPoint(Team var1);

    BedwarsBed getTeamBed(Team var1);

    Chest getTeamChest(Team var1);

    Location getDragonSpawnPoint();

    Location getLobbySpawnPoint();

    Location getSpectatorSpawnPoint();

    Location getWaitingRoomSpawnPoint();

    Region getWaitingRoomRegion();

    Region getRegion();

    void setMapName(String var1);

    void setArenaTime(int var1);

    void setMode(GameMode var1);

    void setTeamShop(Team var1, Location var2);

    void setTeamUpgrade(Team var1, Location var2);

    void addResourceGenerator(Resource var1, Location var2);

    boolean removeResourceGenerator(Resource var1, int var2);

    void setGeneratorSpeed(GeneratorSpeed var1);

    void setTeamGenLocation(Team var1, Location var2);

    void setTeamSpawnPoint(Team var1, Location var2);

    void setTeamBed(BedwarsBed var1);

    void setTeamChest(Team var1, Chest var2);

    void setDragonSpawnPoint(Location var1);

    void setLobbySpawnPoint(Location var1);

    void setSpectatorSpawnPoint(Location var1);

    void setWaitingRoomLocation(Location var1);

    void setWaitingRoomRegion(Region var1);

    void setArenaRegion(Region var1);

    void setEnabled(boolean var1);

    void reloadArena();

    void saveArena();

    boolean exists();

    boolean remove();

    boolean isEnabled();

    boolean isReady();
}
