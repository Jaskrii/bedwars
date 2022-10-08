package me.jaskri.API.Level;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BedwarsLevel {

    private int level;
    private int exp;
    private int expToLevel;

    public BedwarsLevel(int level, int exp, int expToLevel) {
        Preconditions.checkArgument(level > 0, "Bedwars level must be greater than 0");
        Preconditions.checkArgument(exp >= 0, "Progress exp must be greater than or equals to 0");
        Preconditions.checkArgument(expToLevel > 0, "Exp to level must be greater than 0");
        this.expToLevel = expToLevel;
        this.level = level;
        this.setProgressExp(exp);
    }

    public int getLevel() {
        return this.level;
    }

    public void incrementLevel(int amount) {
        if (amount > 0) {
            this.level += amount;
        }
    }

    public void decrementLevel(int amount) {
        if (amount > 0) {
            this.level = this.level - amount > 0 ? this.level - amount : 1;
        }
    }

    public void setLevel(int level) {
        if (level > 0) {
            this.level = level;
        }

    }

    public int getProgressExp() {
        return this.exp;
    }

    public void incrementProgressExp(int amount) {
        if (amount > 0) {
            this.exp += amount;
            if (this.exp >= this.expToLevel) {
                this.exp %= this.expToLevel;
            }

        }
    }

    public void decrementProgressExp(int amount) {
        if (amount > 0) {
            this.exp += this.exp - amount > 0 ? this.exp - amount : 0;
        }
    }

    public void setProgressExp(int exp) {
        if (exp >= 0) {
            this.exp = exp % this.expToLevel;
        }
    }

    public int getExpToLevelUp() {
        return this.expToLevel;
    }

    public void setExpToLevel(int exp) {
        if (exp > 0) {
            this.expToLevel = exp;
        }

    }

    public int getNeededExpToLevel() {
        return this.expToLevel - this.exp;
    }

    public float getProgressPercentage() {
        return (float)this.exp / (float)this.expToLevel;
    }

    public String getProgressBar(int length) {
        return getProgressBar(length, this.getProgressPercentage());
    }

    public boolean isLeveling(int exp) {
        return this.exp + exp >= this.expToLevel;
    }

    public BedwarsLevel clone() {
        return new BedwarsLevel(this.level, this.exp, this.expToLevel);
    }

    public static String getProgressBar(int length, float percentage) {
        Preconditions.checkArgument(percentage >= 0.0F && percentage <= 1.0F, "Percentage must be between 0 and 1");
        StringBuilder builder = new StringBuilder(ChatColor.DARK_GRAY + "[");
        ChatColor previous = null;
        int max = (int)((float)length * percentage);

        for(int i = 0; i < length; ++i) {
            ChatColor color = i < max ? ChatColor.AQUA : ChatColor.GRAY;
            if (color != previous) {
                previous = color;
                builder.append(color);
            }

            builder.append("■");
        }

        builder.append(ChatColor.DARK_GRAY + "]");
        return builder.toString();
    }

    public static void setForPlayer(Player player, BedwarsLevel level) {
        if (player != null && level != null) {
            player.setLevel(level.getLevel());
            player.setExp(level.getProgressPercentage());
        }
    }
}
