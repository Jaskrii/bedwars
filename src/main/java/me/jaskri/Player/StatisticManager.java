package me.jaskri.Player;

import me.jaskri.API.Game.GameReward;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
import me.jaskri.Reward.CoinReward;
import me.jaskri.Reward.ExpReward;

import java.util.HashMap;
import java.util.Map;

public class StatisticManager implements GameStatisticManager {

    private Map<GameStatistic, Integer> stats = new HashMap();
    private GameReward expReward = new ExpReward();
    private GameReward coinReward = new CoinReward();

    public StatisticManager() {
    }

    public Map<GameStatistic, Integer> getStats() {
        return this.stats;
    }

    public int getStatistic(GameStatistic stat) {
        if (stat == null) {
            return 0;
        } else {
            Integer old = (Integer)this.stats.get(stat);
            return old != null ? old : 0;
        }
    }

    public void incrementStatistic(GameStatistic stat, int value) {
        if (stat != null && value > 0) {
            Integer old = (Integer)this.stats.get(stat);
            if (old == null) {
                this.stats.put(stat, value);
            } else {
                this.stats.put(stat, old + value);
            }

        }
    }

    public void decrementStatistic(GameStatistic stat, int value) {
        if (stat != null && value > 0) {
            Integer old = (Integer)this.stats.get(stat);
            if (old != null) {
                this.stats.put(stat, old - value > 0 ? old - value : 0);
            }
        }
    }

    public void setStatistic(GameStatistic stat, int value) {
        if (stat != null && value >= 0) {
            this.stats.put(stat, value);
        }

    }

    public GameReward getExpReward() {
        return this.expReward;
    }

    public GameReward getCoinsReward() {
        return this.coinReward;
    }
}
