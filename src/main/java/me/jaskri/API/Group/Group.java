package me.jaskri.API.Group;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;

public interface Group {

    Set<Player> getPlayers();

    void addPlayers(Collection<Player> var1);

    boolean addPlayer(Player var1);

    boolean removePlayer(Player var1);

    boolean isEmpty();

    boolean contains(Player var1);

    void clear();

    int size();
}
