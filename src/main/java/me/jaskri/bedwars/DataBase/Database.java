package me.jaskri.bedwars.DataBase;

import com.google.common.base.Preconditions;
import me.jaskri.bedwars.API.PACKAGE.Game.GameMode;
import me.jaskri.bedwars.API.PACKAGE.User.Statistics;
import me.jaskri.bedwars.API.PACKAGE.User.User;
import me.jaskri.bedwars.API.PACKAGE.User.UserStatistics;

import java.sql.*;
import java.util.UUID;

public class Database {

    private final String username;
    private final String password;
    private final String url;
    private Connection connection;

    public Database(String username, String password, String url) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkNotNull(password, "Password cannot be null");
        Preconditions.checkNotNull(url, "Database url cannot be null");
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public boolean connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            return true;
        } catch (SQLException var2) {
            return false;
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public boolean disconnect() {
        if (!this.isConnected()) {
            return false;
        } else {
            try {
                this.connection.close();
                return true;
            } catch (SQLException var2) {
                return false;
            }
        }
    }

    public void createCoinsTable() {
        if (this.isConnected()) {
            try {
                StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS Coins ");
                builder.append("(UUID VARCHAR(255), Name VARCHAR(32), Coins INT)");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.executeUpdate();
            } catch (Exception var3) {
            }

        }
    }

    public void addCoinsUser(User user) {
        if (this.isConnected() && user != null) {
            try {
                StringBuilder builder = new StringBuilder("INSERT IGNORE INTO Coins (UUID,Name,Coins) VALUES (?,?,?)");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setString(1, user.getPlayer().getUniqueId().toString());
                statement.setString(2, user.getPlayer().getName());
                statement.setInt(3, user.getCoinsBalance());
                statement.executeUpdate();
            } catch (Exception var4) {
            }

        }
    }

    public void setUserCoins(User user) {
        if (this.isConnected() && user != null) {
            try {
                StringBuilder builder = new StringBuilder("UPDATE Coins SET Coins=? WHERE UUID=?");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setInt(1, user.getCoinsBalance());
                statement.setString(2, user.getPlayer().getUniqueId().toString());
                statement.executeUpdate();
            } catch (Exception var4) {
            }

        }
    }

    public boolean containsCoinsUser(User user) {
        if (this.isConnected() && user != null) {
            try {
                StringBuilder builder = new StringBuilder("SELECT * FROM Coins WHERE UUID=?");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setString(1, user.getPlayer().getUniqueId().toString());
                return statement.executeQuery().next();
            } catch (Exception var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    public void createStatsTable(GameMode mode) {
        if (this.isConnected() && mode != null) {
            try {
                StringBuilder builder = (new StringBuilder("CREATE TABLE IF NOT EXISTS ")).append(mode.getName()).append("_Stats ");
                builder.append("(UUID VARCHAR(255),");
                builder.append("Kills INT,");
                builder.append("Deaths INT,");
                builder.append("KillDeathRatio DOUBLE(64, 2),");
                builder.append("Final_Kills INT,");
                builder.append("Final_Deaths INT,");
                builder.append("FinalKillDeathRatio DOUBLE(64, 2),");
                builder.append("Wins INT,");
                builder.append("Losses INT,");
                builder.append("PRIMARY KEY (UUID))");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.executeUpdate();
            } catch (Exception var4) {
            }

        }
    }

    public UserStatistics getUserStats(GameMode mode, UUID uuid) {
        if (this.isConnected() && mode != null && uuid != null) {
            try {
                if (!this.containsStatsUser(mode, uuid)) {
                    return null;
                } else {
                    StringBuilder builder = new StringBuilder("SELECT * FROM " + mode.getName() + "_Stats WHERE UUID=?");
                    PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                    ResultSet result = statement.executeQuery();
                    UserStatistics stats = new UserStatistics();
                    stats.setStatistic(Statistics.KILLS, result.getInt("Kills"));
                    stats.setStatistic(Statistics.DEATHS, result.getInt("Deaths"));
                    stats.setStatistic(Statistics.FINAL_KILLS, result.getInt("Final_Kills"));
                    stats.setStatistic(Statistics.FINAL_DEATHS, result.getInt("Final_Deaths"));
                    stats.setStatistic(Statistics.WINS, result.getInt("Wins"));
                    stats.setStatistic(Statistics.LOSSES, result.getInt("Losses"));
                    return stats;
                }
            } catch (Exception var7) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void createStatsUser(GameMode mode, User user) {
        if (this.isConnected() && mode != null && user != null) {
            try {
                StringBuilder builder = new StringBuilder("INSERT IGNORE INTO " + mode.getName() + "_Stats ");
                builder.append("(UUID,Kills, Deaths, KillDeathRatio, Final_Kills, Final_Deaths, FinalKillDeathRatio, Wins, Losses) ");
                builder.append("VALUES (?,?,?,?,?,?,?,?,?)");
                UserStatistics stats = user.getStatistics(mode);
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setString(1, user.getPlayer().getUniqueId().toString());
                statement.setInt(2, stats.getStatistic(Statistics.KILLS));
                statement.setInt(3, stats.getStatistic(Statistics.DEATHS));
                statement.setFloat(4, stats.getKillDeathRatio());
                statement.setInt(5, stats.getStatistic(Statistics.FINAL_KILLS));
                statement.setInt(6, stats.getStatistic(Statistics.FINAL_DEATHS));
                statement.setFloat(7, stats.getFinalKillDeathRatio());
                statement.setInt(8, stats.getStatistic(Statistics.WINS));
                statement.setInt(9, stats.getStatistic(Statistics.LOSSES));
                statement.executeUpdate();
            } catch (Exception var6) {
            }

        }
    }

    public void setUserStats(GameMode mode, User user) {
        if (this.isConnected() && mode != null && user != null) {
            try {
                if (!this.containsStatsUser(mode, user.getPlayer().getUniqueId())) {
                    this.createStatsUser(mode, user);
                    return;
                }

                StringBuilder builder = new StringBuilder("UPDATE " + mode.getName() + "_Stats SET ");
                builder.append("Kills INT=?,");
                builder.append("Deaths=?,");
                builder.append("KillDeathRatio=?,");
                builder.append("Final_Kills=?,");
                builder.append("Final_Deaths=?,");
                builder.append("FinalKillDeathRatio=?,");
                builder.append("Wins=?,");
                builder.append("Losses=? ");
                builder.append("WHERE UUID=?");
                UserStatistics stats = user.getStatistics(mode);
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setInt(1, stats.getStatistic(Statistics.KILLS));
                statement.setInt(2, stats.getStatistic(Statistics.DEATHS));
                statement.setFloat(3, stats.getKillDeathRatio());
                statement.setInt(4, stats.getStatistic(Statistics.FINAL_KILLS));
                statement.setInt(5, stats.getStatistic(Statistics.FINAL_DEATHS));
                statement.setFloat(6, stats.getFinalKillDeathRatio());
                statement.setInt(7, stats.getStatistic(Statistics.WINS));
                statement.setInt(8, stats.getStatistic(Statistics.LOSSES));
                statement.setString(9, user.getPlayer().getUniqueId().toString());
                statement.executeUpdate();
            } catch (Exception var6) {
            }

        }
    }

    public boolean containsStatsUser(GameMode mode, UUID uuid) {
        if (this.isConnected() && mode != null && uuid != null) {
            try {
                StringBuilder builder = new StringBuilder("SELECT * FROM " + mode.getName() + "_Stats WHERE UUID=?");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setString(1, uuid.toString());
                return statement.executeQuery().next();
            } catch (SQLException var5) {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getUserStats(GameMode mode, String stat, UUID uuid) {
        if (this.isConnected() && mode != null && stat != null && uuid != null) {
            try {
                StringBuilder builder = (new StringBuilder("SELECT " + stat + "FROM ")).append(mode.getName()).append("Stats ");
                builder.append("WHERE UUID=?");
                PreparedStatement statement = this.connection.prepareStatement(builder.toString());
                statement.setString(1, uuid.toString());
                return statement.executeQuery().getInt(1);
            } catch (Exception var6) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
