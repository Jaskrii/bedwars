package me.jaskri.Util.Messages;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

public final class Titles {

    private static final Object TITLE;
    private static final Object SUBTITLE;
    private static final Object TIMES;
    private static final Object CLEAR;
    private static final MethodHandle PACKET_PLAY_OUT_TITLE;
    private static final MethodHandle CHAT_COMPONENT_TEXT;
    private String title;
    private String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public Titles(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player) {
        sendTitle(player, this.fadeIn, this.stay, this.fadeOut, this.title, this.subtitle);
    }

    public static void sendTitle(@Nonnull Player player, int fadeIn, int stay, int fadeOut, @Nullable String title, @Nullable String subtitle) {
        Objects.requireNonNull(player, "Cannot send title to null player");
        if (title != null || subtitle != null) {
            if (ReflectionUtils.supports(11)) {
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            } else {
                try {
                    Object timesPacket = PACKET_PLAY_OUT_TITLE.invoke(TIMES, CHAT_COMPONENT_TEXT.invoke(title), fadeIn, stay, fadeOut);
                    ReflectionUtils.sendPacket(player, new Object[]{timesPacket});
                    Object subtitlePacket;
                    if (title != null) {
                        subtitlePacket = PACKET_PLAY_OUT_TITLE.invoke(TITLE, CHAT_COMPONENT_TEXT.invoke(title), fadeIn, stay, fadeOut);
                        ReflectionUtils.sendPacket(player, new Object[]{subtitlePacket});
                    }

                    if (subtitle != null) {
                        subtitlePacket = PACKET_PLAY_OUT_TITLE.invoke(SUBTITLE, CHAT_COMPONENT_TEXT.invoke(subtitle), fadeIn, stay, fadeOut);
                        ReflectionUtils.sendPacket(player, new Object[]{subtitlePacket});
                    }
                } catch (Throwable var8) {
                    var8.printStackTrace();
                }

            }
        }
    }

    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subtitle) {
        sendTitle(player, 10, 20, 10, title, subtitle);
    }

    public static Titles parseTitle(@Nonnull ConfigurationSection config) {
        return parseTitle(config, (Function)null);
    }

    public static Titles parseTitle(@Nonnull ConfigurationSection config, @Nullable Function<String, String> transformers) {
        String title = config.getString("title");
        String subtitle = config.getString("subtitle");
        if (transformers != null) {
            title = (String)transformers.apply(title);
            subtitle = (String)transformers.apply(subtitle);
        }

        int fadeIn = config.getInt("fade-in");
        int stay = config.getInt("stay");
        int fadeOut = config.getInt("fade-out");
        if (fadeIn < 1) {
            fadeIn = 10;
        }

        if (stay < 1) {
            stay = 20;
        }

        if (fadeOut < 1) {
            fadeOut = 10;
        }

        return new Titles(title, subtitle, fadeIn, stay, fadeOut);
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public static Titles sendTitle(@Nonnull Player player, @Nonnull ConfigurationSection config) {
        Titles titles = parseTitle(config, (Function)null);
        titles.send(player);
        return titles;
    }

    public static void clearTitle(@Nonnull Player player) {
        Objects.requireNonNull(player, "Cannot clear title from null player");
        if (ReflectionUtils.supports(11)) {
            player.resetTitle();
        } else {
            Object clearPacket;
            try {
                clearPacket = PACKET_PLAY_OUT_TITLE.invoke(CLEAR, (Void)null, -1, -1, -1);
            } catch (Throwable var3) {
                var3.printStackTrace();
                return;
            }

            ReflectionUtils.sendPacket(player, new Object[]{clearPacket});
        }
    }

    public static void sendTabList(@Nonnull String header, @Nonnull String footer, Player... players) {
        Objects.requireNonNull(players, "Cannot send tab title to null players");
        Objects.requireNonNull(header, "Tab title header cannot be null");
        Objects.requireNonNull(footer, "Tab title footer cannot be null");
        if (ReflectionUtils.supports(13)) {
            Player[] var16 = players;
            int var17 = players.length;

            for(int var18 = 0; var18 < var17; ++var18) {
                Player player = var16[var18];
                player.setPlayerListHeaderFooter(header, footer);
            }

        } else {
            try {
                Class<?> IChatBaseComponent = ReflectionUtils.getNMSClass("network.chat", "IChatBaseComponent");
                Class<?> PacketPlayOutPlayerListHeaderFooter = ReflectionUtils.getNMSClass("network.protocol.game", "PacketPlayOutPlayerListHeaderFooter");
                Method chatComponentBuilderMethod = IChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
                Object tabHeader = chatComponentBuilderMethod.invoke((Object)null, "{\"text\":\"" + header + "\"}");
                Object tabFooter = chatComponentBuilderMethod.invoke((Object)null, "{\"text\":\"" + footer + "\"}");
                Object packet = PacketPlayOutPlayerListHeaderFooter.getConstructor().newInstance();
                Field headerField = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("a");
                Field footerField = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("b");
                headerField.setAccessible(true);
                headerField.set(packet, tabHeader);
                footerField.setAccessible(true);
                footerField.set(packet, tabFooter);
                Player[] var11 = players;
                int var12 = players.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    Player player = var11[var13];
                    ReflectionUtils.sendPacket(player, new Object[]{packet});
                }

            } catch (Exception var15) {
                throw new RuntimeException(var15);
            }
        }
    }

    static {
        MethodHandle packetCtor = null;
        MethodHandle chatComp = null;
        Object times = null;
        Object title = null;
        Object subtitle = null;
        Object clear = null;
        if (!ReflectionUtils.supports(11)) {
            Class<?> chatComponentText = ReflectionUtils.getNMSClass("ChatComponentText");
            Class<?> packet = ReflectionUtils.getNMSClass("PacketPlayOutTitle");
            Class<?> titleTypes = packet.getDeclaredClasses()[0];
            Object[] var9 = titleTypes.getEnumConstants();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                Object type = var9[var11];
                switch (type.toString()) {
                    case "TIMES":
                        times = type;
                        break;
                    case "TITLE":
                        title = type;
                        break;
                    case "SUBTITLE":
                        subtitle = type;
                        break;
                    case "CLEAR":
                        clear = type;
                }
            }

            MethodHandles.Lookup lookup = MethodHandles.lookup();

            try {
                chatComp = lookup.findConstructor(chatComponentText, MethodType.methodType(Void.TYPE, String.class));
                packetCtor = lookup.findConstructor(packet, MethodType.methodType(Void.TYPE, titleTypes, ReflectionUtils.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE));
            } catch (IllegalAccessException | NoSuchMethodException var15) {
                var15.printStackTrace();
            }
        }

        TITLE = title;
        SUBTITLE = subtitle;
        TIMES = times;
        CLEAR = clear;
        PACKET_PLAY_OUT_TITLE = packetCtor;
        CHAT_COMPONENT_TEXT = chatComp;
    }
}
