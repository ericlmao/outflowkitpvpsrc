package network.outflowkits.clans.listeners;

import network.outflowkits.clans.ClansManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ClanStatisticsListener implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent event){
        ClansManager clans = new ClansManager();

        Player victim = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (clans.getClan(victim.getUniqueId()) != null){
            String victimClan = clans.getClan(victim.getUniqueId());

            clans.addDeath(victimClan);
        }
        if (clans.getClan(killer.getUniqueId()) != null){
            String killerClan = clans.getClan(killer.getUniqueId());

            clans.addKill(killerClan);
        }
    }
}
