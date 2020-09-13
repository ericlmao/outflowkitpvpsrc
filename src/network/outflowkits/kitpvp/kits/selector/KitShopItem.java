package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.kitpvp.customevents.KitPurchaseEvent;
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

public class KitShopItem implements Listener {

    @EventHandler
    public void kitshopuse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null)return;
        if (!(player.getInventory().getItemInHand().hasItemMeta()))return;
        if (!(player.getInventory().getItemInHand().getItemMeta().hasDisplayName()))return;
        if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&6Kit Shop"))){
            event.setCancelled(true);
            KitShop kitShop = new KitShop(player);
            kitShop.openShop();
        }
    }

    @EventHandler
    public void kitshopinv(InventoryClickEvent event){
        if (event.getView().getTitle().equals(ChatColor.GOLD + "Kit Shop")) {
            event.setCancelled(true);
            if (!(event.getCurrentItem().hasItemMeta())) return;
            if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().getType() == Material.BEDROCK){
                Utils.sendMessage(player, "&c&lYou already have this kit unlocked.");
                return;
            }
            PlayerManagement management = new PlayerManagement(player);
            KitShop kitShop = new KitShop(player);
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aBarbarian"))) {
                int cost = Utils.getKitPrice("Barbarian");
                if (management.hasEnough(cost)){
                    String kit = "Barbarian";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aChemist"))) {
                int cost = Utils.getKitPrice("Chemist");
                if (management.hasEnough(cost)){
                    String kit = "Chemist";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aFisherman"))) {
                int cost = Utils.getKitPrice("Fisherman");
                if (management.hasEnough(cost)){
                    String kit = "Fisherman";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aAvatar"))) {
                int cost = Utils.getKitPrice("Avatar");
                if (management.hasEnough(cost)){
                    String kit = "Avatar";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aKidnapper"))) {
                int cost = Utils.getKitPrice("Kidnapper");
                if (management.hasEnough(cost)){
                    String kit = "Kidnapper";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aMario"))) {
                int cost = Utils.getKitPrice("Mario");
                if (management.hasEnough(cost)){
                    String kit = "Mario";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aKangaroo"))) {
                int cost = Utils.getKitPrice("Kangaroo");
                if (management.hasEnough(cost)){
                    String kit = "Kangaroo";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aNinja"))) {
                int cost = Utils.getKitPrice("Kangaroo");
                if (management.hasEnough(cost)){
                    String kit = "Ninja";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aStomper"))) {
                int cost = Utils.getKitPrice("Stomper");
                if (management.hasEnough(cost)){
                    String kit = "Stomper";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aDwarf"))) {
                int cost = Utils.getKitPrice("Dwarf");
                if (management.hasEnough(cost)){
                    String kit = "Dwarf";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aTeleporter"))) {
                int cost = Utils.getKitPrice("Teleporter");
                if (management.hasEnough(cost)){
                    String kit = "Teleporter";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aSwitcher"))) {
                int cost = Utils.getKitPrice("Switcher");
                if (management.hasEnough(cost)){
                    String kit = "Switcher";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aGank"))) {
                int cost = Utils.getKitPrice("Gank");
                if (management.hasEnough(cost)){
                    String kit = "Gank";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aTank"))) {
                int cost = Utils.getKitPrice("Tank");
                if (management.hasEnough(cost)){
                    String kit = "Tank";
                    Bukkit.getPluginManager().callEvent(new KitPurchaseEvent(player, kit, cost));
                    kitShop.openShop();
                } else {
                    Utils.sendMessage(player, "&c&lYou do not have enough to purchase this kit.");
                }
                return;
            }
        }
    }
}
