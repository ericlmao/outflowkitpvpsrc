package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.LevelManagement;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConvertCMD implements CommandExecutor, Listener {
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
        openconversioninv(player);
        
        return true;
    }

    private void openconversioninv(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "XP Conversion");

        // Black stained glass pane
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, getFiller1());
        }
        for (int i = 17; i < 27; i++) {
            inv.setItem(i, getFiller1());
        }
        // Gray stained glass pane
        inv.setItem(10, getFiller2());
        inv.setItem(12, getFiller2());
        inv.setItem(14, getFiller2());
        inv.setItem(16, getFiller2());

        // 10 XP
        inv.setItem(11, getConversionItem1());
        // 25 XP
        inv.setItem(13, getConversionItem2());
        // 50 XP
        inv.setItem(15, getConversionItem3());
        player.openInventory(inv);
    }


    @EventHandler
    public void conversionclick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("XP Conversion")){
            if (event.getView() == null)return;
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)return;
            PlayerManagement management = new PlayerManagement(player);
            LevelManagement levelManagement = new LevelManagement(player);
            int xp;
            int cost;
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()){
                case CHEST:{
                    xp = 10;
                    cost = 50;

                    int currentcoins = management.getCoins();

                    if (currentcoins >= cost){
                        management.takeCoins(cost);
                        levelManagement.addExperience(xp);

                        Utils.sendMessage(player, "&a&lYou have converted &6&l" + cost + " Coins &a&linto &b&l" + xp + " XP");
                        Utils.playSound(player, Sound.LEVEL_UP);
                        return;
                    } else{
                        Utils.sendMessage(player, "&c&lYou cannot afford to convert Coins into XP");
                        Utils.playSound(player, Sound.VILLAGER_NO);
                        return;
                    }
                }
                case TRAPPED_CHEST:{
                    xp = 20;
                    cost = 100;

                    int currentcoins = management.getCoins();

                    if (currentcoins >= cost){
                        management.takeCoins(cost);
                        levelManagement.addExperience(xp);

                        Utils.sendMessage(player, "&a&lYou have converted &6&l" + cost + " Coins &a&linto &b&l" + xp + " XP");
                        Utils.playSound(player, Sound.LEVEL_UP);
                        return;
                    } else{
                        Utils.sendMessage(player, "&c&lYou cannot afford to convert Coins into XP");
                        Utils.playSound(player, Sound.VILLAGER_NO);
                        return;
                    }
                }
                case ENDER_CHEST:{
                    xp = 50;
                    cost = 250;

                    int currentcoins = management.getCoins();

                    if (currentcoins >= cost){
                        management.takeCoins(cost);
                        levelManagement.addExperience(xp);

                        Utils.sendMessage(player, "&a&lYou have converted &6&l" + cost + " Coins &a&linto &b&l" + xp + " XP");
                        Utils.playSound(player, Sound.LEVEL_UP);
                        return;
                    } else{
                        Utils.sendMessage(player, "&c&lYou cannot afford to convert Coins into XP");
                        Utils.playSound(player, Sound.VILLAGER_NO);
                        return;
                    }
                }
            }


        }
    }

    private ItemStack getFiller1(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getFiller2(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getConversionItem1(){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l10 XP"));

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to trade &6&l50 Coins &7for &b&l10 XP&7."));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getConversionItem2(){
        ItemStack item = new ItemStack(Material.TRAPPED_CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l20 XP"));

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to trade &6&l100 Coins &7for &b&l20 XP&7."));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getConversionItem3(){
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&l50 XP"));

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to trade &6&l250 Coins &7for &b&l50 XP&7."));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
