package me.jaskri.Trap;

import com.google.common.base.Preconditions;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Trap.TrapTarget;

import java.util.Objects;

public class AbstractTrap implements Trap {

    protected String name;
    protected TrapTarget target;
    protected int duration;

    public AbstractTrap(String name, TrapTarget target, int duration) {
        Preconditions.checkNotNull(name, "Trap name cannot be null!");
        Preconditions.checkNotNull(target, "Trap target cannot be null!");
        this.name = name;
        this.target = target;
        this.duration = duration;
    }

    public String getName() {
        return this.name;
    }

    public TrapTarget getTarget() {
        return this.target;
    }

    public int getDuration() {
        return this.duration;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name.toLowerCase(), this.target});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AbstractTrap)) {
            return false;
        } else {
            AbstractTrap other = (AbstractTrap)obj;
            return this.target == other.target && this.name.equalsIgnoreCase(other.name);
        }
    }
}
