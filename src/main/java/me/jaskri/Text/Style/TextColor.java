package me.jaskri.Text.Style;

import org.bukkit.ChatColor;

public enum TextColor {

    BLACK(ChatColor.BLACK),
    DARK_BLUE(ChatColor.DARK_BLUE),
    DARK_GREEN(ChatColor.DARK_GREEN),
    DARK_AQUA(ChatColor.DARK_AQUA),
    DARK_RED(ChatColor.DARK_RED),
    DARK_PURPLE(ChatColor.DARK_PURPLE),
    GOLD(ChatColor.GOLD),
    GRAY(ChatColor.GRAY),
    DARK_GRAY(ChatColor.DARK_GRAY),
    BLUE(ChatColor.BLUE),
    GREEN(ChatColor.GREEN),
    AQUA(ChatColor.AQUA),
    RED(ChatColor.RED),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE),
    YELLOW(ChatColor.YELLOW),
    WHITE(ChatColor.WHITE);

    private ChatColor color;

    private TextColor(ChatColor color) {
        this.color = color;
    }

    public ChatColor toChatColor() {
        return this.color;
    }

    public net.md_5.bungee.api.ChatColor toBungeeChatColor() {
        return this.color.asBungee();
    }
}
