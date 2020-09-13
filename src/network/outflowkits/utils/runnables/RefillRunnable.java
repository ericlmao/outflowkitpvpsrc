package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.customevents.KitSelectEvent;
import network.outflowkits.kitpvp.kits.*;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class RefillRunnable extends BukkitRunnable {
    private KitPvP plugin;

    public RefillRunnable() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.refill_warmup.containsKey(player)) {
                plugin.refill_warmup.put(player, plugin.refill_warmup.get(player) - 1);
                int time = plugin.refill_warmup.get(player);
                Utils.sendMessage(player, "&aRefilling in &e" + time + " &aseconds!");
                Utils.playSound(player, Sound.CLICK);

                Location loc = player.getLocation();
                int x = loc.getBlockX();
                int z = loc.getBlockZ();

                if (x != plugin.refill_warmup_location_x.get(player)) {
                    plugin.refill_warmup.remove(player);
                    plugin.refill_warmup_location_x.remove(player);
                    plugin.refill_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cRefilling canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }
                if (z != plugin.refill_warmup_location_z.get(player)) {
                    plugin.refill_warmup.remove(player);
                    plugin.refill_warmup_location_x.remove(player);
                    plugin.refill_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cRefilling canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }

                if (time <= 0) {
                    plugin.refill_warmup.remove(player);
                    plugin.refill_warmup_location_x.remove(player);
                    plugin.refill_warmup_location_z.remove(player);

                    player.closeInventory();
                    Utils.sendMessage(player, "&aSuccessfully refilled!");
                    Utils.playSound(player, Sound.ANVIL_USE);

                    Utils.giveHealing(player);
                }
            }
        }
    }
}
