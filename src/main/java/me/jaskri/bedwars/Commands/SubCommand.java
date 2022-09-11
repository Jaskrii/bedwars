package me.jaskri.bedwars.Commands;

import org.bukkit.entity.Player;

public interface SubCommand {
    String getName();

    String getDescription();

    String getPermission();

    String getUsage();

    void perform(Player var1, String[] var2);
}
