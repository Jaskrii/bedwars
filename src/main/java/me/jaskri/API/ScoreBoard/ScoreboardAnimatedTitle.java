package me.jaskri.API.ScoreBoard;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScoreboardAnimatedTitle {

    private List<String> titles = new ArrayList();
    private long updateTicks = 20L;
    private int index = 0;

    public Scoreboard$AnimatedTitle(List<String> titles, long updateTicks) {
        Preconditions.checkNotNull(titles, "Titles cannot be null");
        Iterator var4 = titles.iterator();

        while(var4.hasNext()) {
            String title = (String)var4.next();
            if (title != null) {
                this.titles.add(ChatColor.translateAlternateColorCodes('&', title));
            }
        }

        if (titles.isEmpty()) {
            throw new IllegalArgumentException("Titles cannot be empty!");
        } else {
            this.updateTicks = titles.size() > 1 ? updateTicks : -1L;
        }
    }

    public long getUpdateTicks() {
        return this.updateTicks;
    }

    public void setUpdateTicks(long ticks) {
        if (ticks > 0L) {
            this.updateTicks = ticks;
        }

    }

    public int size() {
        return this.titles.size();
    }

    public synchronized String next() {
        return this.index != this.titles.size() - 1 ? (String)this.titles.get(++this.index) : (String)this.titles.get(this.index = 0);
    }
}
