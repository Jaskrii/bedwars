package me.jaskri.Upgrade;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Upgrade.Upgrade;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class EnchantmentUpgrade extends AbstractUpgrade implements Upgrade {

    private Enchantment ench;
    private int level;
    private boolean unsafe;

    public EnchantmentUpgrade(String name, Enchantment ench, int level, boolean unsafe) {
        super(name);
        this.ench = ench;
        this.level = level;
        this.unsafe = unsafe;
    }

    public boolean apply(GamePlayer gp) {
        if (gp == null) {
            return false;
        } else {
            Game game = gp.getGame();
            if (game == null) {
                return false;
            } else {
                Collection<GamePlayer> team_players = game.getTeamPlayers(gp.getTeam());
                if (team_players.isEmpty()) {
                    return false;
                } else {
                    Iterator var4 = team_players.iterator();

                    while(var4.hasNext()) {
                        GamePlayer inGame = (GamePlayer)var4.next();
                        Player player = inGame.getPlayer();
                        ListIterator var7 = player.getInventory().iterator();

                        while(var7.hasNext()) {
                            ItemStack item = (ItemStack)var7.next();
                            if (item != null) {
                                if (this.unsafe) {
                                    item.addUnsafeEnchantment(this.ench, this.level);
                                } else if (this.ench.canEnchantItem(item)) {
                                    item.addEnchantment(this.ench, this.level);
                                }
                            }
                        }
                    }

                    return true;
                }
            }
        }
    }
}
