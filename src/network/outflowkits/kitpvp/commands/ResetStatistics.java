package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ResetStatistics implements CommandExecutor {
    private KitPvP plugin;
    public ResetStatistics(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            sender.sendMessage(ChatColor.RED + "You may only use this command through console.");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID uuid = target.getUniqueId();
        if (plugin.data.config.get(uuid.toString()) == null){
            Utils.sendMessage(sender, "&cUser not found.");
            return true;
        }
        plugin.data.config.set(uuid.toString() + ".kills", 0);
        plugin.data.config.set(uuid.toString() + ".deaths", 0);
        plugin.data.config.set(uuid.toString() + ".coins", 0);
        plugin.data.config.set(uuid.toString() + ".killstreak.current", 0);
        plugin.data.config.set(uuid.toString() + ".killstreak.best", 0);
        plugin.data.config.set(uuid.toString() + ".levels.current", 0);
        plugin.data.config.set(uuid.toString() + ".levels.xp", 0);
        plugin.data.config.set(uuid.toString() + ".levels.required", 250);

        PlayerManagement management = new PlayerManagement(target);
        List<String> kits = management.getUnlockedKits();

        for(String kit : kits){
            plugin.data.config.set(target.getUniqueId().toString() + ".kit-stats." + kit + ".deaths", 0);
            plugin.data.config.set(target.getUniqueId().toString() + ".kit-stats." + kit + ".kills", 0);
        }

        plugin.data.saveData();

        Utils.sendMessage(sender, "&a&lSuccessfully reset player statistics of &e&l" + target.getName());
        return true;
    }
}
