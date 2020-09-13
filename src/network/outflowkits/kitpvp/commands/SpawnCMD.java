package network.outflowkits.kitpvp.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.Spawn;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpawnCMD implements CommandExecutor {

    private KitPvP plugin;
    public SpawnCMD(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (!Utils.isInMainWorld(player)){
            Utils.sendMessage(player, "&cYou cannot use this command in this world.");
            return true;
        }
        if (args.length == 0) {
            if (!(player.getGameMode() == GameMode.SURVIVAL)){
                Spawn spawn = new Spawn(player);
                spawn.send();

                Utils.sendMessage(player, "&a&lYou have been automatically teleported to Spawn");
                return true;
            }
            if (plugin.combat.containsKey(player)) {
                Utils.sendMessage(player, "&c&lYou may not teleport to Spawn while in combat!");
                Utils.playSound(player, Sound.VILLAGER_NO);
                return true;
            }
            if (plugin.spawn_warmup.containsKey(player)) {
                Utils.sendMessage(player, "&cYou are already teleporting to spawn!");
                return true;
            } else {
                for (ProtectedRegion rg : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                    ArrayList<String> regions = new ArrayList<>();
                    regions.add("spawn");
                    regions.add("fpsspawn");
                    ArrayList<String> triggerRegions = new ArrayList<String>(regions);
                    if (triggerRegions.contains(rg.getId().toLowerCase())) {
                        Spawn spawn = new Spawn(player);
                        spawn.send();
                        Utils.sendMessage(player, "&a&lYou have been automatically teleported to Spawn");
                        return true;
                    }
                }
                Location loc = player.getLocation();
                int x = loc.getBlockX();
                int z = loc.getBlockZ();

                plugin.spawn_warmup_location_x.put(player, x);
                plugin.spawn_warmup_location_z.put(player, z);
                plugin.spawn_warmup.put(player, 5);
            }
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            Utils.sendMessage(player, "&cThe player &4" + args[0] + " &cis currently offline.");
            return true;
        }
        Spawn spawn = new Spawn(target);
        spawn.send();

        Utils.sendMessage(target, "&a&lYou have been forcefully sent to Spawn.");
        Utils.sendMessage(player, "&a&lYou have sent &c&l" + target.getName() + " &a&lto Spawn.");
        return true;
    }
}
