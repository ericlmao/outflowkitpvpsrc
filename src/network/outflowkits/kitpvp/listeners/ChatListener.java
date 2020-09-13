package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.LevelManagement;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private KitPvP plugin;
    public ChatListener(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void levelChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        LevelManagement management = new LevelManagement(player);


        String format = event.getFormat().replaceFirst("<outflow_level>", ChatColor.translateAlternateColorCodes('&', management.getLevelDisplay()));

        event.setFormat(format);
    }
}
