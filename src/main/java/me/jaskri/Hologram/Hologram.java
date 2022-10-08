package me.jaskri.Hologram;

import org.bukkit.Location;

import java.util.List;

public interface Hologram {

    String getText(int var1);

    void setText(int var1, String var2);

    void addLine(String var1);

    void removeLine(int var1);

    List<String> getLines();

    void setLines(List<String> var1);

    void setVisible(int var1, boolean var2);

    boolean isVisible(int var1);

    void teleport(Location var1);

    void remove();

    int size();
}
