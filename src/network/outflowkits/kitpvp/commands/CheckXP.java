package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.LevelManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CheckXP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int length = args.length;
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        if (length == 0) {
            Player player = (Player) sender;
            LevelManagement management = new LevelManagement(player);
            Utils.sendMessage(player, "&b&lXP: &7You currently have &b&l" + df.format(management.getExperience()) + " XP&7.");
            Utils.sendMessage(player, "&7You need &b&l" + df.format(management.getRequiredExperience()) + " XP&7 to level up!");
        }
        return true;
    }
}
