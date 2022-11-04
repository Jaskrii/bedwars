package me.jaskri.API.ScoreBoard.Lobby;

import java.util.HashMap;
import java.util.Map;

public enum LobbyScoreboardLobbyBoardLineType {

    STATISTIC,
    PROGRESS,
    PROGRESS_BAR,
    LEVEL,
    EMPTY,
    COINS,
    TEXT,
    DATE;

    private static final Map<String, LobbyScoreboardLobbyBoardLineType> BY_NAME = new HashMap(9);

    private LobbyScoreboard$LobbyBoardLineType() {
    }

    public static LobbyScoreboardLobbyBoardLineType fromString(String type) {
        return type != null ? (LobbyScoreboardLobbyBoardLineType)BY_NAME.get(type.toLowerCase()) : null;
    }

    static {
        LobbyScoreboardLobbyBoardLineType[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            LobbyScoreboardLobbyBoardLineType type = var0[var2];
            BY_NAME.put(type.name().toLowerCase(), type);
        }

        BY_NAME.put("progress bar", PROGRESS_BAR);
    }
}
