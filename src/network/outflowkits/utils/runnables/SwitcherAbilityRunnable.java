package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SwitcherAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public SwitcherAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.switcher_cooldown.containsKey(player)){
                plugin.switcher_cooldown.put(player, plugin.switcher_cooldown.get(player) - 1);

                if (plugin.switcher_cooldown.get(player) <= 0){
                    plugin.switcher_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &6&lSwitcher &ais now ready.");
                }
            }
        }
    }
}
