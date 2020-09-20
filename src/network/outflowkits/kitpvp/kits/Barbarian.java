package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Barbarian implements Listener {
    private KitPvP plugin;
    public Barbarian(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player){
        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getAbilityItem());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 180000, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 180000, 0));
    }

    private static ItemStack getSword(){
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        return item;
    }

    private static ItemStack getAbilityItem(){
        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lBarbarian's Force"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to apply yourself with Strength 1 for 3 seconds."));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void ability(PlayerInteractEvent event){
        if (event.getItem() == null)return;
        if (event.getItem().getType() == Material.INK_SACK){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Barbarian")){
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                    if (cooldowns.hasCooldown("Barbarian")){
                        long cooldown = cooldowns.getCooldown("Barbarian");
                        Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    cooldowns.setCooldown("Barbarian", 40);
                    barbarianStrength(event.getPlayer());
                }
            }
        }
    }

    private void barbarianStrength(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 3, 0));
        Utils.playSound(player.getLocation(), Sound.ZOMBIE_PIG_ANGRY);

        Collection<Entity> entities = player.getNearbyEntities(10, 10, 10);
        for (Entity entity : entities){
            if (entity instanceof Player){
                Player p = (Player) entity;
                Utils.sendMessage(p, "&4&lWARNING: &4" + player.getName() +
                        " &chas activated &4&lBarbarian's Force &cand has &4Strength 1&c! Watch out for them.");
            }
        }
    }


    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.STONE_AXE);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Barbarian";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bBarbarian"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The pure might of my blade will end your life."));
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
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Stone Sword (Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Resistance 3"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &4&lBarbarian's Force"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7On Right-Click, will apply you with Strength 1 for"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &73 seconds."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
