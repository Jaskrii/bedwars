package me.jaskri.API.Game.player.Stats;

import java.util.Map;

public interface GameStatisticManager {

    Map<GameStatistic, Integer> getStats();

    int getStatistic(GameStatistic var1);

    void incrementStatistic(GameStatistic var1, int var2);

    void decrementStatistic(GameStatistic var1, int var2);

    void setStatistic(GameStatistic var1, int var2);

    GameReward getExpReward();

    GameReward getCoinsReward();
}
