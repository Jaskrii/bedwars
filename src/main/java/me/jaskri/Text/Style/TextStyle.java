package me.jaskri.Text.Style;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;

public enum TextStyle {

    MAGIC(ChatColor.MAGIC) {
        public void apply(BaseComponent component) {
            if (component != null) {
                component.setObfuscated(true);
            }

        }
    },
    BOLD(ChatColor.BOLD) {
        public void apply(BaseComponent component) {
            if (component != null) {
                component.setBold(true);
            }

        }
    },
    STRIKETHROUGH(ChatColor.STRIKETHROUGH) {
        public void apply(BaseComponent component) {
            if (component != null) {
                component.setStrikethrough(true);
            }

        }
    },
    UNDERLINE(ChatColor.UNDERLINE) {
        public void apply(BaseComponent component) {
            if (component != null) {
                component.setUnderlined(true);
            }

        }
    },
    ITALIC(ChatColor.ITALIC) {
        public void apply(BaseComponent component) {
            if (component != null) {
                component.setItalic(true);
            }

        }
    };

    private ChatColor color;

    private TextStyle(ChatColor color) {
        this.color = color;
    }

    public ChatColor toChatColor() {
        return this.color;
    }

    public net.md_5.bungee.api.ChatColor toBungeeChatColor() {
        return this.color.asBungee();
    }

    public void apply(BaseComponent component) {
    }
}
