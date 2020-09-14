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

public class Tank {


    public static void getKit(Player player){

        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 180000, 0));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getHelmet() { return new ItemStack(Material.DIAMOND_HELMET); }

    private static ItemStack getChestplate() {
        return new ItemStack(Material.DIAMOND_CHESTPLATE);
    }

    private static ItemStack getLeggings() {
        return new ItemStack(Material.DIAMOND_LEGGINGS);
    }

    private static ItemStack getBoots() {
        return new ItemStack(Material.DIAMOND_BOOTS);
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Tank";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Tank"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7I am tanky boi"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Helmet"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Chestplate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Leggings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Boots"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Stone Sword"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Slowness 1"));
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
