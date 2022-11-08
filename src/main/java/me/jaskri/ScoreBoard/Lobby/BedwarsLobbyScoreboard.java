package me.jaskri.ScoreBoard.Lobby;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.ScoreBoard.ScoreboardAnimatedTitle;
import me.jaskri.ScoreBoard.AbstractScoreboard;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.User.User;
import me.jaskri.API.User.UserStatistics;
import me.jaskri.Util.ChatUtils;
import me.jaskri.Util.ScoreboardUtils;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BedwarsLobbyScoreboard extends AbstractScoreboard implements LobbyScoreBoard {

    private static final Map<UUID, Scoreboard> PLAYERS_BOARDS = new ConcurrentHashMap();
    private Map<Integer, LobbyScoreBoard.LobbyBoardLineType> line_type = new HashMap();
    private Map<Integer, Statistic> stats_line = new HashMap();

    public BedwarsLobbyScoreboard(ScoreboardAnimatedTitle title) {
        super(title);
    }

    public void setText(int line, String text) {
        if (text != null && this.isValidLine(line)) {
            this.getLines().put(line, ChatUtils.format(text));
            this.line_type.put(line, LobbyBoardLineType.TEXT);
        }
    }

    public Map<Integer, Statistic> getStatistics() {
        return new HashMap(this.stats_line);
    }

    public Statistic getStatistic(int line) {
        return (Statistic)this.stats_line.get(line);
    }

    public void setStatistic(int line, Statistic stat) {
        if (stat != null && this.isValidLine(line)) {
            this.stats_line.put(line, stat);
            this.line_type.put(line, LobbyBoardLineType.STATISTIC);
        }
    }

    public Statistic removeStatistic(int line) {
        return (Statistic)this.stats_line.remove(line);
    }

    public LobbyScoreBoard.LobbyBoardLineType getLineType(int line) {
        return (LobbyScoreBoard.LobbyBoardLineType)this.line_type.get(line);
    }

    public void setLineType(int line, LobbyScoreBoard.LobbyBoardLineType type) {
        if (type != null && this.isValidLine(line) && type != LobbyBoardLineType.STATISTIC && type != LobbyBoardLineType.TEXT) {
            this.line_type.put(line, type);
        }
    }

    private Scoreboard getBukkitBoard(Player player) {
        Scoreboard board = (Scoreboard)PLAYERS_BOARDS.get(player.getUniqueId());
        if (board != null) {
            return board;
        } else {
            PLAYERS_BOARDS.put(player.getUniqueId(), board = Bukkit.getScoreboardManager().getNewScoreboard());
            Objective obj = board.registerNewObjective("Scoreboard", "dummy");
            obj.setDisplayName(this.getDisplayTitle());
            obj.setDisplaySlot(this.getDisplaySlot());
            int empty = 0;
            Iterator var5 = this.line_type.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<Integer, LobbyScoreBoard.LobbyBoardLineType> entry = (Map.Entry)var5.next();
                int line = (Integer)entry.getKey();
                switch ((LobbyScoreBoard.LobbyBoardLineType)entry.getValue()) {
                    case TEXT:
                        obj.getScore((String)this.getLines().get(line)).setScore(line);
                        break;
                    case EMPTY:
                        obj.getScore(ScoreboardUtils.getEmptyLine(empty++)).setScore(line);
                        break;
                    case COINS:
                        if (board.getTeam("Coins") == null) {
                            board.registerNewTeam("Coins").addEntry("Coins: " + ChatColor.GOLD);
                            obj.getScore("Coins: " + ChatColor.GOLD).setScore(line);
                        }
                        break;
                    case LEVEL:
                        board.registerNewTeam("Level").addEntry("Your Level: ");
                        obj.getScore("Your Level: ").setScore(line);
                        break;
                    case PROGRESS:
                        if (board.getTeam("Progress") == null) {
                            Team progress = board.registerNewTeam("Progress");
                            progress.addEntry(ChatColor.AQUA.toString());
                            obj.getScore(ChatColor.AQUA.toString()).setScore(line);
                        }
                        break;
                    case PROGRESS_BAR:
                        if (board.getTeam("Progress-Bar") == null) {
                            Team progress_bar = board.registerNewTeam("Progress-Bar");
                            progress_bar.addEntry("");
                            obj.getScore("").setScore(line);
                        }
                        break;
                    case STATISTIC:
                        String name = ((Statistic)this.stats_line.get(line)).toString();
                        if (board.getTeam(name) == null) {
                            Team stat = board.registerNewTeam(name);
                            stat.addEntry("Total " + name + ": ");
                            obj.getScore("Total " + name + ": ").setScore(line);
                        }
                }
            }

            return board;
        }
    }

    public void update(Player player) {
        if (player != null) {
            Scoreboard board = this.getBukkitBoard(player);
            User user = Bedwars.getInstance().getUser(player);
            Iterator var4 = this.line_type.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<Integer, LobbyScoreBoard.LobbyBoardLineType> entry = (Map.Entry)var4.next();
                int line = (Integer)entry.getKey();
                switch ((LobbyScoreBoard.LobbyBoardLineType)entry.getValue()) {
                    case COINS:
                        this.makeCoins(board, user.getCoinsBalance());
                        break;
                    case LEVEL:
                        this.makeLevel(board, user.getLevel(), user.getPrestige());
                        break;
                    case PROGRESS:
                        this.makeProgress(board, board.getObjective("Scoreboard"), line, user.getLevel());
                        break;
                    case PROGRESS_BAR:
                        this.makeProgressBar(board, board.getObjective("Scoreboard"), line, user.getLevel());
                        break;
                    case STATISTIC:
                        this.makeStat(board, (Statistic)this.stats_line.get(line), user.getOverallStatistics());
                }
            }

            player.setScoreboard(board);
        }
    }

    private void makeLevel(Scoreboard board, BedwarsLevel level, Prestige prestige) {
        Team team = board.getTeam("Level");
        if (level == null) {
            level = new BedwarsLevel(1, 0, Bedwars.getInstance().getSettings().getLevelUpExpFor(1));
        }

        if (Prestige == null) {
            prestige = Prestige.DEFAULT;
        }

        team.setSuffix(prestige.formatToScoreboard(level));
    }

    private void makeProgress(Scoreboard board, Objective obj, int line, BedwarsLevel level) {
        Team team = board.getTeam("Progress");
        if (level != null) {
            StringBuilder builder = (new StringBuilder()).append("Progress: ").append(ChatColor.AQUA).append(level.getProgressExp()).append(ChatColor.GRAY).append("/").append(ChatColor.GREEN).append(level.getExpToLevelUp());
            String text = builder.toString();
            team.setPrefix(text.substring(0, 16));
            if (text.length() > 32) {
                team.setSuffix(text.substring(16, 32));
            } else {
                team.setSuffix(text.substring(16));
            }

        }
    }

    private void makeProgressBar(Scoreboard board, Objective obj, int line, BedwarsLevel level) {
        Team team = board.getTeam("Progress-Bar");
        String text = level.getProgressBar(10);
        String prefix = text.substring(0, 16);
        team.setPrefix(prefix);
        String suffix = this.getLastColor(prefix) + text.substring(16, 20);
        team.setSuffix(suffix);
    }

    private ChatColor getLastColor(String text) {
        for(int i = text.length() - 1; i >= 1; --i) {
            char before = text.charAt(i - 1);
            if (before == 167) {
                char current = text.charAt(i);
                ChatColor color = ChatColor.getByChar(current);
                if (color != null) {
                    return color;
                }
            }
        }

        return ChatColor.RESET;
    }

    private void makeStat(Scoreboard board, Statistic stat, UserStatistics stats) {
        Team team = board.getTeam(stat.toString());
        team.setSuffix(ChatColor.GREEN + Integer.toString(stats.getStatistic(stat)));
    }

    private void makeCoins(Scoreboard board, int amount) {
        Team team = board.getTeam("Coins");
        team.setSuffix(ChatColor.GOLD + Integer.toString(amount));
    }

    private boolean isValidLine(int line) {
        return line >= 1 && line <= 15;
    }
}
