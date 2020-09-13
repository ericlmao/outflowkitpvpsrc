package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhatKit implements CommandExecutor {
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
        if (args.length == 0){
            PlayerManagement management = new PlayerManagement(player);
            switch (management.getKit()){
                case "None":{
                    Utils.sendMessage(player, "&7You are currently not using a kit.");
                    return true;
                }
                default:{
                    Utils.sendMessage(player, "&7You are currently use the &c" + management.getKit() + " &7kit.");
                    return true;
                }
            }
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            Utils.sendMessage(player, "&cThe player &e" + args[0] + " &cis not online.");
            return true;
        }
        PlayerManagement management = new PlayerManagement(target);
        switch (management.getKit()){
            case "None":{
                Utils.sendMessage(player, "&c" + target.getName() + " &7is currently not using a kit.");
                return true;
            }
            default:{
                Utils.sendMessage(player, "&c" + target.getName() + " &7is currently use the &c" + management.getKit() + " &7kit.");
                return true;
            }
        }
    }
}
