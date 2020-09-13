package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Avatar implements Listener {
    private KitPvP plugin;
    public Avatar(){
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

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 1));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 6);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAvatar's Water Gun"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to activate the Water Gun Ability!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bWater Gun &7will apply players around you with"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Slowness 5, for 10 seconds!"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        return new ItemStack(Material.DIAMOND_HELMET);
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        return item;
    }

    private static ItemStack getBoots() {
        return new ItemStack(Material.DIAMOND_BOOTS);
    }

    @EventHandler
    public void ability(PlayerInteractEvent event){
        if (event.getItem() == null)return;
        if (event.getItem().getType() == Material.INK_SACK){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Avatar")){
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.avatar_cooldown.containsKey(event.getPlayer())){
                        double time = plugin.avatar_cooldown.get(event.getPlayer());
                        DecimalFormat df = new DecimalFormat("###,###.#");
                        Utils.sendMessage(event.getPlayer(), "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    plugin.avatar_cooldown.put(event.getPlayer(), 25.0);
                    shootBullet(event.getPlayer());
                }
            }
        }
    }

    private void shootBullet(Player player) {
        player.launchProjectile(Snowball.class);
    }
    @EventHandler
    public void launch(ProjectileLaunchEvent event){
        Projectile projectile = event.getEntity();
        Player shooter = (Player) projectile.getShooter();

        PlayerManagement management = new PlayerManagement(shooter);

        if (management.getKit().equals("Avatar")){
            projectile.setCustomName(ChatColor.AQUA + "Water Bullet");
        }
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                Player shooter = (Player) projectile.getShooter();
                Player victim = (Player) event.getEntity();
                PlayerManagement management = new PlayerManagement(shooter);
                if (!Utils.canUseAbility(shooter)){
                    event.setCancelled(true);
                    Utils.sendMessage(shooter, "&c&lYou cannot use abilities while in a protected area");
                    Utils.playSound(shooter, Sound.VILLAGER_NO);
                    return;
                }
                if (management.getKit().equals("Avatar")) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 4));
                    event.setDamage(5.5);

                    Utils.sendMessage(shooter, "&aYou have shot &e" + victim.getName() + " &awith &b&lWater Gun&a!");
                    Utils.sendMessage(victim, "&cYou have been shot by &e" + shooter.getName() + " &clusing &b&lWater Gun&c!");
                }
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            PlayerManagement management = new PlayerManagement(player);
            if (management.getKit().equals("Avatar")){
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
                    event.setCancelled(true);
                }
            }
        }
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Avatar";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Avatar"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The last airbender?"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Helmet"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Chestplate (Protection 4, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Leggings (Protection 4, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Boots"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 2"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &b&lWater Gun"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7You can shoot people with the &bWater Gun"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7to give them slowness!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
