package me.jaskri.Text;

import me.jaskri.Text.Style.TextAlign;
import me.jaskri.Util.TextUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextSection {

    private List<BaseComponent> lines;
    private final int width;

    public TextSection(int width) {
        this.lines = new ArrayList();
        this.width = width;
    }

    public TextSection() {
        this(320);
    }

    private TextSection appendEmpty() {
        this.lines.add((Object)null);
        return this;
    }

    private TextSection appendToLeft(Text text) {
        this.lines.add(text.toBaseComponent());
        return this;
    }

    private TextSection appendToCenter(Text text) {
        BaseComponent[] var2 = TextUtils.alignToCenter(text.toBaseComponent(), this.width);
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BaseComponent comp = var2[var4];
            this.lines.add(comp);
        }

        return this;
    }

    private TextSection appendToRight(Text text) {
        BaseComponent[] var2 = TextUtils.alignToRight(text.toBaseComponent(), this.width);
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BaseComponent comp = var2[var4];
            this.lines.add(comp);
        }

        return this;
    }

    private TextSection appendLine(Text text, int alignWidth) {
        if (alignWidth >= this.width) {
            return this.appendToLeft(text);
        } else {
            BaseComponent builder = new TextComponent(TextUtils.emptyLine(alignWidth));
            builder.addExtra(text.toBaseComponent());
            this.lines.add(builder);
            return this;
        }
    }

    public TextSection append(Text text, TextAlign align) {
        if (text == null) {
            return this.appendEmpty();
        } else if (align == null) {
            return this.appendToLeft(text);
        } else {
            switch (align) {
                case LEFT:
                    return this.appendToLeft(text);
                case CENTER:
                    return this.appendToCenter(text);
                case RIGHT:
                    return this.appendToRight(text);
                default:
                    return this;
            }
        }
    }

    public TextSection append(Text text, int alignBy) {
        if (text == null) {
            return this.appendEmpty();
        } else {
            return alignBy > 0 ? this.appendLine(text, alignBy) : this.appendToLeft(text);
        }
    }

    public TextSection append(Text text) {
        return this.append(text, TextAlign.LEFT);
    }

    private TextSection appendSectionToLeft(TextSection section) {
        Iterator var2 = section.lines.iterator();

        while(var2.hasNext()) {
            BaseComponent comp = (BaseComponent)var2.next();
            this.lines.add(comp);
        }

        return this;
    }

    private TextSection appendSectionToCenter(TextSection section) {
        String empty = TextUtils.emptyLine(Math.abs(this.width - section.width) / 2);
        Iterator var3 = section.lines.iterator();

        while(var3.hasNext()) {
            BaseComponent line = (BaseComponent)var3.next();
            BaseComponent[] var5 = TextUtils.alignToCenter(line, this.width);
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                BaseComponent comp = var5[var7];
                TextComponent toAdd = new TextComponent(empty);
                toAdd.addExtra(comp);
                this.lines.add(toAdd);
            }
        }

        return this;
    }

    private TextSection appendSectionToRight(TextSection section) {
        Iterator var2 = section.lines.iterator();

        while(var2.hasNext()) {
            BaseComponent line = (BaseComponent)var2.next();
            BaseComponent[] var4 = TextUtils.alignToRight(line, section.width);
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                BaseComponent comp = var4[var6];
                this.lines.add(comp);
            }
        }

        return this;
    }

    private TextSection appendSectionBy(TextSection section, int width) {
        String empty = TextUtils.emptyLine(width);
        Iterator var4 = section.lines.iterator();

        while(var4.hasNext()) {
            BaseComponent comp = (BaseComponent)var4.next();
            BaseComponent builder = new TextComponent(empty);
            builder.addExtra(comp);
            this.lines.add(builder);
        }

        return this;
    }

    public TextSection append(TextSection section, TextAlign align) {
        if (section == null) {
            return this.appendEmpty();
        } else if (align != null && align != TextAlign.LEFT) {
            switch (align) {
                case CENTER:
                    return this.appendSectionToCenter(section);
                case RIGHT:
                    return this.appendSectionToRight(section);
                default:
                    return this;
            }
        } else {
            return this.appendSectionToLeft(section);
        }
    }

    public TextSection append(TextSection section, int alignBy) {
        if (section == null) {
            return this.appendEmpty();
        } else {
            return alignBy >= 3 ? this.appendSectionBy(section, alignBy) : this.appendSectionToLeft(section);
        }
    }

    public TextSection append(TextSection section) {
        return this.append(section, TextAlign.LEFT);
    }

    public TextSection appendString(String text, int width) {
        TextComponent comp = new TextComponent(TextUtils.emptyLine(width));
        comp.addExtra(text);
        this.lines.add(comp);
        return this;
    }

    public TextSection append(String text, TextAlign align) {
        if (text == null) {
            return this.appendEmpty();
        } else if (align != null && align != TextAlign.LEFT) {
            switch (align) {
                case CENTER:
                    BaseComponent[] var3 = TextUtils.alignToCenter(new TextComponent(text), this.width);
                    int var4 = var3.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        BaseComponent bc = var3[var5];
                        this.lines.add(bc);
                    }

                    return this;
                case RIGHT:
                    return this.appendString(text, TextUtils.getWidthToRight(text, this.width));
                default:
                    return this;
            }
        } else {
            return this.appendString(text, 0);
        }
    }

    public TextSection append(String text, int space) {
        return text == null ? this.appendEmpty() : this.appendString(text, space * 4);
    }

    public TextSection append(String text) {
        return this.append((String)text, 0);
    }

    public BaseComponent[] toBaseComponent() {
        return (BaseComponent[])this.lines.toArray(new BaseComponent[this.lines.size()]);
    }

    public boolean sendMessage(Player player) {
        if (player == null) {
            return false;
        } else {
            Iterator var2 = this.lines.iterator();

            while(var2.hasNext()) {
                BaseComponent comp = (BaseComponent)var2.next();
                if (comp != null) {
                    player.spigot().sendMessage(comp);
                } else {
                    player.sendMessage("");
                }
            }

            return true;
        }
    }
}
