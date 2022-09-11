package me.jaskri.bedwars.API.PACKAGE.Generator;

import com.google.common.base.Preconditions;
import jdk.internal.loader.Resource;

import java.util.HashMap;
import java.util.Map;

public class GeneratorSpeed {

    private static final Map<String, GeneratorSpeed> BY_NAME = new HashMap();
    private Map<Resource, Integer> speeds = new HashMap();
    private String name;

    public GeneratorSpeed(String name) {
        Preconditions.checkNotNull(name, "Forge speed name cannot be null");
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getDropsPerMinute(Resource rsc) {
        Integer result = (Integer)this.speeds.get(rsc);
        return result != null ? result : 0;
    }

    public void setDropsPerMinute(Resource rsc, int dropsPerMin) {
        if (this.hasResource(rsc)) {
            throw new IllegalStateException("Resource already exists and cannot be changed!");
        } else {
            this.speeds.put(rsc, dropsPerMin);
        }
    }

    public boolean hasResource(Resource rsc) {
        return rsc != null && this.speeds.containsKey(rsc);
    }

    public static GeneratorSpeed getByName(String name) {
        return name != null ? (GeneratorSpeed)BY_NAME.get(name.toLowerCase()) : null;
    }

    public static void registerSpeed(GeneratorSpeed speed) {
        Preconditions.checkNotNull(speed, "Cannot register null speed");
        if (BY_NAME.containsKey(speed.getName().toLowerCase())) {
            throw new IllegalArgumentException("Cannot register existant speed");
        } else {
            BY_NAME.put(speed.getName().toLowerCase(), speed);
        }
    }
}
