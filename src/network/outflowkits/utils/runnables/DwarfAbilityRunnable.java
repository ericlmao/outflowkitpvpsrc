package network.outflowkits.utils.runnables;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DwarfAbilityRunnable extends BukkitRunnable {
    private KitPvP plugin;
    public DwarfAbilityRunnable(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManagement management = new PlayerManagement(player);
                if (plugin.dwarf_cooldown.containsKey(player)) {
                    plugin.dwarf_cooldown.put(player, plugin.dwarf_cooldown.get(player) - 0.1);

                    if (plugin.dwarf_cooldown.get(player) <= 0) {
                        plugin.dwarf_cooldown.remove(player);
                        Utils.sendMessage(player, "&aYour Ability &6&lDWARF YEET &ais now ready.");
                    }
                }
            if (management.getKit().equals("Dwarf")) {
                if (plugin.dwarf_warmup.containsKey(player)) {
                    if (plugin.dwarf_cooldown.containsKey(player)) return;
                    if (player.isSneaking()) {
                        if (plugin.dwarf_warmup.get(player) >= 5) {
                            if (!plugin.dwarf_ready.contains(player)){
                                Utils.sendMessage(player, "&7[&6Dwarf&7]: &eAbility fully charged!");
                                plugin.dwarf_ready.add(player);
                            }
                        } else {
                            plugin.dwarf_warmup.put(player, plugin.dwarf_warmup.get(player) + 1);
                            Utils.sendMessage(player, "&7[&6Dwarf&7]: &eAbility charging up...");
                        }
                    } else {
                        plugin.dwarf_warmup.remove(player);
                        Utils.sendMessage(player, "&7[&6Dwarf&7]: &eAbility charge up failed. You must be shifting.");
                    }
                    return;
                } else {
                    if (player.isSneaking()) {
                        plugin.dwarf_warmup.put(player, 0);
                        Utils.sendMessage(player, "&7[&6Dwarf&7]: &eAbility charging up started. Please shift for up to 5 seconds.");
                        return;
                    }
                }
            }
        }
    }
}
