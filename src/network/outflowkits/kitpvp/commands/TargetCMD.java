package network.outflowkits.kitpvp.commands;

import me.activated.core.api.rank.RankData;
import me.activated.core.plugin.AquaCoreAPI;
import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TargetCMD implements CommandExecutor, Listener {
    private KitPvP plugin;
    public TargetCMD(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        RankData rankData = AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId());
        switch (rankData.getName().toLowerCase()){
            case "owner":
            case "manager":
            case "media-owner":
            case "platformadmin":
            case "developer":
            case "senioradmin":
            case "admin":
            case "seniormod":
            case "mod+":
            case "mod":
            case "trial-mod":{
                if (args.length == 0){
                    plugin.target.remove(player);
                    Utils.sendMessage(player, "&aTarget successfully cleared.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null){
                    Utils.sendMessage(player, "&cThis player is not online.");
                    return true;
                }
                Utils.sendMessage(player, "&aSuccessfully targeted &e" + target.getName() + "&a.");
                plugin.target.put(player, target);
                return true;
            }
            default:{
                Utils.sendMessage(player, "&cYou need to be at least &eTRIAL-MOD &cto use this!");
                return true;
            }
        }
    }

    @EventHandler
    public void targetLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for (Player p : Bukkit.getOnlinePlayers()){
            if (plugin.target.containsKey(p)){
                Player target = plugin.target.get(p);
                if (player == target){
                    Utils.sendMessage(p, "&aYour target, &e" + player.getName() + " &ahas left.");
                }
            }
        }
        if (plugin.target.containsKey(player)){
            plugin.target.remove(player);
        }
    }
}
