package me.jaskri.Text;

import com.google.common.base.Preconditions;
import me.jaskri.Text.Style.TextColor;
import me.jaskri.Text.Style.TextStyle;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Text {

    private BaseComponent main;
    private String text;

    public Text(BaseComponent comp) {
        Preconditions.checkNotNull(comp, "Component cannot be null!");
        this.main = comp;
        this.text = comp.toPlainText();
    }

    public Text(String text) {
        Preconditions.checkNotNull(text, "Text cannot be null");
        this.text = text;
        this.main = new TextComponent(text);
    }

    public Text() {
        this("");
    }

    public String getText() {
        return this.text;
    }

    public Text append(BaseComponent comp) {
        if (comp != null) {
            this.main.addExtra(comp);
        }

        return this;
    }

    public Text append(Text text) {
        if (text != null) {
            this.main.addExtra(text.main);
        }

        return this;
    }

    public Text color(TextColor color) {
        if (color != null) {
            this.main.setColor(color.toBungeeChatColor());
        }

        return this;
    }

    public Text decorate(TextStyle style) {
        if (style != null) {
            style.apply(this.main);
        }

        return this;
    }

    public Text setClickEvent(ClickEvent event) {
        if (event != null) {
            this.main.setClickEvent(event);
        }

        return this;
    }

    public Text setHoverEvent(HoverEvent event) {
        if (event != null) {
            this.main.setHoverEvent(event);
        }

        return this;
    }

    public BaseComponent toBaseComponent() {
        return this.main;
    }

    public boolean sendMessage(Player player) {
        if (player == null) {
            return false;
        } else {
            player.spigot().sendMessage(this.main);
            return true;
        }
    }
}
