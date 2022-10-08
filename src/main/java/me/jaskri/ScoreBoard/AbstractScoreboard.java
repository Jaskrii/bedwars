package me.jaskri.ScoreBoard;

import com.google.common.base.Preconditions;
import me.jaskri.API.ScoreBoard.ScoreBoard;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractScoreboard implements ScoreBoard {

    protected Map<Integer, String> lines = new HashMap();
    protected ScoreBoard.AnimatedTitle title;
    protected String display;
    protected DisplaySlot slot;

    public AbstractScoreboard(ScoreBoard.AnimatedTitle title) {
        Preconditions.checkNotNull(title, "Scoreboard title cannot be null!");
        this.title = title;
        this.display = title.next();
        this.slot = DisplaySlot.SIDEBAR;
    }

    public ScoreBoard.AnimatedTitle getTitle() {
        return this.title;
    }

    public void setTitle(ScoreBoard.AnimatedTitle title) {
        if (title != null) {
            this.title = title;
        }

    }

    public String getDisplayTitle() {
        return this.display;
    }

    public void setDisplayTitle(String title) {
        if (title != null) {
            this.display = title;
        }

    }

    public Map<Integer, String> getLines() {
        return new HashMap(this.lines);
    }

    public String getText(int line) {
        return (String)this.lines.get(line);
    }

    public void setText(int line, String text) {
        if (text != null && line >= 1 && line <= 15) {
            this.lines.put(line, ChatUtils.format(text));
        }

    }

    public String removeText(int line) {
        return (String)this.lines.remove(line);
    }

    public DisplaySlot getDisplaySlot() {
        return this.slot;
    }

    public void setDisplaySlot(DisplaySlot slot) {
        if (slot != null) {
            this.slot = slot;
        }

    }
}
