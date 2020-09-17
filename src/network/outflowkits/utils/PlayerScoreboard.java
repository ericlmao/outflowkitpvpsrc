package network.outflowkits.utils;

import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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

            DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
            kdr = df.format(management.getKDR());
            coins = df.format(management.getCoins());
            killstreak = df.format(management.getCurrentKillStreak());
            bestkillstreak = df.format(management.getBestKillStreak());
            kills = df.format(management.getKills());
            deaths = df.format(management.getDeaths());

            PlayerData playerData = AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId());
            if (playerData.isInStaffMode()){
                objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lModerator Mode"));
                boolean b = plugin.target.containsKey(player);
                boolean vanished = playerData.isVanished();
                if (b){
                    Player target = plugin.target.get(player);
                    replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
                    replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', " "));
                    replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&7* &9Targeting: &f" + target.getName()));
                    replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "  "));
                    replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&7* &9Your Ping: &f" + getPing(player) + "ms"));
                    replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&7* &9Target's Ping: &f" + getPing(target) + "ms"));
                    replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "   "));
                    replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&7* &9Vanished: &f" + vanished));
                    replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));


                    if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    }
                    player.setScoreboard(score);
                    return;
                }
                replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
                replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', " "));
                replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&7* &9Targeting: &f/target"));
                replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "  "));
                replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&7* &9Your Ping: &f" + getPing(player) + "ms"));
                replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&7* &9Target's Ping: &f/target"));
                replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', "   "));
                replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&7* &9Vanished: &f" + vanished));
                replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));


                if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                player.setScoreboard(score);
                return;
            }

            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lOutflow &7(KitPvP)"));
            replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------&r"));
            replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&7* &9Kills: &f" + kills));
            replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&7* &9Deaths: &f" + deaths));
            replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "&7* &9KDR: &f" + kdr));
            replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&7* &9Killstreak: &f" + killstreak + " &7(" + bestkillstreak + ")"));
            replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&7* &9Coins: &f" + coins));
            replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', " "));
            replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&7&ooutflowkits.net"));
            replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m--------------------"));


            if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.setScoreboard(score);
        }
    }

    public static int getPing(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        return entityPlayer.ping;
    }
}
