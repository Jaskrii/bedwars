package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.entity.Player;

public class DisableCommand implements SubCommand{

    public DisableCommand() {
    }

    public String getName() {
        return "disable";
    }

    public String getDescription() {
        return "Disable arena!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw disable <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                arena.setEnabled(false);
                player.sendMessage(ChatUtils.success("Arena has been disabled!"));
            } else {
                player.sendMessage(ChatUtils.error("Arena with name §e" + args[1] + " §cdoesn't exist!"));
            }
        }
    }
}
