package network.outflowkits.kitpvp.listeners;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class ComboCounter implements Listener {

    private HashMap<Player, Integer> combo = new HashMap<>();

    @EventHandler
    public void combodamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            if (event.getDamager() instanceof Player){
                if (!Utils.isInMainWorld((Player) event.getDamager()))return;
                if (!Utils.isInMainWorld((Player) event.getEntity()))return;
                Player attacker = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();
                combo.put(victim, 0);
                PlayerManagement management = new PlayerManagement(attacker);
                if (!combo.containsKey(attacker)){
                    combo.put(attacker, 1);
                    int longest = management.getLongestCombo();

                    if (longest < combo.get(attacker)){
                        management.setLongestCombo(combo.get(attacker));
                    }
                } else {
                    int current = combo.get(attacker);
                    int longest = management.getLongestCombo();
                    combo.put(attacker, current + 1);
                    if (longest < combo.get(attacker)){
                        management.setLongestCombo(combo.get(attacker));
                    }
                }
            }
        }
    }

}
