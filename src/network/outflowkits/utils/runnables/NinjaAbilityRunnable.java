package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NinjaAbilityRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            CooldownManagement cooldown = new CooldownManagement(player);
            if (cooldown.hasCooldown("Ninja")){
                long current = System.currentTimeMillis();
                long cooldownInMills = cooldown.getCooldown("Ninja");

                if (current > cooldownInMills){
                    Utils.sendMessage(player, "&8[&9Ability&8] &c&lNinja Swerve &7is now ready.");
                    cooldown.removeCooldown("Ninja");
                }
            }
        }
    }
}
