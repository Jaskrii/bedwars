package me.jaskri.API.Generator;

import org.bukkit.Material;

import java.util.Set;

public interface TeamGenerator extends ItemGenerator{

    Set<DropItem> getDrops();

    boolean addDrop(DropItem var1);

    DropItem getDrop(Material var1);

    boolean removeDrop(DropItem var1);

    boolean contains(DropItem var1);
}
