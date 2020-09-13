package network.outflowkits.kitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class NoExplosion implements Listener {

    @EventHandler
    public void explosion(ExplosionPrimeEvent event){
        event.setCancelled(true);
    }
}
