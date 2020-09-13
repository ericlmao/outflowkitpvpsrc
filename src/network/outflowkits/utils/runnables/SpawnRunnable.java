package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.Spawn;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public SpawnRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {

        for(Player player : Bukkit.getOnlinePlayers()){
            if (plugin.spawn_warmup.containsKey(player)){
                plugin.spawn_warmup.put(player, plugin.spawn_warmup.get(player) - 1);
                if (plugin.combat.containsKey(player)){
                    Utils.sendMessage(player, "&c&lYou may not teleport to Spawn while in combat!");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                    plugin.spawn_warmup.remove(player);
                    return;
                }
                int time = plugin.spawn_warmup.get(player);
                Utils.sendMessage(player, "&c&lSpawn teleportation commencing in &4&l" + time + " &c&lseconds!");
                Utils.playSound(player, Sound.CLICK);

                Location loc = player.getLocation();
                int x = loc.getBlockX();
                int z = loc.getBlockZ();

                if (x != plugin.spawn_warmup_location_x.get(player)){
                    plugin.spawn_warmup.remove(player);
                    plugin.spawn_warmup_location_x.remove(player);
                    plugin.spawn_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cSpawn teleportation canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }
                if (z != plugin.spawn_warmup_location_z.get(player)){
                    plugin.spawn_warmup.remove(player);
                    plugin.spawn_warmup_location_x.remove(player);
                    plugin.spawn_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cSpawn teleportation canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }

                if (time <= 0){
                    plugin.spawn_warmup.remove(player);
                    plugin.spawn_warmup_location_x.remove(player);
                    plugin.spawn_warmup_location_z.remove(player);
                    plugin.enderpearl_cooldown.remove(player);

                    if (plugin.combat.containsKey(player)){
                        Utils.sendMessage(player, "&c&lYou may not teleport to Spawn while in combat!");
                        Utils.playSound(player, Sound.VILLAGER_NO);
                        return;
                    }

                    Spawn spawn = new Spawn(player);
                    spawn.send();
                    Utils.sendMessage(player, "&a&lSpawn teleportation successful!");
                }
            }
        }
    }
}
