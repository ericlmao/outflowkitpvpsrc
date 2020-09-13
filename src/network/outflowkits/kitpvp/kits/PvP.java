package network.outflowkits.kitpvp.kits;

import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PvP {
    
    public static void getKit(Player player){

        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 180000, 0));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "PvP";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aPvP"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The default kit!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Helmet"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Chestplate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Boots"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &8None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
