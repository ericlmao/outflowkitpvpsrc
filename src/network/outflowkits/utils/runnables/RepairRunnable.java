package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.customevents.KitSelectEvent;
import network.outflowkits.kitpvp.kits.*;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class RepairRunnable extends BukkitRunnable {
    private KitPvP plugin;

    public RepairRunnable() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.repair_warmup.containsKey(player)) {
                plugin.repair_warmup.put(player, plugin.repair_warmup.get(player) - 1);
                int time = plugin.repair_warmup.get(player);
                Utils.sendMessage(player, "&aRepairing in &e" + time + " &aseconds!");
                Utils.playSound(player, Sound.CLICK);

                Location loc = player.getLocation();
                int x = loc.getBlockX();
                int z = loc.getBlockZ();

                if (x != plugin.repair_warmup_location_x.get(player)) {
                    plugin.repair_warmup.remove(player);
                    plugin.repair_warmup_location_x.remove(player);
                    plugin.repair_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cRepairing canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }
                if (z != plugin.repair_warmup_location_z.get(player)) {
                    plugin.repair_warmup.remove(player);
                    plugin.repair_warmup_location_x.remove(player);
                    plugin.repair_warmup_location_z.remove(player);
                    Utils.sendMessage(player, "&cRepairing canceled due to movement.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                }

                if (time <= 0) {
                    plugin.repair_warmup.remove(player);

                    String kit = plugin.recentKit.get(player);

                    player.getInventory().clear();
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    player.closeInventory();
                    Utils.sendMessage(player, "&aSuccessfully repaired!");
                    Utils.playSound(player, Sound.ANVIL_USE);
                    switch (kit.toLowerCase()) {
                        case "pvp": {
                            PvP.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "barbarian": {
                            Barbarian.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "chemist": {
                            Chemist.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "fisherman": {
                            Fisherman.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "avatar": {
                            Avatar.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "kidnapper": {
                            Kidnapper.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "mario": {
                            Mario.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "kangaroo": {
                            Kangaroo.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "ninja": {
                            Ninja.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "stomper": {
                            Stomper.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "dwarf": {
                            Dwarf.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "teleporter": {
                            Teleporter.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "switcher": {
                            Switcher.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "gank": {
                            Gank.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                        case "tank": {
                            Tank.getKit(player);
                            Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                            return;
                        }
                    }
                }
            }
        }
    }
}
