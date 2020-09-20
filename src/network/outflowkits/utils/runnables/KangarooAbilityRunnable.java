package network.outflowkits.utils.runnables;

import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KangarooAbilityRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Kangaroo")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Kangaroo");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&8[&9Ability&8] &9&lRocket Jump &7is now ready.");
                    cooldown.removeCooldown("Kangaroo");
                }
            }
        }
    }
}
