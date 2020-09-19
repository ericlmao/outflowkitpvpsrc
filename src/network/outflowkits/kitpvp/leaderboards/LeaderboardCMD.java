package network.outflowkits.kitpvp.leaderboards;

import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class LeaderboardCMD implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        openmainmenu(player);
        return true;
    }

    private void openmainmenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Leaderboards");

        // Gray glass
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, getFiller());
        }
        
        // Black glass
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, getFiller1());
        }
        inv.setItem(17, getFiller1());
        inv.setItem(18, getFiller1());
        inv.setItem(26, getFiller1());
        inv.setItem(27, getFiller1());
        inv.setItem(35, getFiller1());
        inv.setItem(36, getFiller1());
        for (int i = 44; i < 54; i++) {
            inv.setItem(i, getFiller1());
        }

        inv.setItem(20, getKills());
        inv.setItem(22, getDeaths());
        inv.setItem(24, getKS());
        inv.setItem(40, getCoins());

        player.openInventory(inv);
    }

    @EventHandler
    public void lbclick(InventoryClickEvent event){
        if (event.getView().getTitle().equals("Leaderboards")){
            if (event.getView() == null)return;
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)return;
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()){
                case DIAMOND_SWORD:{
                    Utils.sendMessage((Player) event.getWhoClicked(), "&eLoading leaderboard... This may take a few seconds.");
                    TopKills.opentopkills((Player) event.getWhoClicked());
                    return;
                }
                case DOUBLE_PLANT:{
                    Utils.sendMessage((Player) event.getWhoClicked(), "&eLoading leaderboard... This may take a few seconds.");
                    TopCoins.opentopcoins((Player) event.getWhoClicked());
                    return;
                }
                case IRON_CHESTPLATE:{
                    Utils.sendMessage((Player) event.getWhoClicked(), "&eLoading leaderboard... This may take a few seconds.");
                    TopKillstreak.opentopkS((Player) event.getWhoClicked());
                    return;
                }
                case SKULL_ITEM:{
                    Utils.sendMessage((Player) event.getWhoClicked(), "&eLoading leaderboard... This may take a few seconds.");
                    TopDeaths.opentopdeaths((Player) event.getWhoClicked());
                }
            }
        }
    }


    private ItemStack getKills() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Top Kills");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getDeaths() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Top Deaths");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getCoins() {
        ItemStack item = new ItemStack(Material.DOUBLE_PLANT, 1, (short) 0);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Top Coins");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getKS() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Top Killstreaks");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getFiller() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getFiller1() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
}
