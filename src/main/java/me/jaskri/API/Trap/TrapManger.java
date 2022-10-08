package me.jaskri.API.Trap;

import java.util.List;

public interface TrapManger {

    List<Trap> getTraps();

    void addTrap(Trap var1);

    void removeTrap(Trap var1);

    boolean contains(Trap var1);
}
