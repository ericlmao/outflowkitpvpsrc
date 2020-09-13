package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SoupRegistration implements Listener {
    private KitPvP plugin;
    public SoupRegistration(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void soup(PlayerInteractEvent event){
        Player player = event.getPlayer();
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if (event.getItem() == null)return;
        if (event.getItem().getType() == Material.MUSHROOM_SOUP) {
            if (plugin.soup.contains(player)) return;
            if (player.getHealth() == player.getMaxHealth())return;
            plugin.soup.add(player);
            double toHeal = Math.min(player.getHealth() + 7.0, player.getMaxHealth());
            player.setHealth(toHeal);
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.getInventory().getItem(slot).setType(Material.BOWL);
                    player.updateInventory();
                    plugin.soup.remove(player);
                }
            }.runTaskLater(plugin, 1L);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void soupbutinteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item == null)return;
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if (event.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
            if (plugin.soup.contains(player)){
                return;
            }
            if (player.getHealth() == player.getMaxHealth())return;
            plugin.soup.add(player);
            double toHeal = Math.min(player.getHealth() + 7.0, player.getMaxHealth());
            player.setHealth(toHeal);
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.getInventory().getItem(slot).setType(Material.BOWL);
                    player.updateInventory();
                    plugin.soup.remove(player);
                }
            }.runTaskLater(plugin, 1L);
        }
    }
}
