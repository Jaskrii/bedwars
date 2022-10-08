package me.jaskri.Commands.subcommands;

import org.bukkit.entity.Player;

public class MapSelectorCommand implements SubCommand{

    public MapSelectorCommand() {
    }

    public String getName() {
        return "maps";
    }

    public String getDescription() {
        return "Shows all available maps!";
    }

    public String getPermission() {
        return "bedwars.command.maps";
    }

    public String getUsage() {
        return "/bw maps";
    }

    public void perform(Player player, String[] args) {
    }
}
