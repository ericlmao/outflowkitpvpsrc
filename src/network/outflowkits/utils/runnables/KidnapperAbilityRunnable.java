package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KidnapperAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public KidnapperAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.kidnapper_cooldown.containsKey(player)){
                plugin.kidnapper_cooldown.put(player, plugin.kidnapper_cooldown.get(player) - 0.1);

                if (plugin.kidnapper_cooldown.get(player) <= 0){
                    plugin.kidnapper_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &8&lHandcuffs &ais now ready.");
                }
            }
        }
    }
}
