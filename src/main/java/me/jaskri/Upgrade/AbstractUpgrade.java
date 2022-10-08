package me.jaskri.Upgrade;

import me.jaskri.API.Upgrade.Upgrade;

import java.util.Objects;

public abstract class AbstractUpgrade implements Upgrade {

    private String name;

    public AbstractUpgrade(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AbstractUpgrade)) {
            return false;
        } else {
            AbstractUpgrade other = (AbstractUpgrade)obj;
            return this.name.equalsIgnoreCase(other.name);
        }
    }

    public String toString() {
        return "Upgrade [Name=" + this.name + "]";
    }
}
