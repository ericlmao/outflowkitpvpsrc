package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {
    private KitPvP plugin;
    public BuildListener(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void build(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (!plugin.buildmode.contains(player)){
            event.setCancelled(true);
            Utils.sendMessage(player, "&c&lYou cannot build here.");
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void build(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (!plugin.buildmode.contains(player)){
            event.setCancelled(true);
            Utils.sendMessage(player, "&c&lYou cannot build here.");
        }
    }
}
