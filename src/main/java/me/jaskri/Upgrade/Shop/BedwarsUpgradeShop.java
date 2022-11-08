package me.jaskri.Upgrade.Shop;

import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Shop.Item.Buyable;
import me.jaskri.API.Shop.Item.ItemDescription;
import me.jaskri.API.Team.GameTeam;
import me.jaskri.API.Trap.Trap;
import me.jaskri.API.Upgrade.Shop.Item.TrapItem;
import me.jaskri.API.Upgrade.Shop.UpgradeShop;
import me.jaskri.Manager.ItemManager;
import me.jaskri.Util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BedwarsUpgradeShop implements UpgradeShop {

    private static final ItemStack[] EMPTY_TRAPS = new ItemStack[3];
    private static final ItemStack SEPARATOR;
    private Map<Integer, Buyable> items = new HashMap();

    public BedwarsUpgradeShop() {
    }

    public Map<Integer, Buyable> getItems() {
        return new HashMap(this.items);
    }

    public Buyable getItem(int slot) {
        return (Buyable)this.items.get(slot);
    }

    public boolean addItem(int slot, Buyable item) {
        if (item != null && (slot < 27 || slot > 35)) {
            this.items.put(slot, item);
            return true;
        } else {
            return false;
        }
    }

    public Buyable removeItem(int slot) {
        return (Buyable)this.items.remove(slot);
    }

    public boolean openShop(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            GameTeam team = gp.getGame().getGameTeam(gp.getTeam());
            if (team == null) {
                return false;
            } else {
                return gp.getPlayer().openInventory(this.apply(gp, team, Bukkit.createInventory((InventoryHolder) null, 54, "Upgrades & Traps"))) != null;
            }
        }
    }

    public boolean contains(Buyable buyable) {
        return this.items.containsValue(buyable);
    }

    private Inventory apply(GamePlayer gp, GameTeam team, Inventory inv) {
        List<Trap> traps = team.getTrapManager().getTraps();
        int trapCount = 0;
        Iterator var6 = this.items.entrySet().iterator();

        while(true) {
            Buyable buyable;
            ItemStack display;
            do {
                do {
                    if (!var6.hasNext()) {
                        for(int i = 27; i <= 35; ++i) {
                            inv.setItem(i, SEPARATOR);
                        }

                        while(trapCount < 3) {
                            inv.setItem(39 + trapCount, this.getEmptyTrapDisplayItem(trapCount++));
                        }

                        return inv;
                    }

                    Map.Entry<Integer, Buyable> entry = (Map.Entry)var6.next();
                    buyable = (Buyable)entry.getValue();
                    display = buyable.getDisplayItem(gp);
                    inv.setItem((Integer)entry.getKey(), display);
                } while(trapCount >= 3);
            } while(!(buyable instanceof TrapItem));

            TrapItem trapItem = (TrapItem)buyable;
            Trap trap = trapItem.getTrap();

            for(int i = 0; i < traps.size() && i < 3; ++i) {
                Trap trapsTrap = (Trap)traps.get(i);
                if (trapsTrap.equals(trap)) {
                    inv.setItem(39 + trapCount++, this.getTrapDisplayItem(display, trapItem.getDescription()));
                }
            }
        }
    }

    private ItemStack getEmptyTrapDisplayItem(int index) {
        ItemManager manager = new ItemManager(EMPTY_TRAPS[index].clone());
        manager.setName("§cTrap #" + (index + 1) + ": No Trap!");
        manager.addToLore("§7Next trap: §b" + (int)Math.pow(2.0, (double)index) + " Diamond");
        return manager.getItem();
    }

    private ItemStack getTrapDisplayItem(ItemStack item, ItemDescription desc) {
        ItemManager manager = new ItemManager(item);
        manager.addItemFlags(ItemFlag.values());
        manager.setName(ChatColor.GREEN + manager.getName());
        List<String> lore = new ArrayList(desc.getSize());
        desc.apply(lore);
        manager.setLore(lore);
        return manager.getItem();
    }

    static {
        ItemManager empty = new ItemManager(XMaterial.LIGHT_GRAY_STAINED_GLASS.parseItem());
        List<String> lore = new ArrayList();
        lore.add("§7The first enemy to walk into");
        lore.add("§7your base will trigger this");
        lore.add("§7trap!");
        lore.add("");
        lore.add("§7Purchasing a trap will queue it");
        lore.add("§7here. Its cost will scale based");
        lore.add("§7on the number of traps queued.");
        lore.add("");
        empty.setLore(lore);
        EMPTY_TRAPS[0] = empty.getItem().clone();
        lore.set(0, "§7The second enemy to walk into");
        EMPTY_TRAPS[1] = empty.setAmount(2).getItem().clone();
        lore.set(0, "§7The third enemy to walk into");
        EMPTY_TRAPS[2] = empty.setAmount(3).getItem().clone();
        ItemManager separator = new ItemManager(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem());
        separator.setName("§8⬆ §7Purchasable");
        separator.addToLore("§8⬇ §7Traps Queue");
        SEPARATOR = separator.getItem();
    }
}
