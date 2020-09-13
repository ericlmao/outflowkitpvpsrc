package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public CombatRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.combat.containsKey(player)){
                plugin.combat.put(player, plugin.combat.get(player) - 0.1);

                if (plugin.combat.get(player) <= 0){
                    plugin.combat.remove(player);
                    plugin.combatwith.remove(player);
                    Utils.sendMessage(player, "&aYou are no longer in combat.");
                }
            }
        }
    }
}
