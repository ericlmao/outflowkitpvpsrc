package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AvatarAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public AvatarAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (plugin.avatar_cooldown.containsKey(player)){
                plugin.avatar_cooldown.put(player, plugin.avatar_cooldown.get(player) - 0.1);

                if (plugin.avatar_cooldown.get(player) <= 0){
                    plugin.avatar_cooldown.remove(player);
                    Utils.sendMessage(player, "&aYour Ability &b&lWater Gun &ais now ready.");
                }
            }
        }
    }
}
