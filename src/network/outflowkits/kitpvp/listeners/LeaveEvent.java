package network.outflowkits.kitpvp.listeners;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void leave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("Kit")) {
            PlayerManagement management = new PlayerManagement(player);
            if (Utils.canUseAbility(player)) {
                management.endKillStreak();
            }
        }
    }
}
