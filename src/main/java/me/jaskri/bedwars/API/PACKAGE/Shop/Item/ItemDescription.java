package me.jaskri.bedwars.API.PACKAGE.Shop.Item;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemDescription implements Cloneable{

    private List<String> description;

    public ItemDescription(List<String> description) {
        this.description = new ArrayList();
        this.setDescription(description);
    }

    public ItemDescription(ItemDescription description) {
        this(description.description);
    }

    public ItemDescription() {
        this.description = new ArrayList();
    }

    public List<String> getDescription() {
        return new ArrayList(this.description);
    }

    public void setDescription(List<String> description) {
        if (description != null) {
            this.description = new ArrayList(description);
        }

    }

    public void apply(List<String> list) {
        if (list != null) {
            Iterator var2 = this.description.iterator();

            while(var2.hasNext()) {
                String line = (String)var2.next();
                list.add(ChatColor.GRAY + line);
            }

        }
    }

    public int getSize() {
        return this.description.size();
    }

    public boolean isEmpty() {
        return this.description.isEmpty();
    }

    public ItemDescription clone() {
        try {
            ItemDescription result = (ItemDescription)super.clone();
            result.description = new ArrayList(this.description);
            return result;
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
