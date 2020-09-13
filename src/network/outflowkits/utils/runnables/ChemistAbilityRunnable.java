package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChemistAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public ChemistAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.chemist_cooldown.containsKey(player)){
                plugin.chemist_cooldown.put(player, plugin.chemist_cooldown.get(player) - 0.1);

                if (plugin.chemist_cooldown.get(player) <= 0){
                    plugin.chemist_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &8&lSmoke Bomb &ais now ready.");
                }
            }
        }
    }
}
