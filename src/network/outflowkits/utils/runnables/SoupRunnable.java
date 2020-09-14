package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SoupRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public SoupRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.soup.contains(player)){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if (plugin.soup.contains(player)){
                            plugin.soup.remove(player);
                        }
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }
}
