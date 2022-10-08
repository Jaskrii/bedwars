package me.jaskri.API.Shop;

import java.util.List;

public interface Shop {

    List<Category> getCategories();

    void setCategories(List<Category> var1);

    Category getCategory(int var1);

    boolean addCategory(Category var1);

    boolean removeCategory(Category var1);

    Category removeCategory(int var1);

    void setCategory(int var1, Category var2);

    boolean openShop(GamePlayer var1, Category var2);

    boolean openShop(GamePlayer var1, int var2);

    boolean openShop(GamePlayer var1);

    boolean contains(Category var1);
}
