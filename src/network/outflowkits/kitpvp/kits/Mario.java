package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Mario implements Listener {
    private KitPvP plugin;
    public Mario() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player) {
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getAbilityItem());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 1800000, 0));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.RED_MUSHROOM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lSuper Mushroom"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to activate &c&lSuper Mushroom&7!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&c&lSuper Mushroom&7, when activated, will give you"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7God-like powers! Applying you with Health Boost, and"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Regeneration! It will be hard to die."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.RED);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getChestplate() {
        return new ItemStack(Material.DIAMOND_CHESTPLATE);
    }

    private static ItemStack getLeggings() {
        return new ItemStack(Material.IRON_LEGGINGS);
    }

    private static ItemStack getBoots() {
        return new ItemStack(Material.IRON_BOOTS);
    }

    @EventHandler
    public void ability(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.RED_MUSHROOM) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Mario")) {
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.mario_cooldown.containsKey(event.getPlayer())){
                        double time = plugin.mario_cooldown.get(event.getPlayer());
                        DecimalFormat df = new DecimalFormat("###,###.#");
                        Utils.sendMessage(event.getPlayer(), "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    event.setCancelled(true);
                    plugin.mario_cooldown.put(event.getPlayer(), 60.0);
                    applysupermushroom(event.getPlayer());
                }
            }
        }
    }

    private void applysupermushroom(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 15, 4));
        player.setHealth(40);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 2));

        player.removePotionEffect(PotionEffectType.SPEED);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
        new BukkitRunnable(){

            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 0));
            }
        }.runTaskLater(plugin, 20 * 10 + 1);

        Utils.playSound(player.getLocation(), Sound.EAT);
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.FIREBALL);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Mario";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Mario"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7How many games are there?! Holy..."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Helmet (Protection 1, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Chestplate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Boots"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Fire Resistance"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &c&lSuper Mushroom"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7When activated, will give you"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7God-like powers! Applying you with Health Boost, and"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Regeneration! It will be hard to die."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
