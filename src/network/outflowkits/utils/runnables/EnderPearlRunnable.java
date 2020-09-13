package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderPearlRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public EnderPearlRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.enderpearl_cooldown.containsKey(player)){
                plugin.enderpearl_cooldown.put(player, plugin.enderpearl_cooldown.get(player) - 0.1);

                if (plugin.enderpearl_cooldown.get(player) <= 0){
                    plugin.enderpearl_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYou can now use Ender Pearls.");
                }
            }
        }
    }
}
