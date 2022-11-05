package me.jaskri.bedwars;

import me.jaskri.Commands.BedwarsCommands;
import me.jaskri.Commands.SubCommand;
import me.jaskri.Text.TextSection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class BedwarsHelp {

    private static final TextSection[] PAGES;
    public static final int MAX_PAGE;

    private static String getHeader(int page, int max) {
        return "§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬ §bBed Wars Help §7(§b" + page + "§7/§b" + max + "§7) §a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
    }

    private static String buildCommand(SubCommand sub) {
        StringBuilder builder = (new StringBuilder()).append(ChatColor.YELLOW).append("/bw ").append(sub.getName()).append(": ").append(ChatColor.GRAY).append(sub.getDescription());
        return builder.toString();
    }

    private BedwarsHelp() {
    }

    public static void send(Player player, SubCommand sub) {
        player.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        player.sendMessage("                              §l" + sub.getName());
        player.sendMessage("");
        player.sendMessage("§bDecription: §7" + sub.getDescription());
        player.sendMessage("§bPermission: §7" + sub.getPermission());
        player.sendMessage("§bUsage: §7" + sub.getUsage());
        player.sendMessage("");
        player.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public static void send(Player player, int page) {
        if (page >= 1 && page <= PAGES.length) {
            PAGES[page - 1].sendMessage(player);
        }

    }

    public static void send(Player player) {
        send(player, 1);
    }

    public static boolean isValidPage(int page) {
        return page >= 1 && page <= PAGES.length;
    }

    static {
        String footer = "§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
        List<SubCommand> list = BedwarsCommands.getSubCommandsList();
        int length = (int)Math.ceil((double)list.size() / 8.0);
        PAGES = new TextSection[length];

        for(int i = 0; i < length; ++i) {
            TextSection page = new TextSection();
            page.append(getHeader(i + 1, length));

            for(int j = 0; j < 8; ++j) {
                int index = i * 8 + j;
                if (index >= list.size()) {
                    break;
                }

                page.append(buildCommand((SubCommand)list.get(index)));
            }

            page.append(footer);
            PAGES[i] = page;
        }

        MAX_PAGE = length;
    }
}
