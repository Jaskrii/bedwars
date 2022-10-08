package me.jaskri.ScoreBoard;

import me.jaskri.Configuration.Configuration;
import me.jaskri.ScoreBoard.Game.GameScoreboard;
import me.jaskri.ScoreBoard.Lobby.BedwarsLobbyScoreboard;
import me.jaskri.ScoreBoard.Waiting.WaitingScoreboard;
import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.ScoreBoard.ScoreBoard;
import me.jaskri.API.Team.Team;
import me.jaskri.API.User.Statistics;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.NumberConversions;

import java.io.File;
import java.util.*;

public class ScoreboardConfig extends Configuration {

    private static final ScoreBoard.AnimatedTitle DEFAULT_TITLE;
    private Map<GameMode, GameScoreboard> boards = new HashMap();
    private WaitingScoreboard waitingBoard;
    private LobbyScoreBoard lobbyBoard;
    private static ScoreboardConfig instance;

    private static String bold(ChatColor color) {
        return color + "" + ChatColor.BOLD;
    }

    private ScoreboardConfig() {
        super(new File(Bedwars.getInstance().getDataFolder(), "Scoreboard.yml"));
        this.saveDefaultConfig();
    }

    public GameScoreboard getScoreboard(GameMode mode) {
        if (mode == null) {
            return null;
        } else {
            GameScoreboard existing = (GameScoreboard)this.boards.get(mode);
            if (existing != null) {
                return existing;
            } else {
                ConfigurationSection section = this.getConfig().getConfigurationSection("Game-Scoreboards");
                if (section == null) {
                    return null;
                } else {
                    Iterator var4 = section.getKeys(false).iterator();

                    String key;
                    do {
                        if (!var4.hasNext()) {
                            return null;
                        }

                        key = (String)var4.next();
                    } while(!key.equalsIgnoreCase(mode.getName()));

                    ConfigurationSection lineSection = section.getConfigurationSection(key + ".lines");
                    if (lineSection == null) {
                        return null;
                    } else {
                        ScoreBoard.AnimatedTitle title = this.getTitle("Game-Scoreboards." + key + ".Title", DEFAULT_TITLE);
                        GameScoreboard board = new GameScoreboard(title);
                        Iterator var9 = this.config.getConfigurationSection("Game-Scoreboards." + key + ".lines").getKeys(false).iterator();

                        while(var9.hasNext()) {
                            String lineKey = (String)var9.next();
                            int score = NumberConversions.toInt(lineKey.replace("line-", ""));
                            if (score >= 1 && score <= 15) {
                                GameScoreboard.GameBoardLineType lineType = GameScoreboard.GameBoardLineType.getByName(lineSection.getString(lineKey + ".type"));
                                if (lineType != null) {
                                    String value = lineSection.getString(lineKey + ".value");
                                    switch (lineType) {
                                        case TEXT:
                                            board.setText(score, value);
                                            break;
                                        case TEAM:
                                            board.setTeam(score, Team.getByName(value));
                                            break;
                                        case STATICS:
                                            board.setStatistic(score, GameStatistic.fromString(value));
                                    }

                                    board.setLineType(score, lineType);
                                }
                            }
                        }

                        this.boards.put(mode, board);
                        return board;
                    }
                }
            }
        }
    }

    public ScoreBoard.AnimatedTitle getTitle(String path) {
        return this.getTitle(path, (ScoreBoard.AnimatedTitle)null);
    }

    public ScoreBoard.AnimatedTitle getTitle(String path, ScoreBoard.AnimatedTitle def) {
        List<String> titles = this.getConfig().getStringList(path + ".titles");
        return !titles.isEmpty() ? new ScoreBoard(titles, this.config.getLong(path + ".update-ticks")) : def;
    }

