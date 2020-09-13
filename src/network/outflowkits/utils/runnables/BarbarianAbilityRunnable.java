package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BarbarianAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public BarbarianAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.barbarian_cooldown.containsKey(player)){
                plugin.barbarian_cooldown.put(player, plugin.barbarian_cooldown.get(player) - 0.1);

                if (plugin.barbarian_cooldown.get(player) <= 0){
                    plugin.barbarian_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &4&lBarbarian's Force &ais now ready.");
                }
            }
        }
    }
}
