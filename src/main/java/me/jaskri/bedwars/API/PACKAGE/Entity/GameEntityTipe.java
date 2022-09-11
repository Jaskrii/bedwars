package me.jaskri.bedwars.API.PACKAGE.Entity;

import java.util.HashMap;
import java.util.Map;

public enum GameEntityTipe {
    BED_BUG("Bed Bug"),
    BODY_GUARD("Body Guard"),
    DRAGON("Dragon"),
    CUSTOM("Custom");

    private static final Map<String, GameEntityTipe> BY_NAME = new HashMap<>();
    private final String type;

    private GameEntityType(String type) {
        this.type = type;
    }

    public static GameEntityTipe getByName(String name) {
        return name != null ? (GameEntityTipe) BY_NAME.get(name.toLowerCase()) : null;
    }

    static {
        GameEntityTipe[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            GameEntityTipe type = var0[var2];
            BY_NAME.put(type.type.toLowerCase(), type);

        }

    }
}
