package me.jaskri.Util.Messages;

import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class ActionBar {

    private static final boolean SPIGOT;
    private static final MethodHandle CHAT_COMPONENT_TEXT;
    private static final MethodHandle PACKET_PLAY_OUT_CHAT;
    private static final Object CHAT_MESSAGE_TYPE;
    private static final char TIME_SPECIFIER_START = '^';
    private static final char TIME_SPECIFIER_END = '|';

    private ActionBar() {
    }

    public static void sendActionBar(@Nonnull JavaPlugin plugin, @Nonnull Player player, @Nullable String message) {
        if (!Strings.isNullOrEmpty(message) && message.charAt(0) == '^') {
            int end = message.indexOf(124);
            if (end != -1) {
                int time = NumberUtils.toInt(message.substring(1, end), 0) * 20;
                if (time >= 0) {
                    sendActionBar(plugin, player, message.substring(end + 1), (long)time);
                }
            }
        }

        sendActionBar(player, message);
    }

    public static void sendActionBar(@Nonnull Player player, @Nullable String message) {
        Objects.requireNonNull(player, "Cannot send action bar to null player");
        if (SPIGOT) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        } else {
            try {
                Object component = CHAT_COMPONENT_TEXT.invoke(message);
                Object packet = PACKET_PLAY_OUT_CHAT.invoke(component, CHAT_MESSAGE_TYPE);
                ReflectionUtils.sendPacket(player, new Object[]{packet});
            } catch (Throwable var4) {
                var4.printStackTrace();
            }

        }
    }

    public static void sendPlayersActionBar(@Nullable String message) {
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            Player player = (Player)var1.next();
            sendActionBar(player, message);
        }

    }

    public static void clearActionBar(@Nonnull Player player) {
        sendActionBar(player, " ");
    }

    public static void clearPlayersActionBar() {
        Iterator var0 = Bukkit.getOnlinePlayers().iterator();

        while(var0.hasNext()) {
            Player player = (Player)var0.next();
            clearActionBar(player);
        }

    }

    public static void sendActionBarWhile(@Nonnull JavaPlugin plugin, @Nonnull final Player player, @Nullable final String message, @Nonnull final Callable<Boolean> callable) {
        (new BukkitRunnable() {
            public void run() {
                try {
                    if (!(Boolean)callable.call()) {
                        this.cancel();
                        return;
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

                ActionBar.sendActionBar(player, message);
            }
        }).runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    public static void sendActionBarWhile(@Nonnull JavaPlugin plugin, @Nonnull final Player player, @Nullable final Callable<String> message, @Nonnull final Callable<Boolean> callable) {
        (new BukkitRunnable() {
            public void run() {
                try {
                    if (!(Boolean)callable.call()) {
                        this.cancel();
                        return;
                    }

                    ActionBar.sendActionBar(player, (String)message.call());
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    public static void sendActionBar(@Nonnull JavaPlugin plugin, @Nonnull final Player player, @Nullable final String message, final long duration) {
        if (duration >= 1L) {
            (new BukkitRunnable() {
                long repeater = duration;

                public void run() {
                    ActionBar.sendActionBar(player, message);
                    this.repeater -= 40L;
                    if (this.repeater - 40L < -20L) {
                        this.cancel();
                    }

                }
            }).runTaskTimerAsynchronously(plugin, 0L, 40L);
        }
    }

    static {
        boolean exists = false;

        try {
            Player.Spigot.class.getDeclaredMethod("sendMessage", ChatMessageType.class, BaseComponent.class);
            exists = true;
        } catch (NoSuchMethodException | NoClassDefFoundError var14) {
        }

        SPIGOT = exists;
        MethodHandle packet = null;
        MethodHandle chatComp = null;
        Object chatMsgType = null;
        if (!SPIGOT) {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> packetPlayOutChatClass = ReflectionUtils.getNMSClass("network.protocol.game", "PacketPlayOutChat");
            Class<?> iChatBaseComponentClass = ReflectionUtils.getNMSClass("network.chat", "IChatBaseComponent");

            try {
                Class<?> chatMessageTypeClass = Class.forName(ReflectionUtils.NMS + (String)ReflectionUtils.v(17, "network.chat").orElse("") + "ChatMessageType");
                MethodType type = MethodType.methodType(Void.TYPE, iChatBaseComponentClass, chatMessageTypeClass);
                Object[] var8 = chatMessageTypeClass.getEnumConstants();
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Object obj = var8[var10];
                    String name = obj.toString();
                    if (name.equals("GAME_INFO") || name.equalsIgnoreCase("ACTION_BAR")) {
                        chatMsgType = obj;
                        break;
                    }
                }

                Class<?> chatComponentTextClass = ReflectionUtils.getNMSClass("network.chat", "ChatComponentText");
                chatComp = lookup.findConstructor(chatComponentTextClass, MethodType.methodType(Void.TYPE, String.class));
                packet = lookup.findConstructor(packetPlayOutChatClass, type);
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException var15) {
                try {
                    chatMsgType = 2;
                    Class<?> chatComponentTextClass = ReflectionUtils.getNMSClass("ChatComponentText");
                    chatComp = lookup.findConstructor(chatComponentTextClass, MethodType.methodType(Void.TYPE, String.class));
                    packet = lookup.findConstructor(packetPlayOutChatClass, MethodType.methodType(Void.TYPE, iChatBaseComponentClass, Byte.TYPE));
                } catch (IllegalAccessException | NoSuchMethodException var13) {
                    var13.printStackTrace();
                }
            }
        }

        CHAT_MESSAGE_TYPE = chatMsgType;
        CHAT_COMPONENT_TEXT = chatComp;
        PACKET_PLAY_OUT_CHAT = packet;
    }
}
