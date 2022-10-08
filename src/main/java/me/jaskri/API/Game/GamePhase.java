package me.jaskri.API.Game;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class GamePhase {

    private static final Map<String, GamePhase> BY_NAME = new HashMap();
    private String name;
    private int duration;

    public GamePhase(String name, int duration) {
        Preconditions.checkNotNull(name, "Phase name cannot be null");
        Preconditions.checkArgument(duration >= 0, "Phase duration should be positif");
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return this.name;
    }

    public int getDuration() {
        return this.duration;
    }

    public abstract boolean apply(Game var1);

    public String toString() {
        return "GamePhase [Name= " + this.name + ", Duration= " + this.duration + "]";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.duration, this.name.toLowerCase()});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof GamePhase)) {
            return false;
        } else {
            GamePhase other = (GamePhase)obj;
            return this.duration == other.duration && this.name.equalsIgnoreCase(other.name);
        }
    }

    public static void registerGamePhase(GamePhase phase) {
        if (BY_NAME.containsKey(phase.name.toLowerCase())) {
            throw new IllegalStateException("Cannot register existing game phase");
        } else {
            BY_NAME.put(phase.name.toLowerCase(), phase);
        }
    }

    public static GamePhase getByName(String name) {
        return name != null ? (GamePhase)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static GamePhase[] values() {
        return (GamePhase[])BY_NAME.values().toArray(new GamePhase[BY_NAME.size()]);
    }

}
