package me.jaskri.User;

import com.google.common.base.Preconditions;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.ScoreBoard.Lobby.LobbyScoreBoard;
import me.jaskri.API.Shop.QuickBuy;
import me.jaskri.API.User.User;
import me.jaskri.API.User.UserStatistics;
import me.jaskri.DataBase.Database;
import me.jaskri.Game.AbstractGame;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BedwarsUser implements User {

    private Player player;
    private UserData data;
    private BedwarsLevel level;
    private Prestige prestige;
    private LobbyScoreBoard board;

    public BedwarsUser(Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null");
        this.player = player;
        this.data = new UserData(player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Game getGame() {
        return AbstractGame.getPlayerGame(this.player);
    }

    public BedwarsLevel getLevel() {
        return this.data.getLevel();
    }

    public void setLevel(BedwarsLevel level) {
        this.data.setLevel(level);
    }

    public BedwarsLevel getDisplayLevel() {
        return this.level != null ? this.level.clone() : null;
    }

    public void setDisplayLevel(BedwarsLevel level) {
        if (level != null) {
            this.level = level.clone();
        }

    }

    public Prestige getPrestige() {
        return this.data.getPrestige();
    }

    public void setPrestige(Prestige prestige) {
        this.data.setPrestige(prestige);
    }

    public Prestige getDisplayPrestige() {
        return this.prestige;
    }

    public void setDisplayPrestige(Prestige prestige) {
        if (prestige != null) {
            this.prestige = prestige;
        }

    }

    public UserStatistics getStatistics(GameMode mode) {
        return this.data.getStats(mode);
    }

    public UserStatistics getOverallStatistics() {
        return this.data.getOverallStats();
    }

    public int getCoinsBalance() {
        return this.data.getBalance();
    }

    public void setCoinsBalance(int balance) {
        this.data.setBalance(balance);
    }

    public LobbyScoreBoard getScoreboard() {
        return this.board;
    }

    public QuickBuy getQuickBuy(GameMode mode) {
        return this.data.getQuickBuy(mode);
    }

    public void setScoreboard(LobbyScoreBoard board) {
        this.board = board;
    }

    public void updateScoreboard() {
        if (this.board != null) {
            Bukkit.getScheduler().runTask(Bedwars.getInstance(), () -> {
                this.board.update(this.player);
            });
        }
    }

    public void loadData() {
        this.data.loadData();
    }

    public void saveData() {
        this.data.saveData();
    }

    public void saveInDatabase() {
        Database db = Bedwars.getInstance().getDataBase();
        if (db != null && db.isConnected()) {
            GameMode[] var2 = GameMode.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                GameMode mode = var2[var4];
                db.setUserStats(mode, this);
            }

            db.setUserCoins(this);
        }
    }
}
