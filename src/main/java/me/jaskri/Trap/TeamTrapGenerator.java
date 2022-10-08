package me.jaskri.Trap;

import com.google.common.base.Preconditions;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Trap.TrapManger;

import java.util.ArrayList;
import java.util.List;

public class TeamTrapGenerator implements TrapManger {

    private List<Trap> queue;
    private int limit;

    public TeamTrapManager(int limit) {
        Preconditions.checkArgument(limit >= 1, "Traps limit must be greater than 0!");
        this.limit = limit;
        this.queue = new ArrayList(limit);
    }

    public TeamTrapManager() {
        this.limit = 3;
        this.queue = new ArrayList(3);
    }

    public List<Trap> getTraps() {
        return new ArrayList(this.queue);
    }

    public void addTrap(Trap trap) {
        if (trap != null && this.queue.size() < this.limit) {
            this.queue.add(trap);
        }

    }

    public void removeTrap(Trap trap) {
        if (trap != null) {
            this.queue.remove(trap);
        }

    }

    public boolean contains(Trap trap) {
        return trap != null && this.queue.contains(trap);
    }
}
