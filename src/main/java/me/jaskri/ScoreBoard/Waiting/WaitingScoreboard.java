package me.jaskri.ScoreBoard.Waiting;

import me.jaskri.ScoreBoard.AbstractScoreboard;
import me.jaskri.API.Game.Game;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WaitingScoreboard extends AbstractScoreboard {

    private static final Map<Game, Scoreboard> GAME_BOARD = new ConcurrentHashMap();
    private static final Map<Game, String> COUNTDOWN_TEXT = new ConcurrentHashMap();
    private Map<Integer, WaitingBoardLineType> line_types = new HashMap();

    public WaitingScoreboard(me.jaskri.API.ScoreBoard.AnimatedTitle title) {
        super(title);
    }

    public void setText(int line, String text) {
        if (text != null && this.isValidLine(line)) {
            this.getLineType().put(line, ChatUtils.format(text));
            this.line_types.put(line, WaitingScoreboard.WaitingBoardLineType.TEXT);
        }
    }

    public WaitingBoardLineType getLineType(int line) {
        return (WaitingBoardLineType)this.line_types.get(line);
    }

    public void setLineType(int line, WaitingBoardLineType type) {
        if (type != null && type != WaitingScoreboard.WaitingBoardLineType.TEXT && this.isValidLine(line)) {
            WaitingBoardLineType old = (WaitingBoardLineType)this.line_types.get(line);
            if (old == null) {
                this.line_types.put(line, type);
            }
        }
    }

    public String getCountdownText(Game game) {
        return (String)COUNTDOWN_TEXT.get(game);
    }

    public void setCountdownText(Game game, String text) {
        COUNTDOWN_TEXT.put(game, ChatUtils.format(text));
    }

    public Scoreboard getBukkitBoard(Game game) {
        Scoreboard board = (Scoreboard)GAME_BOARD.get(game);
        if (board != null) {
            return board;
        } else {
            GAME_BOARD.put(game, board = Bukkit.getScoreboardManager().getNewScoreboard());
            Objective obj = board.registerNewObjective("Scoreboard", "dummy");
            obj.setDisplayName(this.display);
            obj.setDisplaySlot(this.slot);
            int empty = 0;
            Iterator var5 = this.line_types.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<Integer, WaitingBoardLineType> entry = (Map.Entry)var5.next();
                int line = (Integer)entry.getKey();
                switch ((WaitingBoardLineType)entry.getValue()) {
                    case EMPTY:
                        ++empty;
                        obj.getScore(ScoreboardUtils.getEmptyLine(empty)).setScore(line);
                        break;
                    case DATE:
                        obj.getScore(ScoreboardUtils.formatDate()).setScore(line);
                        break;
                    case TEXT:
                        obj.getScore((String)this.setLineType().get(line)).setScore(line);
                        break;
                    case MODE:
                        obj.getScore("Mode: " + ChatColor.GREEN + game.getArena().getMode().getDeclaringClass().getName()).setScore(line);
                        break;
                    case VERSION:
                        obj.getScore("Version: " + ChatColor.GREEN + this.getVersion()).setScore(line);
                        break;
                    case MAP:
                        String name = game.getArena().getMapName();
                        if (name != null) {
                            obj.getScore("Map: " + ChatColor.GREEN + name).setScore(line);
                        }
                        break;
                    case PLAYERS:
                        if (board.getTeam("Players") == null) {
                            Team players = board.registerNewTeam("Players");
                            players.addEntry("Players: ");
                            obj.getScore("Players: ").setScore(line);
                        }
                        break;
                    case COUNTDOWN:
                        if (board.getTeam("Countdown") == null) {
                            Team countdown = board.registerNewTeam("Countdown");
                            countdown.addEntry("");
                            obj.getScore("").setScore(line);
                        }
                }
            }

            return board;
        }
    }

    private String getVersion() {
        return "v" + Bedwars.getInstance().getDescription().getVersion();
    }

    public void update(Game game, Set<Player> players) {
        if (game != null && players != null) {
            Scoreboard board = this.getBukkitBoard(game);
            Team players_counter = board.getTeam("Players");
            players_counter.setSuffix(ChatColor.GREEN + "" + players.size() + "/" + game.getMode().getGameMax());
            String text = this.getCountdownText(game);
            if (text == null) {
                text = "Waiting...";
            }

            Team countdown = board.getTeam("Countdown");
            int length = text.length();
            if (length <= 16) {
                countdown.setPrefix(text);
            } else {
                countdown.setPrefix(text.substring(0, 16));
                countdown.setSuffix(text.substring(16, length > 32 ? 32 : length));
            }

            Iterator var8 = players.iterator();

            while(var8.hasNext()) {
                Player player = (Player)var8.next();
                player.setScoreboard(board);
            }

        }
    }

    private boolean isValidLine(int line) {
        return line >= 1 && line <= 15;
    }

    public static enum WaitingBoardLineType {
        COUNTDOWN,
        PLAYERS,
        VERSION,
        EMPTY,
        MODE,
        DATE,
        TEXT,
        MAP;

        private static final Map<String, WaitingBoardLineType> BY_NAME = new HashMap();

        private WaitingBoardLineType() {
        }

        public static WaitingBoardLineType getByName(String name) {
            return name != null ? (WaitingBoardLineType)BY_NAME.get(name.toLowerCase()) : null;
        }

        static {
            WaitingBoardLineType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                WaitingBoardLineType type = var0[var2];
                BY_NAME.put(type.name().toLowerCase(), type);
            }

        }
    }

    public static enum WaitingBoardLineType {

        COUNTDOWN,
        PLAYERS,
        VERSION,
        EMPTY,
        MODE,
        DATE,
        TEXT,
        MAP;

        private static final Map<String, WaitingBoardLineType> BY_NAME = new HashMap();

        private WaitingBoardLineType() {
        }

        public static WaitingBoardLineType getByName(String name) {
            return name != null ? (WaitingBoardLineType)BY_NAME.get(name.toLowerCase()) : null;
        }

        static {
            WaitingBoardLineType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                WaitingBoardLineType type = var0[var2];
                BY_NAME.put(type.name().toLowerCase(), type);
            }

        }
    }
    }
}
