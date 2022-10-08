package me.jaskri.API.Generator;

import com.google.common.base.Preconditions;

public class GeneratorTier {

    private String name;
    private int time;
    private int limit;

    public GeneratorTier(String name, int time, int limit) {
        Preconditions.checkNotNull(name, "Tier display name cannot be null");
        this.name = name;
        this.time = time;
        this.limit = limit;
    }

    public String getDisplayName() {
        return this.name;
    }

    public int getDropTime() {
        return this.time;
    }

    public int getDropLimit() {
        return this.limit;
    }

    public void setDropLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        }

    }
}
