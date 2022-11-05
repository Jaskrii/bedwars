package me.jaskri.Commands.subcommands;

import me.jaskri.API.Generator.Resource;
import me.jaskri.Commands.SubCommand;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ResourcesCommand implements SubCommand {

    private static final String TEXT;

    public ResourcesCommand() {
    }

    public String getName() {
        return "resources";
    }

    public String getDescription() {
        return "Shows all bedwars resources!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw resources";
    }

    public void perform(Player player, String[] args) {
        player.sendMessage(TEXT);
    }

    static {
        StringBuilder builder = new StringBuilder(Bedwars.getInstance().getPluginPrefix());
        Resource[] values = Resource.values();

        for(int i = 0; i < values.length; ++i) {
            Resource resource = values[i];
            if (resource != Resource.FREE) {
                builder.append(resource.getColoredName());
                if (i < values.length - 1) {
                    builder.append(ChatColor.GRAY).append(", ");
                }
            }
        }

        TEXT = builder.toString();
    }
}
