package me.jaskri.API.Game;

import org.bukkit.ChatColor;

public enum GameState {

    WAITING("Waiting", ChatColor.AQUA),
    COUNTDOWN("Countdown", ChatColor.GREEN),
    RUNNING("Running", ChatColor.YELLOW),
    ENDED("Ended", ChatColor.RED),
    RESETTING("Resetting", ChatColor.DARK_PURPLE);

    private final String state;
    private final ChatColor color;

    private GameState(String state, ChatColor color) {
        this.state = state;
        this.color = color;
    }

    public ChatColor getChatColor() {
        return this.color;
    }

    public String toString() {
        return this.state;
    }
}
