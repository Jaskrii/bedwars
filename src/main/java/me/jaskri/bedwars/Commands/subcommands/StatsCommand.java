package me.jaskri.bedwars.Commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class StatsCommand implements SubCommand{

    private static final String TEXT;

    public StatsCommand() {
    }

    public String getName() {
        return "Modes";
    }

    public String getDescription() {
        return "Shows all bedwars gamemodes!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw modes";
    }

    public void perform(Player player, String[] args) {
        player.sendMessage(TEXT);
    }

    static {
        StringBuilder builder = new StringBuilder();
        me.jaskri.bedwars.API.PACKAGE.Game.GameMode[] values = GameMode.values();

        for(int i = 0; i < values.length; ++i) {
            builder.append(ChatColor.GOLD);
            builder.append(values[i].getName());
            if (i < values.length - 1) {
                builder.append(',');
            }
        }

        TEXT = builder.toString();
    }
}
