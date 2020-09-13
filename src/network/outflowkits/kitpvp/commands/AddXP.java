package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.LevelManagement;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddXP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            Player target = Bukkit.getPlayer(args[0]);
            try{
                int amount = Integer.parseInt(args[1]);
                LevelManagement management = new LevelManagement(target);
                management.addExperience(amount);
                return true;
            }catch (Exception ignored){}
        }
        return true;
    }
}
