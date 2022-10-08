package me.jaskri.Upgrade;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Generator.DropItem;
import me.jaskri.API.Generator.Resource;
import me.jaskri.API.Generator.TeamGenerator;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ForgeUpgrade extends AbstractTieredUpgrade{

    public ForgeUpgrade() {
        super("Forge Upgrade", 0, 4);
    }

    private void increaseDropSpeed(TeamGenerator gen, Material material, int speed) {
        DropItem item = gen.getDrop(material);
        if (item != null) {
            item.setDropsPerMinute(item.getDropsPerMinute() / speed);
        }
    }

    private void addEmeraldDrop(Game game, TeamGenerator gen) {
        Resource resource = Resource.EMERALD;
        int limit = Bedwars.getInstance().getTeamForgeSettings().getDropLimit(game.getMode(), resource);
        if (limit <= 0) {
            limit = 2;
        }

        int speed = game.getArena().getGeneratorSpeed().getDropsPerMinute(resource);
        if (speed <= 0) {
            speed = 60;
        }

        gen.addDrop(new DropItem(new ItemStack(Material.EMERALD), speed, limit));
    }

    public boolean apply(GamePlayer gp) {
        if (this.current != 0 && gp != null) {
            TeamGenerator gen = gp.getGame().getTeamGenerator(gp.getTeam());
            if (gen == null) {
                return false;
            } else {
                switch (this.current) {
                    case 1:
                        this.increaseDropSpeed(gen, Material.IRON_INGOT, 2);
                        this.increaseDropSpeed(gen, Material.GOLD_INGOT, 2);
                        break;
                    case 2:
                        this.increaseDropSpeed(gen, Material.IRON_INGOT, 2);
                        this.increaseDropSpeed(gen, Material.GOLD_INGOT, 2);
                        break;
                    case 3:
                        this.addEmeraldDrop(gp.getGame(), gen);
                        break;
                    case 4:
                        this.increaseDropSpeed(gen, Material.EMERALD, 2);
                        this.increaseDropSpeed(gen, Material.IRON_INGOT, 2);
                        this.increaseDropSpeed(gen, Material.GOLD_INGOT, 2);
                        break;
                    default:
                        return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
