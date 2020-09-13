package network.outflowkits.kitpvp.listeners;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerSettings implements  Listener {

    @EventHandler
    public void settingsItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() == Material.AIR)return;
        if (event.getItem().getType() == Material.WATCH){
            if (!Utils.canUseAbility(player)){
                event.setCancelled(true);
                openSettings(player);
            }
            return;
        }
    }

    private void openSettings(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "User Settings");

        for (int i = 0; i < 10; i++) {
            inv.setItem(i, getFiller());
        }
        for (int i = 17; i < 27; i++) {
            inv.setItem(i, getFiller());
        }

        inv.setItem(11, getDropSettings(player));
        inv.setItem(13, getStatisticsViewing(player));
        inv.setItem(15, getPotionsSettings(player));

        player.openInventory(inv);
    }

    private ItemStack getFiller() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack getDropSettings(Player player){
        ItemStack item;
        ItemMeta meta;

        PlayerManagement management = new PlayerManagement(player);
        if (management.dropsEnabled()){
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eItem Drops &a&lENABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7The ability to drop items on the ground."));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &4&lDISABLE&7."));

            meta.setLore(lore);
        } else {
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eItem Drops &4&lDISABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7The ability to drop items on the ground."));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &a&lENABLE&7."));

            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getStatisticsViewing(Player player){
        ItemStack item;
        ItemMeta meta;

        PlayerManagement management = new PlayerManagement(player);
        if (management.statisticsViewingEnabled()){
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePublic Statistics &a&lENABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Having this enabled will make your"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7statistics public. People can view your"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7statistics by doing &c/stats (player)&7!"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &4&lDISABLE&7."));

            meta.setLore(lore);
        } else {
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePublic Statistics &4&lDISABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Having this enabled will make your"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7statistics public. People can view your"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7statistics by doing &c/stats (player)&7!"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &a&lENABLE&7."));

            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }


    private ItemStack getPotionsSettings(Player player){
        ItemStack item;
        ItemMeta meta;

        PlayerManagement management = new PlayerManagement(player);
        if (management.potionsEnabled()){
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePotions &a&lENABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Instead of using Soup as the normal healing,"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7you are using Potions."));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &4&lDISABLE&7."));

            meta.setLore(lore);
        } else {
            item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePotions &4&lDISABLED"));

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Instead of using Soup as the normal healing,"));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7you are using Potions."));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to &a&lENABLE&7."));

            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void settingsinvclick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(ChatColor.DARK_AQUA + "User Settings")){
            event.setCancelled(true);
            if (event.getView() == null)return;
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)return;
            ItemStack item = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();

            PlayerManagement management = new PlayerManagement(player);

            // Item Drops
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eItem Drops &a&lENABLED"))){
                management.setDropsEnabled(false);
                openSettings(player);
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eItem Drops &4&lDISABLED"))){
                management.setDropsEnabled(true);
                openSettings(player);
                return;
            }

            // Public Statistics Viewing
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&ePublic Statistics &a&lENABLED"))){
                management.setStatisticsViewingEnabled(false);
                openSettings(player);
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&ePublic Statistics &4&lDISABLED"))){
                management.setStatisticsViewingEnabled(true);
                openSettings(player);
                return;
            }

            // Potions
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&ePotions &a&lENABLED"))){
                management.setPotionsEnabled(false);
                openSettings(player);
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&ePotions &4&lDISABLED"))){
                management.setPotionsEnabled(true);
                openSettings(player);
                return;
            }
        }
    }

}
