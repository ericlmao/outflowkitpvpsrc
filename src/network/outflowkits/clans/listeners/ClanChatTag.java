package network.outflowkits.clans.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.clans.ClansManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ClanChatTag implements Listener {
    private KitPvP plugin;
    public ClanChatTag(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void levelChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        ClansManager clans = new ClansManager();
        String playerClan = clans.getClan(player.getUniqueId());

        String format;
        if (playerClan == null){
            format = event.getFormat().replaceFirst("<outflow_clan>", ChatColor.translateAlternateColorCodes('&', ""));
        } else {
            format = event.getFormat().replaceFirst("<outflow_clan>", ChatColor.translateAlternateColorCodes('&', "&7[&e" + playerClan + "&7] &r"));
        }
        event.setFormat(format);
    }
}
