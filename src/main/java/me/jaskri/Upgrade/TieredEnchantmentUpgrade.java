package me.jaskri.Upgrade;

import me.jaskri.API.Game.player.GamePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class TieredEnchantmentUpgrade extends AbstractTieredUpgrade{

    private Enchantment ench;
    private boolean unsafe;

    public TieredEnchantmentUpgrade(String name, Enchantment ench, int max, boolean unsafe) {
        super(name, 0, max);
        this.ench = ench;
        this.unsafe = unsafe;
    }

    public boolean apply(GamePlayer gp) {
        if (this.current != 0 && gp != null) {
            Collection<GamePlayer> team_players = gp.getGame().getTeamPlayers(gp.getTeam());
            if (team_players.isEmpty()) {
                return false;
            } else {
                Iterator var3 = team_players.iterator();

                while(var3.hasNext()) {
                    GamePlayer inGame = (GamePlayer)var3.next();
                    Player player = inGame.getPlayer();
                    ItemStack[] var6 = player.getEquipment().getArmorContents();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        ItemStack item = var6[var8];
                        if (item != null) {
                            if (this.unsafe) {
                                item.addUnsafeEnchantment(this.ench, this.current);
                            } else if (this.ench.canEnchantItem(item)) {
                                item.addEnchantment(this.ench, this.current);
                            }
                        }
                    }

                    ListIterator var10 = player.getInventory().iterator();

                    while(var10.hasNext()) {
                        ItemStack item = (ItemStack)var10.next();
                        if (item != null) {
                            if (this.unsafe) {
                                item.addUnsafeEnchantment(this.ench, this.current);
                            } else if (this.ench.canEnchantItem(item)) {
                                item.addEnchantment(this.ench, this.current);
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
