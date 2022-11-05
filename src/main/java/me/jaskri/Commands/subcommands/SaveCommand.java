package me.jaskri.Commands.subcommands;

import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SaveCommand implements SubCommand {

    public SaveCommand() {
    }

    public String getName() {
        return "save";
    }

    public String getDescription() {
        return "Saves arena!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw save <Arena>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error("§e" + args[1] + " §cis already in use and cannot be saved!"));
                } else {
                    arena.saveArena();
                    player.sendMessage(ChatUtils.success(ChatColor.YELLOW + args[1] + ChatColor.GREEN + " has been saved!"));
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name §e" + args[1] + " §cdoesn't exist!"));
            }
        }
    }
}
