package me.jaskri.Util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.map.MapFont;
import org.bukkit.map.MinecraftFont;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    public static final int DEFAULT_CHAT_WIDTH = 320;

    public TextUtils() {
    }

    public static BaseComponent[] alignToRight(BaseComponent comp, int maxWidth) {
        List<BaseComponent> result = new ArrayList();
        String text = comp.toLegacyText();
        boolean isBold = false;
        boolean isColor = false;
        int start_index = 0;
        int line_width = 0;

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c == 167) {
                isColor = true;
            } else if (!isColor) {
                line_width += getCharacterWidth(c, isBold) + 1;
                if (i == text.length() - 1 || line_width >= maxWidth) {
                    result.add(new TextComponent(alignToRight(text.substring(start_index, i + 1), line_width, maxWidth)));
                    start_index = i;
                    line_width = 0;
                }
            } else {
                isColor = false;
                isBold = c == 'l' || c == 'L';
            }
        }

        return (BaseComponent[])result.toArray(new BaseComponent[result.size()]);
    }

    public static BaseComponent[] alignToCenter(BaseComponent comp, int maxWidth) {
        List<BaseComponent> result = new ArrayList();
        String text = comp.toLegacyText();
        boolean isBold = false;
        boolean isColor = false;
        int start_index = 0;
        int line_width = 0;

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c == 167) {
                isColor = true;
            } else if (!isColor) {
                line_width += getCharacterWidth(c, isBold) + 1;
                if (i == text.length() - 1 || line_width >= maxWidth) {
                    result.add(new TextComponent(alignToCenter(text.substring(start_index, i + 1), line_width, maxWidth)));
                    start_index = i;
                    line_width = 0;
                }
            } else {
                isColor = false;
                isBold = c == 'l' || c == 'L';
            }
        }

        return (BaseComponent[])result.toArray(new BaseComponent[result.size()]);
    }

    public static BaseComponent[] alignToCenter(BaseComponent comp) {
        return alignToCenter(comp, 320);
    }

    private static String alignToRight(String text, int textWidth, int width) {
        return alignBy(text, (int)Math.ceil((double)Math.abs(width - textWidth)));
    }

    private static String alignToCenter(String text, int textWidth, int width) {
        return alignBy(text, (int)(Math.ceil((double)Math.abs(width - textWidth)) / 2.0));
    }

    private static String alignBy(String text, int width) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i <= width; i += 4) {
            sb.append(' ');
        }

        return sb.append(text).toString();
    }

    public static int getTextWidth(String text) {
        if (text != null && !text.isEmpty()) {
            int result = 0;
            boolean isBold = false;
            boolean isColorChar = false;

            for(int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c == 167) {
                    isColorChar = true;
                } else if (!isColorChar) {
                    result += getCharacterWidth(c, isBold) + 1;
                } else {
                    isColorChar = false;
                    isBold = c == 'l' || c == 'L';
                }
            }

            return result;
        } else {
            return 0;
        }
    }

    public static int getCharacterWidth(char c, boolean bold) {
        MapFont.CharacterSprite sprite = MinecraftFont.Font.getChar(c);
        if (sprite != null) {
            return bold ? sprite.getWidth() + 1 : sprite.getWidth();
        } else {
            return 5;
        }
    }

    public static int getWidthToCenter(String text, int width) {
        return Math.abs(width - getTextWidth(text)) / 2;
    }

    public static int getWidthToRight(String text, int width) {
        return Math.abs(width - getTextWidth(text));
    }

    public static String emptyLine(int width) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < width; i += 4) {
            builder.append(' ');
        }

        return builder.toString();
    }

    public static String format(String text) {
        return text != null ? ChatColor.translateAlternateColorCodes('&', text) : null;
    }
}
