package me.jaskri.API.User;

import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
import org.bukkit.Statistic;

import java.util.EnumMap;
import java.util.Map;

public class UserStatistics implements Cloneable{

    private Map<Statistics, Integer> stats = new EnumMap(Statistic.class);

    public UserStatistics() {
    }

    public Map<Statistic, Integer> getStats() {
        return this.stats;
    }

    public int getStatistic(Statistic stat) {
        Integer result = (Integer)this.stats.get(stat);
        return result != null ? result : 0;
    }

    public float getKillDeathRatio() {
        Integer kills = (Integer)this.stats.get(Statistic.PLAYER_KILLS);
        if (kills == null) {
            return 0.0F;
        } else {
            Integer deaths = (Integer)this.stats.get(Statistic.DEATHS);
            return deaths != null && deaths != 0 ? (float)(kills / deaths) : 0.0F;
        }
    }

    public float getFinalKillDeathRatio() {
        Integer kills = (Integer)this.stats.get(Statistic.FINAL_KILLS);
        if (kills == null) {
            return 0.0F;
        } else {
            Integer deaths = (Integer)this.stats.get(Statistic.FINAL_DEATHS);
            return deaths != null && deaths != 0 ? (float)(kills / deaths) : 0.0F;
        }
    }

    public void setStatistic(Statistic stat, int value) {
        if (stat != null && value >= 0) {
            this.stats.put(stat, value);
        }

    }

    public void incrementStatistic(Statistic stat, int value) {
        if (stat != null && value > 0) {
            Integer old = (Integer)this.stats.get(stat);
            if (old == null) {
                this.stats.put(stat, value);
            } else {
                this.stats.put(stat, old + value);
            }

        }
    }

    public void decrementStatistic(Statistic stat, int value) {
        if (stat != null && value > 0) {
            Integer old = (Integer)this.stats.get(stat);
            if (old != null) {
                this.stats.put(stat, old - value >= 0 ? old - value : 0);
            }
        }
    }

    public void incrementStatistics(GameStatisticManager stats) {
        if (stats != null) {
            this.incrementStatistic(Statistic.PLAYER_KILLS, stats.getStatistic(GameStatistic.KILLS));
            this.incrementStatistic(Statistic.DEATHS, stats.getStatistic(GameStatistic.DEATHS));
            this.incrementStatistic(Statistic.FINAL_KILLS, stats.getStatistic(GameStatistic.FINAL_KILLS));
            this.incrementStatistic(Statistic.FINAL_DEATHS, stats.getStatistic(GameStatistic.FINAL_DEATHS));
            this.incrementStatistic(Statistic.BED_BROKEN, stats.getStatistic(GameStatistic.BED_BROKEN));
            this.incrementStatistic(Statistic.BED_LOSSES, stats.getStatistic(GameStatistic.BED_LOST));
        }
    }

    public void decrementStatistics(GameStatisticManager stats) {
        if (stats != null) {
            this.decrementStatistic(Statistic.PLAYER_KILLS, stats.getStatistic(GameStatistic.KILLS));
            this.decrementStatistic(Statistic.DEATHS, stats.getStatistic(GameStatistic.DEATHS));
            this.decrementStatistic(Statistic.FINAL_KILLS, stats.getStatistic(GameStatistic.FINAL_KILLS));
            this.decrementStatistic(Statistic.FINAL_DEATHS, stats.getStatistic(GameStatistic.FINAL_DEATHS));
            this.decrementStatistic(Statistic.BED_BROKEN, stats.getStatistic(GameStatistic.BED_BROKEN));
            this.decrementStatistic(Statistic.BED_LOSSES, stats.getStatistic(GameStatistic.BED_LOST));
        }
    }

    public UserStatistics clone() {
        try {
            return (UserStatistics)super.clone();
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
