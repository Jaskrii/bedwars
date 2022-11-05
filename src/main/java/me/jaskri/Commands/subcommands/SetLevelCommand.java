package me.jaskri.Commands.subcommands;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.User.User;
import me.jaskri.Commands.SubCommand;
import me.jaskri.User.UserData;
import me.jaskri.Util.ChatUtils;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class SetLevelCommand implements SubCommand {

    public SetLevelCommand() {
    }

    public String getName() {
        return "setLevel";
    }

    public String getDescription() {
        return "Sets current player bedwars level!";
    }

    public String getPermission() {
        return "bedwars.command.level";
    }

    public String getUsage() {
        return "/Bw setLevel <Player> <Level> <Exp-Optional>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            OfflinePlayer userPlayer = Bukkit.getOfflinePlayer(args[1]);
            if (!userPlayer.hasPlayedBefore()) {
                player.sendMessage(ChatUtils.error("Player not found!"));
            } else {
                int level = NumberConversions.toInt(args[2]);
                if (level < 1) {
                    player.sendMessage(ChatUtils.error("Level must be between 1 and 2147483647"));
                } else {
                    int exp = args.length >= 4 ? NumberConversions.toInt(args[3]) : 0;
                    if (exp < 0) {
                        player.sendMessage(ChatUtils.error("Exp must be positif!"));
                    } else {
                        boolean display = args.length >= 5 ? Boolean.parseBoolean(args[4]) : false;
                        BedwarsLevel bwLvL = new BedwarsLevel(level, exp, Bedwars.getInstance().getSettings().getLevelUpExpFor(level));
                        Prestige prestige = Prestige.getByLevel(level);
                        if (userPlayer.isOnline()) {
                            User user = Bedwars.getInstance().getUser(player);
                            if (display) {
                                user.setDisplayLevel(bwLvL);
                                user.setDisplayPrestige(prestige);
                            } else {
                                user.setLevel(bwLvL);
                                user.setPrestige(prestige);
                            }
                        } else {
                            UserData config = new UserData(userPlayer);
                            config.setLevel(bwLvL);
                            config.setPrestige(prestige);
                        }

                    }
                }
            }
        }
    }
}
