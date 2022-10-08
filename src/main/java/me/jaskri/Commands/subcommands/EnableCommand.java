package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import org.bukkit.entity.Player;

public class EnableCommand implements SubCommands{

    public EnableCommand() {
    }

    public String getName() {
        return "enable";
    }

    public String getDescription() {
        return "Enable arena";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw enable <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                arena.setEnabled(true);
                player.sendMessage(ChatUtils.success("Arena has been enabled!"));
            } else {
                player.sendMessage(ChatUtils.error("Arena with name §e" + args[1] + " §cdoesn't exist!"));
            }
        }
    }
}
