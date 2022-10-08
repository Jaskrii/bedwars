package me.jaskri.Player;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameReward;
import me.jaskri.API.Game.player.ArmorType;
import me.jaskri.API.Game.player.GameInventory;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
import me.jaskri.API.Team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;

public class BedwarsPlayer implements GamePlayer {

    private Player player;
    private Game game;
    private GameInventory inventory;
    private GameStatisticManager stats;
    private ArmorType armor;
    private Team team;

    public BedwarsPlayer(Player player, Game game, Team team) {
        this.armor = ArmorType.LEATHER;
        Preconditions.checkNotNull(player, "Player cannot be null");
        Preconditions.checkNotNull(game, "Game cannot be null");
        Preconditions.checkNotNull(team, "Team cannot be null");
        this.player = player;
        this.game = game;
        this.team = team;
        this.inventory = new BedwarsInventory();
        this.stats = new GameStatisticManager() {
            @Override
            public Map<GameStatistic, Integer> getStats() {
                return null;
            }

            @Override
            public int getStatistic(GameStatistic var1) {
                return 0;
            }

            @Override
            public void incrementStatistic(GameStatistic var1, int var2) {

            }

            @Override
            public void decrementStatistic(GameStatistic var1, int var2) {

            }

            @Override
            public void setStatistic(GameStatistic var1, int var2) {

            }

            @Override
            public GameReward getExpReward() {
                return null;
            }

            @Override
            public GameReward getCoinsReward() {
                return null;
            }
        };
    }

    public Player getPlayer() {
        return this.player;
    }

    public Game getGame() {
        return this.game;
    }

    public Team getTeam() {
        return this.team;
    }

    public ArmorType getArmorType() {
        return this.armor;
    }

    public void setArmorType(ArmorType type) {
        if (type != null) {
            this.armor = type;
        }

    }

    public GameInventory getInventory() {
        return this.inventory;
    }

    public void setInventory(GameInventory inv) {
        if (inv != null) {
            this.inventory = inv;
        }

    }

    public GameStatisticManager getStatisticManager() {
        return this.stats;
    }

    public void setStatistics(GameStatisticManager statistics) {
        if (statistics != null) {
            this.stats = statistics;
        }

    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.player, this.game});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BedwarsPlayer)) {
            return false;
        } else {
            BedwarsPlayer other = (BedwarsPlayer)obj;
            return this.game.equals(other.game) && this.player.equals(other.player);
        }
    }

    public String toString() {
        return "BedwarsPlayer [Player=" + this.player.getName() + "]";
    }

    public void initPlayer() {
        this.player = Bukkit.getPlayer(this.player.getUniqueId());
    }
}
