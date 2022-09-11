package me.jaskri.bedwars.API.PACKAGE.ScoreBoard.Lobby;

import me.jaskri.bedwars.API.PACKAGE.ScoreBoard.ScoreBoard;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public interface LobbyScoreBoard extends ScoreBoard {

    Map<Integer, Statistic> getStatistics();

    Statistic getStatistic(int var1);

    void setStatistic(int var1, Statistic var2);

    Statistic removeStatistic(int var1);

    LobbyBoardLineType getLineType(int var1);

    void setLineType(int var1, LobbyBoardLineType var2);

    void update(Player var1);

    public static enum LobbyBoardLineType {
        STATISTIC,
        PROGRESS,
        PROGRESS_BAR,
        LEVEL,
        EMPTY,
        COINS,
        TEXT,
        DATE;

        private static final Map<String, LobbyBoardLineType> BY_NAME = new HashMap(9);

        private LobbyLine() {
        }

        public static LobbyBoardLineType fromString(String type) {
            return type != null ? (LobbyBoardLineType)BY_NAME.get(type.toLowerCase()) : null;
        }

        static {
            LobbyBoardLineType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                LobbyBoardLineType type = var0[var2];
                BY_NAME.put(type.name().toLowerCase(), type);
            }

            BY_NAME.put("progress bar", PROGRESS_BAR);
        }
    }
}
