package network.outflowkits.kitpvp.commands;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class KitUnlocker implements CommandExecutor {
    private KitPvP plugin;
    public KitUnlocker(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            PlayerManagement management = new PlayerManagement(player);
            if (management.getKitUnlockers() == 0) {
                Utils.sendMessage(player, "&cYou do not have any Kit Unlockers left.");
                return true;
            }
            List<String> unlockable = management.getKits();
            List<String> unlocked = management.getUnlockedKits();

            Random random = new Random();
            int randomNumber = random.nextInt(unlockable.size());
            String unlockerSelected = unlockable.get(randomNumber);

            for (String kitsunlocked : unlocked) {
                if (unlockerSelected.equals(kitsunlocked)) {
                    management.removeKitUnlockers(1);
                    management.addCoins(5000);
                    Utils.sendMessage(player, "&7The Kit Unlocker has granted you the &a" + unlockerSelected + " &7kit, but because you have it already unlocked you have been given &a5,000 Coins&7.");
                    return true;
                }
            }
            management.unlockKit(unlockerSelected);
            Utils.sendMessage(player, "&7The Kit Unlocker has granted you the &a" + unlockerSelected + " &7kit.");
            return true;
        }
        switch (args[0].toLowerCase()){
            case "give":{
                if (player.hasPermission("Server.GiveKitUnlockers")){
                    if (args.length == 1){
                        Utils.sendMessage(player, "&7You must provide a username.");
                        Utils.sendMessage(player, "&c/kitunlocker give <player> <amount>");
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null){
                        Utils.sendMessage(player, "&7The player &c" + args[1] + " &7is not online.");
                        return true;
                    }
                    if (args.length == 2){
                        Utils.sendMessage(player, "&7You must provide a number.");
                        Utils.sendMessage(player, "&c/kitunlocker give <player> <amount>");
                        return true;
                    }
                    try {
                        int amount = Integer.parseInt(args[2]);
                        PlayerManagement targetManager = new PlayerManagement(target);
                        targetManager.giveKitUnlockers(amount);

                        Utils.sendMessage(player, "&7You have given &a" + amount + " &7Kit Unlockers to &a" + target.getName() + "&7.");
                        Utils.sendMessage(target, "&7You have been given &a" + amount + " &7Kit Unlockers.");
                        return true;
                    }catch (Exception e){
                        Utils.sendMessage(player, "&7You must provide a number.");
                        Utils.sendMessage(player, "&c/kitunlocker give <player> <amount>");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
