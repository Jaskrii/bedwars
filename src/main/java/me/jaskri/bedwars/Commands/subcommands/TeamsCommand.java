package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.API.PACKAGE.Team.Team;
import me.jaskri.bedwars.Bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamsCommand implements SubCommand{

    private static final String TEXT;

    public TeamsCommand() {
    }

    public String getName() {
        return "teams";
    }

    public String getDescription() {
        return "Show all the available teams!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw teams";
    }

    public void perform(Player player, String[] args) {
        player.sendMessage(TEXT);
    }

    static {
        StringBuilder builder = new StringBuilder(Bedwars.getInstance().getPluginPrefix());
        Team[] values = Team.values();

        for(int i = 0; i < values.length; ++i) {
            builder.append(values[i].getColoredString());
            if (i < values.length - 1) {
                builder.append(ChatColor.GRAY).append(", ");
            }
        }

        TEXT = builder.toString();
    }
}
