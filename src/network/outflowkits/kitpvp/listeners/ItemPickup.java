package network.outflowkits.kitpvp.listeners;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPickup implements Listener {
    @EventHandler
    public void itemPickup(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (!Utils.isInMainWorld(player))return;
        PlayerManagement management = new PlayerManagement(player);

        if (management.potionsEnabled()){
            if (item.getItemStack().getType() == Material.MUSHROOM_SOUP){
                event.setCancelled(true);
                item.remove();
                player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
            }
        } else {
            if (item.getItemStack().getType() == Material.POTION){
                event.setCancelled(true);
                item.remove();
                player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
            }
        }
    }
}
