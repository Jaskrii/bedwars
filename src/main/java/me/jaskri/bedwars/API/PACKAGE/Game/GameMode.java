package me.jaskri.bedwars.API.PACKAGE.Game;

import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GameMode {

    public static final GameMode SOLO = new GameMode("Solo", 1, 8);
    public static final GameMode DUO = new GameMode("Doubles", 2, 8);
    public static final GameMode TRIO = new GameMode("3v3v3v3", 3, 4);
    public static final GameMode QUATUOR = new GameMode("4v4v4v4", 4, 4);
    private static final Map<String, GameMode> BY_NAME = new LinkedHashMap();
    private final String name;
    private final int teamMax;
    private final int teams;

    public GameMode(String name, int teamMax, int teams) {
        Preconditions.checkNotNull(name, "Mode name cannot be null!");
        Preconditions.checkArgument(teams <= 8 && teams >= 2, "Mode teams should be between 8 and 2!");
        this.name = name;
        this.teamMax = teamMax;
        this.teams = teams;
    }

    public String getName() {
        return this.name;
    }

    public int getTeamMax() {
        return this.teamMax;
    }

    public int getMaxTeams() {
        return this.teams;
    }

    public int getGameMax() {
        return this.teamMax * this.teams;
    }

    public String toString() {
        return "GameMode [Name=" + this.name + ", Team-max=" + this.teamMax + ", Teams=" + this.teams + "]";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name.toLowerCase(), this.teamMax, this.teams});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof GameMode)) {
            return false;
        } else {
            GameMode other = (GameMode)obj;
            return this.teamMax == other.teamMax && this.teams == other.teams ? this.name.equalsIgnoreCase(other.name) : false;
        }
    }

    public static void registerGameMode(GameMode mode) {
        if (!canRegisterGameMode(mode)) {
            throw new IllegalStateException("Cannot register existing game mode");
        } else {
            BY_NAME.put(mode.name.toLowerCase(), mode);
        }
    }

    public static boolean canRegisterGameMode(GameMode mode) {
        return mode != null && !BY_NAME.containsKey(mode.name.toLowerCase());
    }

    public static GameMode getByName(String name) {
        return name != null ? (GameMode)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static GameMode fromString(String name) {
        return name != null ? (GameMode)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static GameMode[] values() {
        return (GameMode[])BY_NAME.values().toArray(new GameMode[BY_NAME.size()]);
    }

    static {
        registerGameMode(SOLO);
        registerGameMode(DUO);
        registerGameMode(TRIO);
        registerGameMode(QUATUOR);
    }
}
