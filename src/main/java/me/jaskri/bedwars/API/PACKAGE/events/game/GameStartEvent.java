package me.jaskri.bedwars.API.PACKAGE.events.game;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.util.Set;
import java.util.UUID;

public class GameStartEvent extends GameEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private final Set<UUID> players;
    private final Set<UUID> spectators;

    public GameStartEvent(Game game, Set<UUID> players, Set<UUID> spectators) {
        super(game);
        this.players = players;
        this.spectators = spectators;
    }

    public Set<UUID> getPlayers() {
        return this.players;
    }

    public Set<UUID> getSpectators() {
        return this.spectators;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
