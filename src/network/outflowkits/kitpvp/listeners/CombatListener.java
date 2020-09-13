package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatListener implements Listener {
    private KitPvP plugin;

    public CombatListener() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent event){
        if (event.isCancelled())return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (event.isCancelled())return;
            if (event.getDamager() instanceof Projectile){
                Projectile projectile = (Projectile) event.getDamager();
                Player attacker = (Player) projectile.getShooter();
                if (!Utils.isInMainWorld(attacker))return;
                Player victim = (Player) event.getEntity();
                if (!Utils.isInMainWorld(victim))return;
                if (!(plugin.combat.containsKey(victim))){
                    Utils.sendMessage(victim, "&c&lCOMBAT: &7You are now in combat! Do not log out.");
                }
                if (!(plugin.combat.containsKey(attacker))){
                    Utils.sendMessage(attacker, "&c&lCOMBAT: &7You are now in combat! Do not log out.");
                }
                plugin.combat.put(attacker, 20.0);
                plugin.combat.put(victim, 20.0);
            }
            if (!(event.getEntity() instanceof Player))return;
            if (!(event.getDamager() instanceof Player))return;
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
            if (!Utils.isInMainWorld(attacker))return;
            if (!Utils.isInMainWorld(attacker))return;
            if (attacker == victim)return;

            if (!(plugin.combat.containsKey(victim))){
                Utils.sendMessage(victim, "&cYou are now in combat.");
            }
            if (!(plugin.combat.containsKey(attacker))){
                Utils.sendMessage(attacker, "&cYou are now in combat.");
            }
            plugin.combat.put(attacker, 20.0);
            plugin.combat.put(victim, 20.0);
            plugin.combatwith.put(attacker, victim);
            plugin.combatwith.put(victim, attacker);
        }, 1);
    }

    @EventHandler
    public void leave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (plugin.combat.containsKey(player)){
            plugin.combat.remove(player);
            plugin.combatwith.remove(plugin.combatwith.get(player));
            plugin.combatwith.remove(player);
            player.setHealth(0);

            Utils.broadcast("&c&lLOGGER: &c" + player.getName() + " &7has logged out while in combat!");
        }
    }
}
