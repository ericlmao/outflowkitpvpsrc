package network.outflowkits.kitpvp.listeners;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.kitpvp.management.Spawn;
import network.outflowkits.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PreventLeaveSpawnWOKit implements Listener {

    @EventHandler
    public void playermove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equalsIgnoreCase("kit")) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (Utils.canUseAbility(player)) {
                    PlayerManagement management = new PlayerManagement(player);
                    if (management.getKit().equals("None")) {
                        Spawn spawn = new Spawn(player);
                        spawn.send();

                        Utils.sendMessage(player, "&cYou must have a kit selected in order to leave spawn.");
                    }
                }
            }
        }
    }
}
