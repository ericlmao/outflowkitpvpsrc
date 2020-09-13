package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Teleporter implements Listener {
    private KitPvP plugin;
    public Teleporter(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    public static void getKit(Player player){

        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getPearls());

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.updateInventory();
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }
    private static ItemStack getPearls() {
        ItemStack item = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lEnder Pearl Dispenser"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to throw an Ender Pearl!"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getHelmet() { return new ItemStack(Material.CHAINMAIL_HELMET); }

    private static ItemStack getChestplate() {
        return new ItemStack(Material.IRON_CHESTPLATE);
    }

    private static ItemStack getLeggings() {
        return new ItemStack(Material.IRON_LEGGINGS);
    }

    private static ItemStack getBoots() {
        return new ItemStack(Material.CHAINMAIL_BOOTS);
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Teleporter";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5Teleporter"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7I'm over there! Oh, wait. I'm here!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Helmet"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Chestplate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Boots"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &5&lEnder Pearl Dispenser"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Right-Click to throw an Ender Pearl"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void dispensePearl(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
            if (event.getItem().getType() == Material.ENDER_PORTAL_FRAME){
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equals("Teleporter")){
                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                    if (!Utils.canUseAbility(event.getPlayer())){
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.enderpearl_cooldown.containsKey(event.getPlayer())){
                        double time = plugin.enderpearl_cooldown.get(event.getPlayer());
                        DecimalFormat df = new DecimalFormat("###,###.#");
                        Utils.sendMessage(event.getPlayer(), "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    event.getPlayer().launchProjectile(EnderPearl.class);
                    Utils.playSound(event.getPlayer(), Sound.SHOOT_ARROW);
                }
            }
        }
    }
}
