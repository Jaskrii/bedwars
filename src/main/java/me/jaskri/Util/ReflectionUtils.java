package me.jaskri.Util;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public final class ReflectionUtils {

    public static final String VERSION;
    public static final int VER;
    public static final String CRAFTBUKKIT;
    public static final String NMS;
    private static final MethodHandle PLAYER_CONNECTION;
    private static final MethodHandle GET_HANDLE;
    private static final MethodHandle SEND_PACKET;

    private ReflectionUtils() {
    }

    public static <T> VersionHandler<T> v(int version, T handle) {
        return new VersionHandler(version, handle);
    }

    public static <T> CallableVersionHandler<T> v(int version, Callable<T> handle) {
        return new CallableVersionHandler(version, handle);
    }

    public static boolean supports(int version) {
        return VER >= version;
    }

    @Nullable
    public static Class<?> getNMSClass(@Nonnull String newPackage, @Nonnull String name) {
        if (supports(17)) {
            name = newPackage + '.' + name;
        }

        return getNMSClass(name);
    }

    @Nullable
    public static Class<?> getNMSClass(@Nonnull String name) {
        try {
            return Class.forName(NMS + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    @Nonnull
    public static CompletableFuture<Void> sendPacket(@Nonnull Player player, @Nonnull Object... packets) {
        return CompletableFuture.runAsync(() -> {
            sendPacketSync(player, packets);
        }).exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
    }

    public static void sendPacketSync(@Nonnull Player player, @Nonnull Object... packets) {
        try {
            Object handle = GET_HANDLE.invoke(player);
            Object connection = PLAYER_CONNECTION.invoke(handle);
            if (connection != null) {
                Object[] var4 = packets;
                int var5 = packets.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Object packet = var4[var6];
                    SEND_PACKET.invoke(connection, packet);
                }
            }
        } catch (Throwable var8) {
            var8.printStackTrace();
        }

    }

    @Nullable
    public static Object getHandle(@Nonnull Player player) {
        Objects.requireNonNull(player, "Cannot get handle of null player");

        try {
            return GET_HANDLE.invoke(player);
        } catch (Throwable var2) {
            var2.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Object getConnection(@Nonnull Player player) {
        Objects.requireNonNull(player, "Cannot get connection of null player");

        try {
            Object handle = GET_HANDLE.invoke(player);
            return PLAYER_CONNECTION.invoke(handle);
        } catch (Throwable var2) {
            var2.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getCraftClass(@Nonnull String name) {
        try {
            return Class.forName(CRAFTBUKKIT + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Class<?> getArrayClass(String clazz, boolean nms) {
        clazz = "[L" + (nms ? NMS : CRAFTBUKKIT) + clazz + ';';

        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Class<?> toArrayClass(Class<?> clazz) {
        try {
            return Class.forName("[L" + clazz.getName() + ';');
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    static {
        String found = null;
        Package[] var1 = Package.getPackages();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Package pack = var1[var3];
            String name = pack.getName();
            if (name.startsWith("org.bukkit.craftbukkit.v")) {
                found = pack.getName().split("\\.")[3];

                try {
                    Class.forName("org.bukkit.craftbukkit." + found + ".entity.CraftPlayer");
                    break;
                } catch (ClassNotFoundException var9) {
                    found = null;
                }
            }
        }

        if (found == null) {
            throw new IllegalArgumentException("Failed to parse server version. Could not find any package starting with name: 'org.bukkit.craftbukkit.v'");
        } else {
            VERSION = found;
            VER = Integer.parseInt(VERSION.substring(1).split("_")[1]);
            CRAFTBUKKIT = "org.bukkit.craftbukkit." + VERSION + '.';
            NMS = (String)v(17, (Object)"net.minecraft.").orElse("net.minecraft.server." + VERSION + '.');
            Class<?> entityPlayer = getNMSClass("server.level", "EntityPlayer");
            Class<?> craftPlayer = getCraftClass("entity.CraftPlayer");
            Class<?> playerConnection = getNMSClass("server.network", "PlayerConnection");
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle sendPacket = null;
            MethodHandle getHandle = null;
            MethodHandle connection = null;

            try {
                connection = lookup.findGetter(entityPlayer, (String)v(17, (Object)"b").orElse("playerConnection"), playerConnection);
                getHandle = lookup.findVirtual(craftPlayer, "getHandle", MethodType.methodType(entityPlayer));
                sendPacket = lookup.findVirtual(playerConnection, (String)v(18, (Object)"a").orElse("sendPacket"), MethodType.methodType(Void.TYPE, getNMSClass("network.protocol", "Packet")));
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException var8) {
                var8.printStackTrace();
            }

            PLAYER_CONNECTION = connection;
            SEND_PACKET = sendPacket;
            GET_HANDLE = getHandle;
        }
    }
    public static final class CallableVersionHandler {

        private int version;
        private Callable<Throwable> handle;

        private CallableVersionHandler(int version, Callable<Throwable> handle) {
            if (ReflectionUtils.supports(version)) {
                this.version = version;
                this.handle = handle;
            }

        }

        public CallableVersionHandler<Throwable> v(int version, Callable<Throwable> handle) {
            if (version == this.version) {
                throw new IllegalArgumentException("Cannot have duplicate version handles for version: " + version);
            } else {
                if (version > this.version && ReflectionUtils.supports(version)) {
                    this.version = version;
                    this.handle = handle;
                }

                return this;
            }
        }

        public Throwable orElse(Callable<Throwable> handle) {
            try {
                return (this.version == 0 ? handle : this.handle).call();
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }
    }

    public static final class VersionHandler {

        private int version;
        private T handle;

        private VersionHandler(int version, Throwable handle) {
            if (ReflectionUtils.supports(version)) {
                this.version = version;
                this.handle = handle;
            }

        }

        public VersionHandler<Throwable> v(int version, Throwable handle) {
            if (version == this.version) {
                throw new IllegalArgumentException("Cannot have duplicate version handles for version: " + version);
            } else {
                if (version > this.version && ReflectionUtils.supports(version)) {
                    this.version = version;
                    this.handle = handle;
                }

                return this;
            }
        }

        public Throwable orElse(Throwable handle) {
            return this.version == 0 ? handle : this.handle;
        }
    }
    }
}
