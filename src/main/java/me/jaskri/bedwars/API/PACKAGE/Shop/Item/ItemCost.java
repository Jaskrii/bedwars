package me.jaskri.bedwars.API.PACKAGE.Shop.Item;

import me.jaskri.bedwars.API.PACKAGE.Generator.Resource;

import java.util.Objects;

public class ItemCost implements Cloneable{

    private Resource resource;
    private int price;

    public ItemCost(Resource resource, int price) {
        this.setResource(resource);
        this.setPrice(price);
    }

    public ItemCost(ItemCost cost) {
        this.resource = cost.resource;
        this.price = cost.price;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource rsc) {
        if (rsc != null && rsc != Resource.FREE) {
            this.resource = rsc;
        } else {
            this.resource = Resource.FREE;
            this.price = 0;
        }
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        if (price > 0) {
            this.price = price;
        } else {
            this.setResource(Resource.FREE);
        }
    }

    public ItemCost clone() {
        try {
            return (ItemCost)super.clone();
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "ItemCost [Resource=" + this.resource + ", Price=" + this.price + "]";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.price, this.resource});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ItemCost)) {
            return false;
        } else {
            ItemCost other = (ItemCost)obj;
            return this.price == other.price && Objects.equals(this.resource, other.resource);
        }
    }
}
