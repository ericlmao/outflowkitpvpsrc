package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Dwarf implements Listener {
    private KitPvP plugin;
    private ArrayList<Player> dwarfCharge = new ArrayList<>();
    public Dwarf() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player) {
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getAbilityItem());

        player.updateInventory();
    }
    // Ability item / Weapon
    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.GOLD_AXE);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDwarf's Trusty Axe"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7When you attack a player, you have a chance"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7to do double damage!"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.GOLD_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.GOLD_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.GOLD_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    @EventHandler
    public void ability(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player){
            if (event.getEntity() instanceof Player){
                Player attacker = (Player) event.getDamager();

                if (attacker.getInventory().getItemInHand().getType() == Material.GOLD_AXE){
                    PlayerManagement attackerManagement = new PlayerManagement(attacker);
                    if (attackerManagement.getKit().equals("Dwarf")) {
                        Random random = new Random();
                        int chance = random.nextInt(100);
                        if (chance <= 2){
                            event.setDamage(event.getDamage() * 2);
                            Utils.playSound(attacker, Sound.SUCCESSFUL_HIT);
                        }
                    }
                }
            }
        }
    }

    private void yeetPlayer(Player attacker, Player victim) {
        Vector vector = attacker.getLocation().getDirection().multiply(8).setY(7);
        victim.setVelocity(vector);
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.GOLD_AXE);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Dwarf";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dwarf"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7I'm gonna yeet this guy outta my sight"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Helmet (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Chestplate (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Leggings (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Boots (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Axe (Sharpness"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &6&lDwarf YEET"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Shift up to 5 seconds to be able to use &6&lDwarf YEET"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7on the next person you attack."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7This ability will send the absolutely flying! And will also"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7double the initial damage."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
