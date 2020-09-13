package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FishermanAbilityRunnable  extends BukkitRunnable {
    private KitPvP plugin;
    public FishermanAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.fisherman_cooldown.containsKey(player)){
                plugin.fisherman_cooldown.put(player, plugin.fisherman_cooldown.get(player) - 0.1);

                if (plugin.fisherman_cooldown.get(player) <= 0){
                    plugin.fisherman_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &a&lFisherman's Reel &ais now ready.");
                }
            }
        }
    }
}
