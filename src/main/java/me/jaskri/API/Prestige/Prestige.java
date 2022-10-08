package me.jaskri.API.Prestige;

import com.google.common.base.Preconditions;
import me.jaskri.API.Level.BedwarsLevel;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Prestige {

    private static final Map<Integer, Prestige> BY_START_LEVEL = new HashMap();
    private static final Map<Integer, Prestige> BY_END_LEVEL = new HashMap();
    private static final Map<String, Prestige> BY_NAME = new HashMap();
    public static final Prestige DEFAULT;
    private final String name;
    private final String display;
    private final String chatFormat;
    private final String boardFormat;
    private final int start;
    private final int end;

    public Prestige(String name, String displayName, String chatFormat, String boardFormat, int start, int end) {
        Preconditions.checkNotNull(name, "Prestige name cannot be null!");
        Preconditions.checkNotNull(chatFormat, "Prestige chat format cannot be null!");
        Preconditions.checkNotNull(boardFormat, "Prestige scoreboard format cannot be null!");
        Preconditions.checkArgument(start >= 0, "Prestige start level must be positif!");
        Preconditions.checkArgument(end >= 0, "Prestige end level must be positif!");
        Preconditions.checkArgument(end >= start, "Prestige end level must be greater than or equals to start level!");
        this.name = name;
        this.display = displayName;
        this.chatFormat = chatFormat;
        this.boardFormat = boardFormat;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.display;
    }

    public String getChatFormat() {
        return this.chatFormat;
    }

    public String getScoreboardFormat() {
        return this.boardFormat;
    }

    public int getStartLevel() {
        return this.start;
    }

    public int getEndLevel() {
        return this.end;
    }

    public String formatToChat(BedwarsLevel level) {
        return level != null ? format(this.chatFormat, level) : null;
    }

    public String formatToScoreboard(BedwarsLevel level) {
        return level != null ? format(this.boardFormat, level) : null;
    }

    public static String format(String format, BedwarsLevel level) {
        return format(format, String.valueOf(level.getLevel()));
    }

    private static String format(String format, String lvl) {
        StringBuilder builder = new StringBuilder(format);
        int index = 0;

        for(int i = 0; i < builder.length(); ++i) {
            if (builder.charAt(i) == '*') {
                if (index < lvl.length()) {
                    builder.setCharAt(i, lvl.charAt(index++));
                } else {
                    builder.deleteCharAt(i--);
                }
            }
        }

        return format(builder.toString());
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.chatFormat, this.boardFormat, this.end, this.start});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Prestige)) {
            return false;
        } else {
            Prestige other = (Prestige)obj;
            if (this.start == other.start && this.end == other.end && this.name.equalsIgnoreCase(other.name)) {
                return this.chatFormat.equalsIgnoreCase(other.chatFormat) && this.boardFormat.equalsIgnoreCase(other.boardFormat);
            } else {
                return false;
            }
        }
    }

    public static Prestige getByName(String name) {
        return name != null ? (Prestige)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static Prestige getByStartLevel(int level) {
        return (Prestige)BY_START_LEVEL.get(level);
    }

    public static Prestige getByEndLevel(int level) {
        return (Prestige)BY_END_LEVEL.get(level);
    }

    public static Prestige getByLevel(int level) {
        Iterator var1 = BY_NAME.values().iterator();

        Prestige prestige;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            prestige = (Prestige)var1.next();
        } while(level < prestige.start || level > prestige.end);

        return prestige;
    }

    public static boolean registerPrestige(Prestige prestige) {
        if (prestige == null) {
            return false;
        } else {
            String name = prestige.name.toLowerCase();
            if (!BY_NAME.containsKey(name) && !BY_START_LEVEL.containsKey(prestige.start) && !BY_END_LEVEL.containsKey(prestige.end)) {
                BY_NAME.put(name, prestige);
                BY_START_LEVEL.put(prestige.start, prestige);
                BY_END_LEVEL.put(prestige.end, prestige);
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isRegistered(Prestige prestige) {
        if (prestige == null) {
            return false;
        } else {
            return BY_NAME.containsKey(prestige.name) || BY_START_LEVEL.containsKey(prestige.start) || BY_END_LEVEL.containsKey(prestige.end);
        }
    }

    private static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    static {
        DEFAULT = new Prestige("Stone Prestige", ChatColor.GRAY + "Stone Prestige", ChatColor.GRAY + "[****✫]", ChatColor.GRAY + "****✫", 1, 99);
    }
}
