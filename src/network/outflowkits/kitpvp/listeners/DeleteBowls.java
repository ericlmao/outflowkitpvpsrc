package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DeleteBowls implements Listener {
    private KitPvP plugin;
    public DeleteBowls(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void dropitem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        Material type = item.getItemStack().getType();
        switch (type){
            case SNOW_BALL:{
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Switcher")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case BOWL:{
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    item.remove();
                    player.updateInventory();
                }, 10);
                return;
            }
            case MUSHROOM_SOUP:
            case POTION:{
                PlayerManagement management = new PlayerManagement(player);
                if (!Utils.canUseAbility(player)){
                    event.setCancelled(true);
                    return;
                }
                if (management.dropsEnabled()) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        item.remove();
                        player.updateInventory();
                    }, 20 * 10);
                } else {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
                return;
            }
            case SUGAR:{
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Archer")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case ANVIL:{
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Stomper")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case ENDER_PORTAL_FRAME:{
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Teleporter")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case INK_SACK:{
                PlayerManagement management = new PlayerManagement(player);
                switch (management.getKit().toLowerCase()){
                    case "barbarian":
                    case "kidnapper":
                    case "avatar": {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }
                }
            }
            case FIREWORK_CHARGE: {
                PlayerManagement management = new PlayerManagement(player);
                switch (management.getKit().toLowerCase()) {
                    case "chemist": {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }
                }
            }
            case FIREWORK:{
                PlayerManagement management = new PlayerManagement(player);
                switch (management.getKit().toLowerCase()) {
                    case "kangaroo": {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }
                }
            }
            case COMPASS:{
                if (item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Kit Selector")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case FIREBALL:{
                PlayerManagement management = new PlayerManagement(player);
                switch (management.getKit().toLowerCase()) {
                    case "mario": {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }
                }
            }
            case CHEST:{
                if (item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit Shop")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case WATCH:{
                if (item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "User Settings")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            case NETHER_STAR:{
                PlayerManagement management = new PlayerManagement(player);
                switch (management.getKit().toLowerCase()) {
                    case "ninja": {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }
                }
                if (item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Select Recent Kit")){
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
            // Weapons
            case STONE_SWORD:
            case DIAMOND_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
            case DIAMOND_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOOD_AXE:
            case BOW:
            case ARROW:
            case ENDER_PEARL:
            case FISHING_ROD:{
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
            // Helmets
            case DIAMOND_HELMET:
            case CHAINMAIL_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case LEATHER_HELMET:{
                player.getInventory().setHelmet(item.getItemStack());
                player.updateInventory();
                item.remove();
                return;
            }
            // Chestplates
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case LEATHER_CHESTPLATE:{
                player.getInventory().setChestplate(item.getItemStack());
                player.updateInventory();
                item.remove();
                return;
            }
            // Leggings
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:{
                player.getInventory().setLeggings(item.getItemStack());
                player.updateInventory();
                item.remove();
                return;
            }
            // Boots
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case LEATHER_BOOTS:{
                player.getInventory().setBoots(item.getItemStack());
                player.updateInventory();
                item.remove();
            }
        }
    }
}
