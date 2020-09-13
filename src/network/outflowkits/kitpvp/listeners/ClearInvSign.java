package network.outflowkits.kitpvp.listeners;

import net.md_5.bungee.api.ChatColor;
import network.outflowkits.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;

public class ClearInvSign implements Listener {

    @EventHandler
    public void clearsignevent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!(sign.getLine(2).equals(ChatColor.translateAlternateColorCodes('&', "&aClick to clear")))) return;
        if (!(sign.getLine(3).equals(ChatColor.translateAlternateColorCodes('&', "&ayour inventory")))) return;

        player.getInventory().clear();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.getInventory().getHelmet().setType(Material.AIR);

        player.getInventory().getLeggings().setType(Material.AIR);

        player.getInventory().getLeggings().setType(Material.AIR);

        player.getInventory().getBoots().setType(Material.AIR);

        player.updateInventory();

        Utils.sendMessage(player, "&aSuccessfully cleared inventory.");

    }


}
