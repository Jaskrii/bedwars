package me.jaskri.Commands.subcommands;

import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.User.User;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SetPrestigeCommand implements SubCommand{

    public SetPrestigeCommand() {
    }

    public String getName() {
        return "setPrestige";
    }

    public String getDescription() {
        return "Sets player prestige!";
    }

    public String getPermission() {
        return "bedwars.command.prestige";
    }

    public String getUsage() {
        return "/Bw setPrestige <Prestige> <Player-Optional>";
    }

    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatUtils.usage(this.getUsage()));
        } else {
            Prestige prestige = Prestige.getByName(args[1]);
            if (prestige == null) {
                player.sendMessage(ChatUtils.error("§e" + args[1] + " §cdoesn't exist"));
            } else if (args.length < 3) {
                this.editPrestige(Bedwars.getInstance().getUser(player), prestige);
            } else {
                OfflinePlayer userPlayer = Bukkit.getOfflinePlayer(args[2]);
                if (!userPlayer.hasPlayedBefore()) {
                    player.sendMessage(ChatUtils.error("Player not found!"));
                } else {
                    if (userPlayer.isOnline()) {
                        this.editPrestige(Bedwars.getInstance().getUser(userPlayer.getPlayer()), prestige);
                    } else {
                        UserData data = new UserData(userPlayer);
                        data.setPrestige(prestige);
                        Bukkit.getScheduler().runTaskAsynchronously(Bedwars.getInstance(), () -> {
                            data.saveData();
                        });
                    }

                    player.sendMessage(ChatUtils.success(userPlayer.getName() + "'s prestige has been set to §e" + prestige.getName() + "§a!"));
                }
            }
        }
    }

    private void editPrestige(User user, Prestige prestige) {
        user.setPrestige(prestige);
        user.getPlayer().sendMessage(ChatUtils.success("Your prestige has been changed to §e" + prestige.getName() + "§a!"));
    }
}
