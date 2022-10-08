package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import org.bukkit.entity.Player;

public class CreateCommand implements SubCommand{

    public CreateCommand() {
    }

    public String getName() {
        return "create";
    }

    public String getDescription() {
        return "Create a new arena with the given name!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw create <Name>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = new BedwarsArena(args[1]);
            if (arena.exists()) {
                player.sendMessage(ChatUtils.error("§e" + args[1] + " §calready exists!"));
            } else {
                arena.createFile();
                player.sendMessage(ChatUtils.success("§e" + args[1] + " §ahas been created!"));
            }
        }
    }
}
