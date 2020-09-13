package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RepairCMD implements CommandExecutor {
    private KitPvP plugin;
    public RepairCMD(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (!Utils.canUseAbility(player)){
            Utils.sendMessage(player, "&cYou cannot use this command in spawn.");
            return true;
        }
        if (plugin.repair_warmup.containsKey(player)){
            Utils.sendMessage(player, "&cYou are already commencing a repair!");
            return true;
        }
        Utils.sendMessage(player, "&aCommencing repair! Please stand still for &e5 seconds&a.");
        Location loc = player.getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        plugin.repair_warmup_location_x.put(player, x);
        plugin.repair_warmup_location_z.put(player, z);
        plugin.repair_warmup.put(player, 5);
        return true;
    }
}
