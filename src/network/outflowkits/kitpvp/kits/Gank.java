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

public class Gank {

    public static void getKit(Player player){

        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 180000, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 180000, 2));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Gank";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Gank"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The gankiest of ganks"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Stone Sword (Sharpness 4)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 2"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Resistance 3"));
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
