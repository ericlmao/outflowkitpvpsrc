package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Coins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int length = args.length;
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        if (length == 0){
            Player player = (Player) sender;
            if (!Utils.isInMainWorld(player)){
                Utils.sendMessage(player, "&cYou cannot use this command in this world.");
                return true;
            }
            PlayerManagement management = new PlayerManagement(player);
            Utils.sendMessage(player, "&6&lCOINS: &7You currently have &6&l" + df.format(management.getCoins()) + " Coins&7.");
            return true;
        }
        if ("give".equals(args[0].toLowerCase())) {
            if (!(sender instanceof Player)) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    System.out.println("Player not online.");
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[2]);
                    PlayerManagement management = new PlayerManagement(target);
                    management.addCoins(amount);
                } catch (Exception e) {
                    System.out.println("Use numbers.");
                }
            }
        }
        return true;
    }
}
