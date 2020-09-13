package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MarioAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public MarioAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.mario_cooldown.containsKey(player)){
                plugin.mario_cooldown.put(player, plugin.mario_cooldown.get(player) - 0.1);

                if (plugin.mario_cooldown.get(player) <= 0){
                    plugin.mario_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &c&lSuper Mushroom &ais now ready.");
                }
            }
        }
    }
}
