package network.outflowkits.kitpvp.commands;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatsCMD implements CommandExecutor, Listener {

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
            viewstats(player, player);
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        PlayerManagement management = new PlayerManagement(target);
        if (player != target) {
            if (management.statisticsViewingEnabled()) {
                viewstats(player, target);
            } else {
                Utils.sendMessage(player, "&cThis player does not allow people to view their statistics.");
                return true;
            }
        } else {
            viewstats(player, player);
        }
        return true;
    }

    private void viewstats(Player viewer, OfflinePlayer player) {
        PlayerManagement management = new PlayerManagement(player);
        DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
        int kills = management.getKills();
        int deaths = management.getDeaths();
        int coins = management.getCoins();
        double kdr = management.getKDR();
        int longestCombo = management.getLongestCombo();

        List<String> kitsUnlocked = management.getUnlockedKits();

        Inventory inv = Bukkit.createInventory(null, 27, "Statistics of " + player.getName());

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE + "'s Statistics");
        ArrayList<String> skullLore = new ArrayList<>();
        skullLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        skullLore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(coins)));
        skullLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        skullMeta.setLore(skullLore);
        skull.setItemMeta(skullMeta);

        ItemStack pvpStats = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta pvpMeta = pvpStats.getItemMeta();
        pvpMeta.setDisplayName(ChatColor.DARK_RED + "PvP Statistics");

        ArrayList<String> pvpLore = new ArrayList<>();
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills: &c" + df.format(kills)));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths: &c" + df.format(deaths)));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', "&7Kill Death Ratio: &c" + df.format(kdr)));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', "&7Longest Combo: &c" + df.format(longestCombo)));
        pvpLore.add(ChatColor.translateAlternateColorCodes('&', " "));
        pvpMeta.setLore(pvpLore);
        pvpStats.setItemMeta(pvpMeta);

        ItemStack kitStatistics = new ItemStack(Material.CHEST);
        ItemMeta kitstatsMeta = kitStatistics.getItemMeta();
        kitstatsMeta.setDisplayName(ChatColor.GREEN + "Kit Statistics");
        ArrayList<String> kitstatslore = new ArrayList<>();
        kitstatslore.add(ChatColor.translateAlternateColorCodes('&', " "));
        kitstatslore.add(ChatColor.translateAlternateColorCodes('&', "&7Kits Unlocked"));

        for (String kit : kitsUnlocked){
            kitstatslore.add(ChatColor.translateAlternateColorCodes('&', "&7- &c" + kit));
        }
        kitstatslore.add(ChatColor.translateAlternateColorCodes('&', " "));
        kitstatsMeta.setLore(kitstatslore);
        kitStatistics.setItemMeta(kitstatsMeta);

        inv.setItem(10, pvpStats);
        inv.setItem(13, skull);
        inv.setItem(16, kitStatistics);

        viewer.openInventory(inv);
    }

    @EventHandler
    public void statsinv(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null)return;
        if (event.getView().getTitle().contains("Statistics of ")) {
            event.setCancelled(true);
        }
    }
}
