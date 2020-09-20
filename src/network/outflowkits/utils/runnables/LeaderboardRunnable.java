package network.outflowkits.utils.runnables;

import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LeaderboardRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Leaderboard")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Leaderboard");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&aYou can now refresh Leaderboards!");
                    cooldown.removeCooldown("Leaderboard");
                }
            }
        }
    }
}
