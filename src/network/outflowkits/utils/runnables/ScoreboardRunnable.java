package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public ScoreboardRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            if (player.getWorld().getName().equals("Kit")) {
                plugin.scoreboard.scoreboard(player);
            }
        }
    }
}
