package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class Switcher implements Listener {
    private KitPvP plugin;
    public Switcher(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public ArrayList<UUID> snowballs = new ArrayList<>();
    public static void getKit(Player player){

        Utils.giveHealing(player);

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getAbilityItem());

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.updateInventory();
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getAbilityItem() {
        return new ItemStack(Material.SNOW_BALL, 1);
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.BLACK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.BLACK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.BLACK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        return item;
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.SNOW_BALL);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Switcher";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3Switcher"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7I'm here, I'm there, I'm everywhere!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Helmet (Protection 1, Unbreaking 4)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Chestplate (Protection 3, Unbreaking 20)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Leggings (Protection 3, Unbreaking 20)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Boots (Protection 3, Unbreaking 20)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &6&lSwitcher"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7When activated, it will simply just switch"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7you with the target that you hit."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void switcherthrow(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if (event.getItem() == null || event.getItem().getType() == Material.AIR)return;

            if (event.getItem().getType() == Material.SNOW_BALL) {
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Switcher")) {
                    event.setCancelled(true);
                    player.updateInventory();
                    if (!Utils.canUseAbility(event.getPlayer())) {
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                    if (cooldowns.hasCooldown("Switcher")) {
                        long cooldown = cooldowns.getCooldown("Switcher");
                        Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    if (snowballs.contains(player.getUniqueId())) {
                        Utils.sendMessage(event.getPlayer(), "&c&lYou already have a Switcher Ball in the air!");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        event.setCancelled(true);
                        return;
                    }
                    player.updateInventory();
                    player.launchProjectile(Snowball.class);
                    snowballs.add(player.getUniqueId());
                }
            }
        }
    }
    @EventHandler
    public void land(ProjectileHitEvent event){
        Projectile projectile = event.getEntity();
        if (projectile instanceof Snowball){
            Player shooter = (Player) projectile.getShooter();

            PlayerManagement management = new PlayerManagement(shooter);
            if (management.getKit().equals("Switcher")) {
                if (snowballs.contains(shooter.getUniqueId())){
                    snowballs.remove(shooter.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void switcherdamage(EntityDamageByEntityEvent event){
        new BukkitRunnable(){

            @Override
            public void run() {
                if (event.isCancelled())return;
                if (event.getEntity() instanceof Player) {
                    if (event.getDamager() instanceof Snowball) {
                        Player shooter = (Player) ((Snowball) event.getDamager()).getShooter();
                        Player victim = (Player) event.getEntity();
                        PlayerManagement shooterManagement = new PlayerManagement(shooter);

                        if (shooterManagement.getKit().equals("Switcher")) {
                            Location vLoc = victim.getLocation();
                            Location sLoc = shooter.getLocation();

                            victim.damage(8);

                            shooter.teleport(vLoc);
                            victim.teleport(sLoc);

                            Utils.sendMessage(shooter, "&aYou have &6SWITCHED &awith &6" + victim.getName() + "&a.");
                            Utils.sendMessage(victim, "&aYou have &6SWITCHED &awith &6" + shooter.getName() + "&a.");

                            CooldownManagement cooldowns = new CooldownManagement(shooter);
                            cooldowns.setCooldown("Switcher", 30);
                        }
                    }
                }
            }
        }.runTaskLater(plugin, 1);
    }
}
