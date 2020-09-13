package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NinjaAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public NinjaAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.ninja_cooldown.containsKey(player)){
                plugin.ninja_cooldown.put(player, plugin.ninja_cooldown.get(player) - 0.1);

                if (plugin.ninja_cooldown.get(player) <= 0){
                    plugin.ninja_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &c&lNinja Swerve &ais now ready.");
                }
            }
        }
    }
}
