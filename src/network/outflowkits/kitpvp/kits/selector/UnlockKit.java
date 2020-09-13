package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.kitpvp.customevents.KitPurchaseEvent;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class UnlockKit implements Listener {

    @EventHandler
    public void unlock(KitPurchaseEvent event){
        Player player = event.getPlayer();
        int cost = event.getCost();
        String kit = event.getKit();

        DecimalFormat df = new DecimalFormat("###,###,###,###");
        Utils.sendMessage(player, "&a&lYou have purchased &e&l" + kit + " &a&lfor &e&l" + df.format(cost));

        PlayerManagement management = new PlayerManagement(player);
        management.takeCoins(cost);
        management.unlockKit(kit);
    }
}
