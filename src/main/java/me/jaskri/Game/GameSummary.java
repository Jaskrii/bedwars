package me.jaskri.Game;

import me.jaskri.API.Level.BedwarsLevel;
import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GameReward;
import me.jaskri.API.Game.player.GamePlayer;
import me.jaskri.API.Game.player.Stats.GameStatistic;
import me.jaskri.API.Game.player.Stats.GameStatisticManager;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.API.Team.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;

public class GameSummary {

    private static final DecimalFormat FORMATTER = new DecimalFormat("###.##");
    private static final String EMPTY = "";
    private static final char CIRCLE = '・';
    private Map<Player, TextSection> rewards = new HashMap();
    private Game game;
    private Team winner;

    public GameSummary(Game game, Team winner) {
        this.game = game;
        this.winner = winner;
    }

    public Game getGame() {
        return this.game;
    }

    public Team getWinner() {
        return this.winner;
    }

    private String format(String first, Map.Entry<Player, Integer> entry) {
        StringBuilder builder = (new StringBuilder(first)).append(" §7- ").append(((Player)entry.getKey()).getDisplayName()).append("§7 - ").append(entry.getValue());
        return builder.toString();
    }

    private TextSection getGameSummary(Collection<GamePlayer> players) {
        List<Player> winners = new ArrayList();
        Object[] top_players = new Object[players.size() < 3 ? players.size() : 3];
        int length = top_players.length;
        int size = 0;

        GamePlayer gp;
        Player player;
        for(Iterator var6 = players.iterator(); var6.hasNext(); this.rewards.put(player, this.getRewardSummary(gp))) {
            gp = (GamePlayer)var6.next();
            player = gp.getPlayer();
            if (gp.getTeam() == this.winner) {
                winners.add(player);
            }

            GameStatisticManager stats = gp.getStatisticManager();
            int total = stats.getStatistic(GameStatistic.KILLS) + stats.getStatistic(GameStatistic.FINAL_KILLS);
            Map.Entry<Player, Integer> entry = this.createEntry(player, total);
            if (size < length) {
                top_players[size++] = entry;
            }

            for(int i = 0; i < length; ++i) {
                Object obj = top_players[i];
                if (obj != null) {
                    Map.Entry<Player, Integer> other = (Map.Entry)obj;
                    if (total > (Integer)other.getValue() && !((Player)other.getKey()).equals(player)) {
                        for(int j = length - 1; j > i; --j) {
                            top_players[j] = top_players[j - 1];
                        }

                        top_players[i] = entry;
                        break;
                    }
                }
            }
        }

        TextSection summary = new TextSection(256);
        summary.append("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        summary.append("§lBed Wars", TextAlign.CENTER);
        summary.append("");
        if (!winners.isEmpty()) {
            StringBuilder builder = (new StringBuilder(this.winner.getColoredString())).append(" §7- ");
            int winnersSize = winners.size();

            for(int i = 0; i < winnersSize; ++i) {
                Player player = (Player)winners.get(i);
                builder.append(player.getDisplayName());
                if (i != winners.size() - 1) {
                    builder.append("§7,");
                }
            }

            summary.append(builder.toString(), TextAlign.CENTER);
            summary.append("");
        }

        if (length > 0) {
            summary.append(this.format("§e§l1st Killer", (Map.Entry)top_players[0]), TextAlign.CENTER);
        }

        if (length > 1) {
            summary.append(this.format("§6§l2nd Killer", (Map.Entry)top_players[1]), TextAlign.CENTER);
        }

        if (length > 2) {
            summary.append(this.format("§c§l3rd Killer", (Map.Entry)top_players[2]), TextAlign.CENTER);
        }

        summary.append("");
        summary.append("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        return summary;
    }

    private TextSection getRewardSummary(GamePlayer gp) {
        GameStatisticManager stats = gp.getStatisticManager();
        TextSection rewardSection = new TextSection(256);
        rewardSection.append("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        rewardSection.append("§lReward Summary", TextAlign.CENTER);
        rewardSection.append("");
        rewardSection.append("§7You earned", 2);
        rewardSection.append("・" + ChatColor.GOLD + stats.getCoinsReward().getAmount() + " Bed Wars Coins", 4);
        rewardSection.append("");
        this.appendLevelSection(rewardSection, gp);
        rewardSection.append("");
        rewardSection.append("§7You earned §b" + stats.getExpReward().getAmount() + " Bed Wars Experience");
        rewardSection.append("");
        rewardSection.append("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        return rewardSection;
    }

    private void appendLevelSection(TextSection section, GamePlayer gp) {
        GameStatisticManager stats = gp.getStatisticManager();
        GameReward reward = stats.getExpReward();
        section.append("§bBed Wars Experience", TextAlign.CENTER);
        BedwarsLevel level = Bedwars.getInstance().getUser(gp.getPlayer()).getLevel();
        if (!level.isLeveling(reward.getAmount())) {
            level.incrementProgressExp(reward.getAmount());
            section.append(this.formatLevelLine(level, this.formatLevel(level.getLevel() + 1)), TextAlign.CENTER);
            section.append(level.getProgressBar(34), TextAlign.CENTER);
            section.append(this.formatLevelProgress(level), TextAlign.CENTER);
        } else {
            BedwarsLevel leveled = level.clone();
            Prestige prestige = LevelUtils.levelUp(leveled, reward.getAmount());
            if (prestige != null) {
                section.append(this.formatLevelToPrestigeLine(level, prestige), TextAlign.CENTER);
                section.append(BedwarsLevel.getProgressBar(34, 1.0F), TextAlign.CENTER);
                section.append(this.formatLevelUpToPrestigeLine(prestige), TextAlign.CENTER);
            } else {
                section.append(this.formatLevelToLevelLine(level, leveled), TextAlign.CENTER);
                section.append(BedwarsLevel.getProgressBar(34, 1.0F), TextAlign.CENTER);
                section.append(this.formatLevelUpToLevelLine(leveled), TextAlign.CENTER);
            }

        }
    }

    private String formatLevelProgress(BedwarsLevel level) {
        StringBuilder builder = (new StringBuilder()).append(ChatColor.AQUA).append(ScoreboardUtils.formatDecimal(level.getProgressExp())).append(ChatColor.GRAY).append(" / ").append(ChatColor.GREEN).append(ScoreboardUtils.formatDecimal(level.getExpToLevelUp())).append(ChatColor.GRAY).append(" (").append(FORMATTER.format((double)(level.getProgressPercentage() * 100.0F))).append("%)");
        return builder.toString();
    }

    private String formatLevelToPrestigeLine(BedwarsLevel start, Prestige end) {
        return this.formatLevelLine(start, end.getDisplayName());
    }

    private String formatLevelToLevelLine(BedwarsLevel start, BedwarsLevel end) {
        return this.formatLevelLine(start, ChatColor.AQUA + "Level " + end.getLevel());
    }

    private String formatLevelLine(BedwarsLevel start, String end) {
        String first = this.formatLevel(start.getLevel());
        int width = 211 - (TextUtils.getTextWidth(first) + TextUtils.getTextWidth(end));
        return first + TextUtils.emptyLine(width) + end;
    }

    private String formatLevel(int level) {
        return ChatColor.AQUA + "Level " + level;
    }

    private String formatLevelUpToPrestigeLine(Prestige prestige) {
        return this.formatLevelUpLine(prestige.getDisplayName());
    }

    private String formatLevelUpToLevelLine(BedwarsLevel level) {
        return this.formatLevelUpLine(ScoreboardUtils.formatDecimal(level.getLevel()));
    }

    private String formatLevelUpLine(String text) {
        StringBuilder builder = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.MAGIC).append("aa ").append(ChatColor.AQUA).append(ChatColor.BOLD).append("LEVEL UP!").append(ChatColor.AQUA).append("You are now ").append(text).append(ChatColor.AQUA).append("!").append(ChatColor.GOLD).append(ChatColor.MAGIC).append("aa ");
        return builder.toString();
    }

    public void send() {
        Collection<GamePlayer> players = this.game.getGamePlayers();
        TextSection summary = this.getGameSummary(players);
        Iterator var3 = players.iterator();

        while(var3.hasNext()) {
            GamePlayer gp = (GamePlayer)var3.next();
            Player player = gp.getPlayer();
            summary.sendMessage(player);
            ((TextSection)this.rewards.get(player)).sendMessage(player);
        }

    }

    private Map.Entry<Player, Integer> createEntry(Player player, int total) {
        return new AbstractMap.SimpleEntry(player, total);
    }
}
