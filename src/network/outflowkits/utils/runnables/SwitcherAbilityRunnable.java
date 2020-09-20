package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SwitcherAbilityRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Switcher")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Switcher");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&8[&9Ability&8] &6&lSwitcher &7is now ready.");
                    cooldown.removeCooldown("Switcher");
                }
            }
        }
    }
}
