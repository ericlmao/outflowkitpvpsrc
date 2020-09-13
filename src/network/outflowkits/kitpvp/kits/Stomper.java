package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Stomper implements Listener {
    private KitPvP plugin;
    public Stomper() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player) {
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getSword());

        player.updateInventory();
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    @EventHandler
    public void ability(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            PlayerManagement management = new PlayerManagement(player);
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (management.getKit().equals("Stomper")){
                    if (!Utils.canUseAbility(player))return;

                    Utils.playSound(player.getLocation(), Sound.EXPLODE);

                    Collection<Entity> entities = player.getNearbyEntities(5,5,5);
                    Random random = new Random();
                    int rand = random.nextInt(10);
                    for (Entity entity : entities){
                        if (entity instanceof Player){
                            Player p = (Player) entity;
                            p.damage(rand + 1);
                            Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(player, p, EntityDamageEvent.DamageCause.ENTITY_ATTACK, rand + 1));
                        }
                    }
                }
            }
        }
    }


    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Stomper";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Stomper"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Stomp stomp stomp stomp stomp stomp stomp"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Helmet (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Chestplate (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Boots (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Sword"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &9&lStomper"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7When you take fall damage, you will damage"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7nearby players and stun them for a few seconds."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
