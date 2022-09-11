package me.jaskri.bedwars.API.PACKAGE.Generator;

import org.bukkit.Location;

public interface ItemGenerator {

    Location getDropLocation();

    void setDropLocation(Location var1);

    void start();

    void stop();

    boolean isDropping();
}
