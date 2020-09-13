package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CombatTimer implements CommandExecutor {
    private KitPvP plugin;

    public CombatTimer() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (!Utils.isInMainWorld(player)){
            Utils.sendMessage(player, "&cYou cannot use this command in this world.");
            return true;
        }
        if (!(plugin.combat.containsKey(player))){
            Utils.sendMessage(player, "&aYou are not in combat.");
        } else{
            double combattime = plugin.combat.get(player);
            DecimalFormat df = new DecimalFormat("###,###.#");
            Utils.sendMessage(player, "&cYour combat timer will expire in &e" + df.format(combattime) + " seconds&c.");
        }
        return true;
    }
}
