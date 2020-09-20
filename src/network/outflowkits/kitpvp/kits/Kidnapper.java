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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Kidnapper implements Listener {
    private KitPvP plugin;

    public HashMap<Player, Location> prelocation = new HashMap<>();
    public ArrayList<UUID> snowballs = new ArrayList<>();

    public Kidnapper() {
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
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 8);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lHandcuffs"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to activate the Handcuffs Ability!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&cHandcuffs &7will lock you and players near you"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7into a box. You will be applied with Strength 2 for"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&710 seconds."));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.PURPLE);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.PURPLE);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.PURPLE);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.PURPLE);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void ability(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.INK_SACK) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Kidnapper")) {
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.gulagplayers.containsKey(event.getPlayer())){
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use this Ability while in the &4&lGulag&7&l.");
                        event.setCancelled(true);
                        return;
                    }
                    if (snowballs.contains(event.getPlayer().getUniqueId())) {
                        Utils.sendMessage(event.getPlayer(), "&c&lYou already have a Snow Ball in the air!");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        event.setCancelled(true);
                        return;
                    }
                    CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                    if (cooldowns.hasCooldown("Kidnapper")){
                        long cooldown = cooldowns.getCooldown("Kidnapper");
                        Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    kidnapbox(event.getPlayer());
                }
            }
        }
    }

    private void kidnapbox(Player player) {
        player.launchProjectile(Snowball.class);
        snowballs.add(player.getUniqueId());
    }

    @EventHandler
    public void land(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player player = (Player) event.getEntity().getShooter();
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Kidnapper")) {
                    snowballs.remove(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            if (event.getEntity() instanceof Player) {
                Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getShooter() instanceof Player) {
                    Player shooter = (Player) projectile.getShooter();
                    PlayerManagement management = new PlayerManagement(shooter);
                    if (management.getKit().equals("Kidnapper")) {
                        Player victim = (Player) event.getEntity();
                        sendtogulag(shooter, victim);

                        CooldownManagement cooldowns = new CooldownManagement(shooter);
                        cooldowns.setCooldown("Kidnapper", 80);
                    }
                }
            }
        }
    }

    private void sendtogulag(Player shooter, Player victim) {
        Location shooterLocation = shooter.getLocation();
        Location victimLocation = victim.getLocation();

        Location gulagLocationforShooter = new Location(Bukkit.getWorld("Kit"), -273.5, 83, 595.5, 45, 0);
        Location gulagLocationforVictim = new Location(Bukkit.getWorld("Kit"), -289.5, 83, 612.5, -135, 0);

        shooter.teleport(gulagLocationforShooter);
        victim.teleport(gulagLocationforVictim);

        Utils.playSound(shooter, Sound.WITHER_SPAWN);
        Utils.playSound(victim, Sound.WITHER_SPAWN);

        prelocation.put(shooter, shooterLocation);
        prelocation.put(victim, victimLocation);

        plugin.gulagplayers.put(shooter, victim);
        plugin.gulagplayers.put(victim, shooter);

        for (Player all : Bukkit.getOnlinePlayers()){
            all.hidePlayer(shooter);
            all.hidePlayer(victim);
        }

        shooter.showPlayer(victim);
        victim.showPlayer(shooter);

        // Shooter message
        Utils.sendMessage(shooter, "&7");
        Utils.sendMessage(shooter, "&8&l>> &4&lWELCOME TO THE GULAG &8&l<<");
        Utils.sendMessage(shooter, "&7You have been applied with &4Strength&7, you have 10 seconds to kill &c" + victim.getName() +
                " &7before you are teleported back into the Arena.");
        Utils.sendMessage(shooter, "&7");
        Utils.sendMessage(shooter, "&7Good luck!");
        Utils.sendMessage(shooter, " &7");

        // Victim message
        Utils.sendMessage(victim, "&7");
        Utils.sendMessage(victim, "&4&lWELCOME TO THE GULAG");
        Utils.sendMessage(victim, "&c" + shooter.getName() + " &7has been applied with &4Strength&7. Your main goal is to survive!");
        Utils.sendMessage(victim, "&7");
        Utils.sendMessage(victim, "&7Good luck!");
        Utils.sendMessage(victim, "&7");

        shooter.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (plugin.gulagplayers.containsKey(shooter)){
                shooter.teleport(shooterLocation);
                plugin.gulagplayers.remove(shooter);
                prelocation.remove(shooter);

                for (Player all : Bukkit.getOnlinePlayers()){
                    all.showPlayer(shooter);
                }

                Utils.sendMessage(shooter, "&7");
                Utils.sendMessage(shooter, "&4&lYOU HAVE SURVIVED THE GULAG");
                Utils.sendMessage(shooter, "&7You have survived the Gulag and have failed to kill your enemy.");
                Utils.sendMessage(shooter, "&7");
            }
            if (plugin.gulagplayers.containsKey(victim)){
                victim.teleport(victimLocation);
                plugin.gulagplayers.remove(victim);
                prelocation.remove(victim);

                for (Player all : Bukkit.getOnlinePlayers()){
                    all.showPlayer(victim);
                }

                Utils.sendMessage(victim, "&7");
                Utils.sendMessage(victim, "&4&lYOU HAVE SURVIVED THE GULAG");
                Utils.sendMessage(victim, "&7You have successfully survived the Gulag! Congrats!");
                Utils.sendMessage(victim, "&7");
            }
        }, 20 * 10);
    }

    @EventHandler
    public void deathingulag(PlayerDeathEvent event){
        Player victim = event.getEntity();

        if (plugin.gulagplayers.containsKey(victim)){
            Player gulagEnemy = plugin.gulagplayers.get(victim);

            Utils.sendMessage(gulagEnemy, " ");
            Utils.sendMessage(gulagEnemy, "&4&lTHE GULAG");
            Utils.sendMessage(gulagEnemy, "&7You have defeated &c" + victim.getName() + " &7in the Gulag!");
            Utils.sendMessage(gulagEnemy, " ");

            gulagEnemy.teleport(prelocation.get(gulagEnemy));
            prelocation.remove(gulagEnemy);
            prelocation.remove(victim);

            if (gulagEnemy.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                gulagEnemy.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }

            for (Player all : Bukkit.getOnlinePlayers()){
                all.showPlayer(gulagEnemy);
                all.showPlayer(victim);
            }

            Utils.sendMessage(victim, " ");
            Utils.sendMessage(victim, "&4&lTHE GULAG");
            Utils.sendMessage(victim, "&7You have been defeated by &c" + gulagEnemy.getName() + " &7in the Gulag!");
            Utils.sendMessage(victim, " ");

            plugin.gulagplayers.remove(gulagEnemy);
            plugin.gulagplayers.remove(victim);
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event){
        Player player = event.getPlayer();

        for(Player gulagPlayer : plugin.gulagplayers.values()){
            player.hidePlayer(gulagPlayer);
        }
    }

    @EventHandler
    public void leaveingulag(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (plugin.gulagplayers.containsKey(player)){
            Player enemy = plugin.gulagplayers.get(player);

            Utils.sendMessage(enemy, "&7");
            Utils.sendMessage(enemy, "&4&lTHE GULAG");
            Utils.sendMessage(enemy, "&c" + player.getName() + " &7has left while in the Gulag.");
            Utils.sendMessage(enemy, "&7");

            enemy.teleport(prelocation.get(enemy));
            plugin.gulagplayers.remove(player);
            plugin.gulagplayers.remove(enemy);
            prelocation.remove(player);
            prelocation.remove(enemy);

            if (enemy.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                enemy.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
        }
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Kidnapper";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cKidnapper"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7My kid is taking a nap..."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Helmet (Protection 1, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Chestplate (Protection 1, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Leggings (Protection 1, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Boots (Protection 1, Unbreaking 10)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &8&lHandcuffs"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7On activation, you and the player you hit with"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7your Handcuffs, will be teleported to the &4&lGULAG&7!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7You will be applied with Strength, and you will have"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &c10 seconds &7to kill your enemy!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
