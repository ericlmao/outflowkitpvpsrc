package network.outflowkits.utils;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerScoreboard implements Listener {

    public ArrayList<UUID> initialized;
    private KitPvP plugin;

    public PlayerScoreboard() {
        initialized = new ArrayList<UUID>();
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        scoreboard(player);
        this.initialized.add(player.getUniqueId());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        this.initialized.remove(player.getUniqueId());
    }

    public static String getEntryFromScore(final Objective o, final int score) {
        if (o == null) {
            return null;
        }
        if (!hasScoreTaken(o, score)) {
            return null;
        }
        for (final String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return o.getScore(s).getEntry();
            }
        }
        return null;
    }

    public static boolean hasScoreTaken(Objective o, final int score) {
        for (final String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return true;
            }
        }
        return false;
    }

    public static void replaceScore(Objective o, final int score, final String name) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                return;
            }
            if (!getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                o.getScoreboard().resetScores(getEntryFromScore(o, score));
            }
        }
        o.getScore(name).setScore(score);
    }

    public void scoreboard(Player player) {
        if (player.getWorld().getName().equals("Kit")) {
            if (initialized.contains(player.getUniqueId())) {
                return;
            }
            if (player.getScoreboard().equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard())) {
                player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
            }
            Scoreboard score = player.getScoreboard();
            final Objective objective = (score.getObjective(player.getName()) == null) ? score.registerNewObjective(player.getName(), "dummy") : score.getObjective(player.getName());

            PlayerManagement management = new PlayerManagement(player);

            String kdr;
            String coins;
            String killstreak;
            String bestkillstreak;
            String kills;
            String deaths;
            String kit = management.getKit();

            DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
            DecimalFormat df1 = new DecimalFormat("###,###.#");
            kdr = df.format(management.getKDR());
            coins = df.format(management.getCoins());
            killstreak = df.format(management.getCurrentKillStreak());
            bestkillstreak = df.format(management.getBestKillStreak());
            kills = df.format(management.getKills());
            deaths = df.format(management.getDeaths());

            // Checking if the player is in combat
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lOutflow &7[KitPvP]"));
            if (plugin.combat.containsKey(player)) {
                double combatTime = plugin.combat.get(player);

                // Checking if the player is also has a pearl cooldown
                if (plugin.enderpearl_cooldown.containsKey(player)) {
                    double pearlTime = plugin.enderpearl_cooldown.get(player);
                    // Combat & Enderpearl scoreboard
                    replaceScore(objective, 12, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
                    replaceScore(objective, 11, ChatColor.translateAlternateColorCodes('&', "&9Kills: &f" + kills));
                    replaceScore(objective, 10, ChatColor.translateAlternateColorCodes('&', "&9Deaths: &f" + deaths));
                    replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&9KDR: &f" + kdr));
                    replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&9Killstreak: &f" + killstreak + " &7(" + bestkillstreak + ")"));
                    replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "  "));
                    replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "&9Coins: &f" + coins));
                    replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&9Active Kit: &f" + kit));
                    replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "    "));
                    replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "&9Combat Time: &f" + df1.format(combatTime) + "s"));
                    replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&9Ender Pearl: &f" + df1.format(pearlTime) + "s"));
                    replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));

                    if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    }
                    player.setScoreboard(score);

                    return;
                }
                // Combat Scoreboard

                replaceScore(objective, 11, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
                replaceScore(objective, 10, ChatColor.translateAlternateColorCodes('&', "&9Kills: &f" + kills));
                replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&9Deaths: &f" + deaths));
                replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&9KDR: &f" + kdr));
                replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&9Killstreak: &f" + killstreak + " &7(" + bestkillstreak + ")"));
                replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "  "));
                replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&9Coins: &f" + coins));
                replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&9Active Kit: &f" + kit));
                replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "    "));
                replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&9Combat Time: &f" + df1.format(combatTime) + "s"));
                replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));

                if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                player.setScoreboard(score);

                return;
            }
            // Checking if the player has a enderpearl cooldown
            if (plugin.enderpearl_cooldown.containsKey(player)) {
                double pearlTime = plugin.enderpearl_cooldown.get(player);
                // Enderpearl scoreboard
                replaceScore(objective, 11, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
                replaceScore(objective, 10, ChatColor.translateAlternateColorCodes('&', "&9Kills: &f" + kills));
                replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&9Deaths: &f" + deaths));
                replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&9KDR: &f" + kdr));
                replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&9Killstreak: &f" + killstreak + " &7(" + bestkillstreak + ")"));
                replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "  "));
                replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&9Coins: &f" + coins));
                replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&9Active Kit: &f" + kit));
                replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "    "));
                replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&9Ender Pearl: &f" + df1.format(pearlTime) + "s"));
                replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));

                if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                player.setScoreboard(score);

                return;
            }

            replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
            replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&9Kills: &f" + kills));
            replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&9Deaths: &f" + deaths));
            replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "&9KDR: &f" + kdr));
            replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&9Killstreak: &f" + killstreak + " &7(" + bestkillstreak + ")"));
            replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "  "));
            replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "&9Coins: &f" + coins));
            replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&9Active Kit: &f" + kit));
            replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));


            if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.setScoreboard(score);
        }
    }
}
