package network.outflowkits.kitpvp.listeners;

import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void oncommandevent(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String command = event.getMessage();
        switch (command.toLowerCase()){
            case "/event join":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/event spec":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/event spectate":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/event host":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/events join":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/events spec":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/events spectate":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
            case "/events host":{
                if (Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    Utils.sendMessage(player, "&cYou must be in spawn to do this command.");
                    return;
                }
            }
        }
    }
}
