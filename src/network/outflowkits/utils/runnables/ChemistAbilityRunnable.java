package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChemistAbilityRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Chemist")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Chemist");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&8[&9Ability&8] &8&lSmoke Bomb &7is now ready.");
                    cooldown.removeCooldown("Chemist");
                }
            }
        }
    }
}
