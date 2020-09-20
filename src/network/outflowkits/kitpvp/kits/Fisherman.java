package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Fisherman implements Listener {
    private KitPvP plugin;
    public Fisherman(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player){
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getAbilityItem());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 0));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.GOLD_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.GOLD_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        return item;
    }

    @EventHandler
    public void ability(PlayerFishEvent event){
        Player player = event.getPlayer();
        PlayerManagement management = new PlayerManagement(player);
        if (management.getKit().equals("Fisherman")){
            if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY){
                if (!Utils.canUseAbility(event.getPlayer())){
                    event.setCancelled(true);
                    Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                    Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                    return;
                }
                CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                if (cooldowns.hasCooldown("Fisherman")){
                    long cooldown = cooldowns.getCooldown("Fisherman");
                    Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                    event.setCancelled(true);
                    return;
                }

                Entity caught = event.getCaught();
                if (caught instanceof Player){
                    Player victim = (Player) caught;

                    victim.teleport(player.getLocation());

                    cooldowns.setCooldown("Fisherman", 15);
                }
            }
        }
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Fisherman";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eFisherman"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7''I caught something..?''"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Golden Helmet (Protection 2, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Chestplate (Protection 3, Unbreaking 2)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings (Protection 3, Unbreaking 2)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Golden Boots (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &a&lFisherman's Reel"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7You can fish people up by getting them caught"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7in your hook! Will teleport them to your location."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
