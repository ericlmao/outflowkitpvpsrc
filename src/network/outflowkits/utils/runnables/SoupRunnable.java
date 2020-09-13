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
            if (plugin.stomper_cooldown.containsKey(player)){
                plugin.stomper_cooldown.put(player, plugin.stomper_cooldown.get(player) - 0.1);

                if (plugin.stomper_cooldown.get(player) <= 0){
                    plugin.stomper_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &9&lStomper &ais now ready.");
                }
            }
        }
    }
}
