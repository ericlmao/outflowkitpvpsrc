package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.customevents.KitSelectEvent;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SelectKit implements Listener {
    private KitPvP plugin;
    public SelectKit(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void selectKit(KitSelectEvent event){
        Player player = event.getPlayer();
        String kit = event.getKit();

        Utils.sendMessage(player, "&a&lYou have selected the &e&l" + kit + " &a&lkit");
        Utils.playSound(player, Sound.ANVIL_USE);

        PlayerManagement management = new PlayerManagement(player);
        management.setKit(kit);
        plugin.recentKit.put(player, kit);

    }
}
