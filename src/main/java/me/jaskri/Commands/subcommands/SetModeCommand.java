package me.jaskri.Commands.subcommands;

import me.jaskri.API.Game.GameMode;
import me.jaskri.Arena.BedwarsArena;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Game.AbstractGame;
import me.jaskri.Util.ChatUtils;
import org.bukkit.entity.Player;

public class SetModeCommand implements SubCommand {

    public SetModeCommand() {
    }

    public String getName() {
        return "setMode";
    }

    public String getDescription() {
        return "Sets arena game mode!";
    }

    public String getPermission() {
        return "bedwars.setup";
    }

    public String getUsage() {
        return "/bw setMode <Arena> <GameMode>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            BedwarsArena arena = BedwarsArena.getArena(args[1]);
            if (arena != null && arena.exists()) {
                if (AbstractGame.isArenaOccuped(arena)) {
                    player.sendMessage(ChatUtils.error("§e" + args[1] + " §is already in use and cannot be edited!"));
                } else {
                    GameMode mode = GameMode.getByName(args[2]);
                    if (mode == null) {
                        player.sendMessage(ChatUtils.error("Invalid GameMode!"));
                        player.sendMessage(ChatUtils.info("/bw modes"));
                    } else {
                        arena.setMode(mode);
                        arena.saveConfig();
                        player.sendMessage(ChatUtils.success("Arena gamemode has been set to §e" + mode.getName() + "§a!"));
                    }
                }
            } else {
                player.sendMessage(ChatUtils.error("Arena with name §e" + args[1] + " §cdoesn't exist!"));
            }
        }
    }
}
