package network.outflowkits.kitpvp.listeners;

import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class DisableHunger implements Listener {
    @EventHandler
    public void denyHunger(FoodLevelChangeEvent event){
        if (!Utils.isInMainWorld((Player) event.getEntity()))return;
        event.setFoodLevel(20);
    }
}
