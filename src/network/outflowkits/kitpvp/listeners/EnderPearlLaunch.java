package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
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
    private KitPvP plugin;

    public EnderPearlLaunch() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @EventHandler
    public void pearllaunch(ProjectileLaunchEvent event){
        Entity projectile = event.getEntity();
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (projectile instanceof EnderPearl) {
                if (!(plugin.enderpearl_cooldown.containsKey(player))){
                    plugin.enderpearl_cooldown.put(player, 15.0);
                } else{
                    event.setCancelled(true);
                    player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
                    player.updateInventory();
                    double time = plugin.enderpearl_cooldown.get(player);
                    Utils.sendMessage(player, "&cPlease wait &e" + time + " seconds &cbefore doing this again!");
                }
            }
        }
    }
}
