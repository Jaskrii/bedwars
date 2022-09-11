package me.jaskri.bedwars.Game;

import me.jaskri.bedwars.API.PACKAGE.Group.Group;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BedwarsGroup implements Group {

    private Set<Player> players = new HashSet();

    public BedwarsGroup(Collection<Player> players) {
        this.addPlayers(players);
    }

    public BedwarsGroup() {
    }

    public Set<Player> getPlayers() {
        return new HashSet(this.players);
    }

    public void addPlayers(Collection<Player> players) {
        if (players != null) {
            this.players.addAll(players);
        }

    }

    public boolean addPlayer(Player player) {
        return player != null ? this.players.add(player) : false;
    }

    public boolean removePlayer(Player player) {
        return player != null ? this.players.remove(player) : false;
    }

    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    public boolean contains(Player player) {
        return this.players != null ? this.players.contains(player) : false;
    }

    public void clear() {
        this.players.clear();
    }

    public int size() {
        return this.players.size();
    }
}