    public WaitingScoreboard getWaitingScoreboard() {
        if (this.waitingBoard != null) {
            return this.waitingBoard;
        } else {
            ConfigurationSection section = this.getConfig().getConfigurationSection("Waiting-Scoreboard.lines");
            if (section == null) {
                return null;
            } else {
                ScoreBoard.AnimatedTitle title = this.getTitle("Waiting-Scoreboard.Title", DEFAULT_TITLE);
                this.waitingBoard = new WaitingScoreboard(title);
                Iterator var3 = section.getKeys(false).iterator();

                while(var3.hasNext()) {
                    String lineKey = (String)var3.next();
                    int score = NumberConversions.toInt(lineKey.replace("line-", ""));
                    if (score > 0 && score <= 15) {
                        WaitingScoreboard.WaitingBoardLineType type = WaitingScoreboard.WaitingBoardLineType.getByName(section.getString(lineKey + ".type"));
                        if (type != null) {
                            String value = section.getString(lineKey + ".value");
                            if (value != null && type == WaitingScoreboard.WaitingBoardLineType.TEXT) {
                                this.waitingBoard.setText(score, value);
                            }

                            this.waitingBoard.setLineType(score, type);
                        }
                    }
                }

                return this.waitingBoard;
            }
        }
    }

    public LobbyScoreBoard getLobbyScoreboard() {
        if (this.lobbyBoard != null) {
            return this.lobbyBoard;
        } else {
            ConfigurationSection section = this.getConfig().getConfigurationSection("Lobby-Scoreboard.lines");
            if (section == null) {
                return null;
            } else {
                ScoreBoard.AnimatedTitle title = this.getTitle("Lobby-Scoreboard.Title", DEFAULT_TITLE);
                this.lobbyBoard = new BedwarsLobbyScoreboard(title);
                Iterator var3 = section.getKeys(false).iterator();

                while(var3.hasNext()) {
                    String lineKey = (String)var3.next();
                    int score = NumberConversions.toInt(lineKey.replace("line-", ""));
                    if (score >= 1 && score <= 15) {
                        LobbyScoreBoard.LobbyBoardLineType type = LobbyScoreBoard.LobbyBoardLineType.fromString(section.getString(lineKey + ".type"));
                        if (type != null) {
                            String value = section.getString(lineKey + ".value");
                            if (value != null) {
                                if (type == LobbyScoreBoard.LobbyBoardLineType.STATISTIC) {
                                    this.lobbyBoard.setStatistic(score, Statistics.getByName(value));
                                } else if (type == LobbyScoreBoard.LobbyBoardLineType.TEXT) {
                                    this.lobbyBoard.setText(score, value);
                                }
                            }

                            this.lobbyBoard.setLineType(score, type);
                        }
                    }
                }

                return this.lobbyBoard;
            }
        }
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            Bedwars.getInstance().saveResource("Scoreboard.yml", false);
        }

    }

    public static ScoreboardConfig getInstance() {
        if (instance == null) {
            instance = new ScoreboardConfig();
        }

        return instance;
    }

    static {
        List<String> titles = Arrays.asList(bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.WHITE) + "" + bold(ChatColor.GOLD) + "B" + bold(ChatColor.YELLOW) + "ED WARS", bold(ChatColor.WHITE) + "B" + bold(ChatColor.GOLD) + "E" + bold(ChatColor.YELLOW) + "D WARS", bold(ChatColor.WHITE) + "BE" + bold(ChatColor.GOLD) + "D" + bold(ChatColor.YELLOW) + " WARS", bold(ChatColor.WHITE) + "BED " + bold(ChatColor.GOLD) + "W" + bold(ChatColor.YELLOW) + "ARS", bold(ChatColor.WHITE) + "BED W" + bold(ChatColor.GOLD) + "A" + bold(ChatColor.YELLOW) + "RS", bold(ChatColor.WHITE) + "BED WA" + bold(ChatColor.GOLD) + "R" + bold(ChatColor.YELLOW) + "S", bold(ChatColor.WHITE) + "BED WAR" + bold(ChatColor.GOLD) + "S" + bold(ChatColor.YELLOW) + "", bold(ChatColor.WHITE) + "BED WARS", bold(ChatColor.WHITE) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.WHITE) + "BED WARS", bold(ChatColor.WHITE) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS", bold(ChatColor.YELLOW) + "BED WARS");
        DEFAULT_TITLE = new ScoreBoard(titles, 5L);
    }
}
