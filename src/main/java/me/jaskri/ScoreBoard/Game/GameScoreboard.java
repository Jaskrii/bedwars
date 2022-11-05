package me.jaskri.ScoreBoard.Game;

import me.jaskri.API.ScoreBoard.ScoreBoard;
import me.jaskri.API.User.Statistics;
import me.jaskri.ScoreBoard.AbstractScoreboard;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Team.Team;
import me.jaskri.Util.ChatUtils;
import me.jaskri.Util.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.w3c.dom.Text;
import sun.invoke.empty.Empty;

import java.util.*;

public class GameScoreboard extends AbstractScoreboard {

    private static final Map<UUID, Scoreboard> BOARDS = new HashMap();
    private Map<Integer, GameBoardLineType> line_type = new HashMap(15);
    private Map<Integer, GameStatistic> stat_line = new HashMap(15);
    private Map<Integer, Team> team_line = new HashMap(15);
    private String phase;

    public GameScoreboard(ScoreBoard title) {
        super(title);
    }

    public void setText(int line, String text) {
        if (text != null && this.isValidLine(line)) {
            this.line_type.put(line, ChatUtils.format(text));
            this.line_type.put(line, GameScoreboard.GameBoardLineType.getByName(Text));
        }
    }

    public void setStatistic(int line, GameStatistic stat) {
        if (stat != null && this.isValidLine(line)) {
            this.stat_line.put(line, stat);
            this.line_type.put(line, GameScoreboard.GameBoardLineType.getByName(Statistics));
        }
    }

    public void setTeam(int line, Team team) {
        if (team != null && this.isValidLine(line)) {
            this.team_line.put(line, team);
            this.line_type.put(line, GameScoreboard.GameBoardLineType.getByName(Team));
        }
    }

    public void setPhaseText(String text) {
        this.phase = text;
    }

    public void setLineType(int line, GameBoardLineType type) {
        if (type != null && this.isValidLine(line)) {
            switch (type) {
                case Statistic:
                case Team:
                case Text:
                    return;
                default:
                    this.line_type.put(line, type);
            }
        }
    }

    private Scoreboard getBukkitBoard(Player player) {
        Scoreboard board = (Scoreboard)BOARDS.get(player.getUniqueId());
        if (board != null) {
            return board;
        } else {
            BOARDS.put(player.getUniqueId(), board = Bukkit.getScoreboardManager().getNewScoreboard());
            Objective obj = board.registerNewObjective("Scoreboard", "dummy");
            obj.setDisplayName(this.display);
            obj.setDisplaySlot(this.slot);
            int empty = 0;
            Iterator var5 = this.line_type.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<Integer, GameBoardLineType> entry = (Map.Entry)var5.next();
                int line = (Integer)entry.getKey();
                switch ((GameBoardLineType)entry.getValue()) {
                    case Statistics:
                        String stat = ((GameStatistic)this.stat_line.get(line)).toString();
                        if (board.getTeam(stat) == null) {
                            board.registerNewTeam(stat).addEntry(stat + ": " + ChatColor.GREEN);
                            obj.getScore(stat + ": " + ChatColor.GREEN).setScore(line);
                        }
                        break;
                    case Team:
                        String team = ((Team)this.team_line.get(line)).toString();
                        if (board.getTeam(team) == null) {
                            board.registerNewTeam(team).addEntry(" " + team + ": ");
                            obj.getScore(" " + team + ": ").setScore(line);
                        }
                        break;
                    case Text:
                        obj.getScore((String)this.line_type.get(line)).setScore(line);
                        break;
                    case Empty:
                        ++empty;
                        obj.getScore(ScoreboardUtils.getEmptyLine(empty)).setScore(line);
                        break;
                    case Date:
                        obj.getScore(ScoreboardUtils.formatDate()).setScore(line);
                        break;
                    case PHASE:
                        if (board.getTeam("GamePhase") == null) {
                            board.registerNewTeam("GamePhase").addEntry("");
                            obj.getScore("").setScore(line);
                        }
                }
            }

            return board;
        }
    }

    public void update(GamePlayer gp) {
        if (gp != null) {
            Scoreboard board = this.getBukkitBoard(gp.getPlayer());
            Iterator var3 = this.line_type.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<Integer, GameBoardLineType> entry = (Map.Entry)var3.next();
                int line = (Integer)entry.getKey();
                switch ((GameBoardLineType)entry.getValue()) {
                    case Statistics:
                        this.makeStatistic(board, gp, (GameStatistic)this.stat_line.get(line));
                        break;
                    case Team:
                        this.makeTeam(board, gp, (Team)this.team_line.get(line));
                        break;
                    case Phase:
                        this.makePhase(board, board.getObjective("Scoreboard"), line);
                }
            }

            gp.getPlayer().setScoreboard(board);
        }
    }

    private void makeStatistic(Scoreboard board, GamePlayer gp, GameStatistic stat) {
        org.bukkit.scoreboard.Team statistic = board.getTeam(stat.toString());
        statistic.setSuffix(Integer.toString(gp.getStatisticManager().getStatistic(stat)));
    }

    private void makePhase(Scoreboard board, Objective obj, int line) {
        if (this.phase != null) {
            org.bukkit.scoreboard.Team team = board.getTeam("GamePhase");
            Iterator var5 = team.getEntries().iterator();

            while(var5.hasNext()) {
                String entry = (String)var5.next();
                board.resetScores(entry);
            }

            team.addEntry(this.phase);
            obj.getScore(this.phase).setScore(line);
        }
    }

    private void makeTeam(Scoreboard board, GamePlayer gp, Team game_team) {
        org.bukkit.scoreboard.Team team = board.getTeam(game_team.toString());
        team.setPrefix(game_team.getColoredChar());
        team.setSuffix(ScoreboardUtils.getTeamState(gp.getGame(), gp, game_team));
    }

    private boolean isValidLine(int line) {
        return line >= 1 && line <= 15;
    }

    public static enum GameBoardLineType {
        STATISTIC,
        PHASE,
        EMPTY,
        DATE,
        TEXT,
        TEAM;

        private static final Map<String, GameBoardLineType> BY_NAME = new HashMap(6);

        private GameBoardLineType() {
        }

        public static GameBoardLineType getByName(String type) {
            return type != null ? (GameBoardLineType)BY_NAME.get(type.toLowerCase()) : null;
        }

        static {
            GameBoardLineType[] var0 = values();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                GameBoardLineType type = var0[var2];
                BY_NAME.put(type.name().toLowerCase(), type);
            }

        }
    }

    public static class GameBoardLineType {

        Statistic,
        EnderDragon.Phase,
        Empty,
        Date,
        Text,
        org.bukkit.scoreboard.Team;

        private static final Map<String, GameBoardLineType> BY_NAME = new HashMap(6);

        private GameBoardLineType() {
        }

        public static GameBoardLineType getByName(String type) {
            return type != null ? (GameBoardLineType)BY_NAME.get(type.toLowerCase()) : null;
        }

        static {
            GameBoardLineType[] var0 = Value();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                GameBoardLineType type = var0[var2];
                BY_NAME.put(type.getByName().toLowerCase(), type);
            }

        }
    }
    }
}
