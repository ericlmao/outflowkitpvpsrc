package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonationCMD implements CommandExecutor {

    private KitPvP plugin;
    public DonationCMD(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        // command code here
        return true;
    }
}
