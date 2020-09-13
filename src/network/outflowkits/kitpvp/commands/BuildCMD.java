package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCMD implements CommandExecutor {
    private KitPvP plugin;
    public BuildCMD(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("Server.Buildmode")){
            Utils.sendMessage(player, "&cNo permission.");
            return true;
        }
        if (!plugin.buildmode.contains(player)){
            plugin.buildmode.add(player);
            Utils.sendMessage(player, "&aBuild mode &eenabled&a.");
        } else {
            plugin.buildmode.remove(player);
            Utils.sendMessage(player, "&cBuild mode &edisabled&c.");
        }
        return true;
    }
}
