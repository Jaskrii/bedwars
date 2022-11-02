package me.jaskri.Util;

import org.bukkit.Bukkit;

public enum Version {

    UNSUPPORTED("Unsupported"),
    V1_8_R3("v1_8_R3"),
    V1_9_R1("v1_9_R1"),
    V1_9_R2("v1_9_R2"),
    V1_10_R1("v1_10_R1"),
    V1_11_R1("v1_11_R1"),
    V1_12_R1("v1_12_R1"),
    V1_13_R1("v1_13_R1"),
    V1_13_R2("v1_13_R2"),
    V1_14_R1("v1_14_R1"),
    V1_15_R1("v1_15_R1"),
    V1_16_R1("v1_16_R1"),
    V1_16_R2("v1_16_R2"),
    V1_16_R3("v1_16_R3"),
    V1_17_R1("v1_17_R1"),
    V1_18_R1("v1_18_R1"),
    V1_18_R2("v1_18_R2"),
    V1_19_R1("v1_19_R1");

    private static final Version CURRENT;
    private static final String NAME = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private final String version;

    private Version(String version) {
        this.version = version;
    }

    public String toString() {
        return this.version;
    }

    public boolean isNewAPI() {
        return this != UNSUPPORTED && this.ordinal() > 6;
    }

    public boolean isNewerThan(Version version) {
        if (this != UNSUPPORTED && version != UNSUPPORTED) {
            return this.ordinal() > version.ordinal();
        } else {
            return false;
        }
    }

    public static Version getVersion() {
        return CURRENT;
    }

    public static String getVersionName() {
        return NAME;
    }

    static {
        Version current = UNSUPPORTED;
        Version[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Version version = var1[var3];
            if (version.toString().equals(NAME)) {
                current = version;
                break;
            }
        }

        CURRENT = current;
    }
}
