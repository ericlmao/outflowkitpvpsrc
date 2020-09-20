package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderPearlRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Enderpearl_Cooldown")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Enderpearl_Cooldown");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&aYou can now use Ender Pearls.");
                    cooldown.removeCooldown("Enderpearl_Cooldown");
                }
            }
        }
    }
}
