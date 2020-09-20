package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class EnderPearlLaunch implements Listener {
    @EventHandler
    public void pearllaunch(ProjectileLaunchEvent event){
        Entity projectile = event.getEntity();
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (projectile instanceof EnderPearl) {

                CooldownManagement cooldowns = new CooldownManagement(player);
                if (cooldowns.hasCooldown("Enderpearl_Cooldown")){
                    long cooldown = cooldowns.getCooldown("Enderpearl_Cooldown");
                    Utils.sendMessage(player, "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7cbefore doing this again!");
                    event.setCancelled(true);
                    player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
                    player.updateInventory();
                    return;
                }
                cooldowns.setCooldown("Enderpearl_Cooldown", 16);
            }
        }
    }
}
