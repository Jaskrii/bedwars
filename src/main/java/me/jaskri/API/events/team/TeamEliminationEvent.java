package me.jaskri.API.events.team;

import org.bukkit.event.HandlerList;

public class TeamEliminationEvent extends TeamEvent{

    private static final HandlerList HANDLERS = new HandlerList();
    private String message;

    public TeamEliminationEvent(Game game, Team team, String message) {
        super(game, team);
        this.message = message;
    }

    public String getEliminationMessage() {
        return this.message;
    }

    public void setEliminationMessage(String message) {
        if (message != null) {
            this.message = message;
        }

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
