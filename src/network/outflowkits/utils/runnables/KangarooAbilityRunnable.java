package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KangarooAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public KangarooAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.kangaroo_cooldown.containsKey(player)){
                plugin.kangaroo_cooldown.put(player, plugin.kangaroo_cooldown.get(player) - 0.1);

                if (plugin.kangaroo_cooldown.get(player) <= 0){
                    plugin.kangaroo_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &9&lRocket Jump &ais now ready.");
                }
            }
        }
    }
}
