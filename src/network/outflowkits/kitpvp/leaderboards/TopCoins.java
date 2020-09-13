package network.outflowkits.kitpvp.leaderboards;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.Leaderboard;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TopCoins implements Listener, CommandExecutor {
    private KitPvP plugin;
    public TopCoins(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    public static Leaderboard leaderboard = new Leaderboard();
    public static DecimalFormat df = new DecimalFormat("###,###,###,###,###");
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        opentopcoins(player);
        return true;
    }

    public static void opentopcoins(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Coins Leaderboard");

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

        inv.setItem(13, getNumber1());
        inv.setItem(21, getNumber2());
        inv.setItem(23, getNumber3());
        inv.setItem(29, getNumber4());
        inv.setItem(30, getNumber5());
        inv.setItem(31, getNumber6());
        inv.setItem(32, getNumber7());
        inv.setItem(33, getNumber8());
        inv.setItem(39, getNumber9());
        inv.setItem(41, getNumber10());


        inv.setItem(49, refreshItem());
        player.openInventory(inv);
    }

    private static ItemStack getFiller() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    private static ItemStack getFiller1() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack refreshItem() {
        ItemStack item = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lREFRESH"));
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber1() {
        int pos = 1;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber2() {
        int pos = 2;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber3() {
        int pos = 3;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber4() {
        int pos = 4;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber5() {
        int pos = 5;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber6() {
        int pos = 6;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber7() {
        int pos = 7;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber8() {
        int pos = 8;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNumber9() {
        int pos = 9;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private static ItemStack getNumber10() {
        int pos = 10;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(leaderboard.topCoinsName(pos));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l#" + pos + " &c" + leaderboard.topCoinsName(pos)));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Coins: &c" + df.format(leaderboard.topCoinsValue(pos))));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void selectorinvclick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;
        if (event.getView().getTitle().equals(ChatColor.GOLD + "Coins Leaderboard")) {
            event.setCancelled(true);
            if (!(event.getCurrentItem().hasItemMeta()))return;
            if (!(event.getCurrentItem().getItemMeta().hasDisplayName()))return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&a&lREFRESH"))){
                if (!plugin.leaderboardCooldown.contains(player)) {
                    opentopcoins(player);
                    Utils.sendMessage(player, "&eRefreshing leaderboard... This may take a few seconds.");

                    plugin.leaderboardCooldown.add(player);

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            plugin.leaderboardCooldown.remove(player);
                        }
                    }.runTaskLater(plugin, 20 * 5);
                } else {
                    Utils.sendMessage(player, "&cPlease wait up to 5 seconds before refreshing the leaderboard again!");
                }
            }
        }
    }
}
