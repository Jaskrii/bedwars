package me.jaskri.bedwars.Commands.subcommands;

import me.jaskri.bedwars.Arena.BedwarsArena;
import org.bukkit.entity.Player;

public class ReloadCommand implements SubCommand{

    public ReloadCommand() {
    }

    public String getName() {
        return "reloadArena";
    }

    public String getDescription() {
        return "Reloads arena!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw reloadArena <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error("§e" + args[1] + " §cis already in use and cannot be reloaded!"));
                } else {
                    arena.reloadArena();
                    player.sendMessage(ChatUtils.success("Arena region has been set!"));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name §e" + args[1] + " §cdoesn't exist!"));
            }
        }
    }
}
