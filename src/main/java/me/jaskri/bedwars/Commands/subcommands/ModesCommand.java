package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Bedwars.Bedwars;
import me.jaskri.bedwars.Bedwarss;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ModesCommand implements SubCommand{

    private static final String TEXT;

    public ModesCommand() {
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
        StringBuilder builder = new StringBuilder(Bedwars.getInstance().getPluginPrefix());
        GameMode[] values = GameMode.values();

        for(int i = 0; i < values.length; ++i) {
            builder.append(ChatColor.GOLD).append(values[i].getName());
            if (i < values.length - 1) {
                builder.append(ChatColor.GRAY).append(", ");
            }
        }

        TEXT = builder.toString();
    }
}
