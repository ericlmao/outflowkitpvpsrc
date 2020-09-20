package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.CooldownManagement;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Stomper implements Listener {
    private KitPvP plugin;
    private ArrayList<Player> stomperCharge = new ArrayList<>();
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
        player.getInventory().setItem(1, getAbilityItem());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 180000, 0));
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lStomper"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to get launched in the air!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7While in the air, Shift and Right Click to be"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7sent back down to inflict damage on the players near"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7you."));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        return item;
    }

    private static ItemStack getHelmet() {
        return new ItemStack(Material.DIAMOND_HELMET);
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
    public void ability(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.ANVIL) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (!(stomperCharge.contains(event.getPlayer()))){
                    PlayerManagement management = new PlayerManagement(event.getPlayer());
                    if (management.getKit().equalsIgnoreCase("Stomper")) {
                        event.setCancelled(true);
                        event.getPlayer().updateInventory();
                        if (!Utils.canUseAbility(event.getPlayer())){
                            event.setCancelled(true);
                            Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                            Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                            return;
                        }
                        CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                        if (cooldowns.hasCooldown("Stomper")){
                            long cooldown = cooldowns.getCooldown("Stomper");
                            Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                            event.setCancelled(true);
                            return;
                        }
                        Location ploc = event.getPlayer().getLocation();
                        for (int i = 0; i < 10; i++) {
                            Location loc = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() + i, ploc.getZ());

                            if (loc.getBlock().getType() != Material.AIR){
                                Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use Stomper under blocks!");
                                return;
                            }
                        }
                        activateStomp(event.getPlayer());
                        return;
                    }
                }
                if (event.getPlayer().isSneaking()){
                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                    event.getPlayer().setVelocity(new Vector(0, -3.5, 0));
                } else {
                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                    Utils.sendMessage(event.getPlayer(), "&cYou must be shifting in order to Stomp effectively!");
                }
            }
        }
    }
    @EventHandler
    public void abilitybutinteract(PlayerInteractAtEntityEvent event){
        if (event.getPlayer().getInventory().getItemInHand() == null) return;
        if (event.getPlayer().getInventory().getItemInHand().getType() == Material.ANVIL) {
            if (!(stomperCharge.contains(event.getPlayer()))) {
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Stomper")) {
                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    CooldownManagement cooldowns = new CooldownManagement(event.getPlayer());
                    if (cooldowns.hasCooldown("Stomper")){
                        long cooldown = cooldowns.getCooldown("Stomper");
                        Utils.sendMessage(event.getPlayer(), "&8[&9Ability&8] &7Please wait &9" + cooldowns.formatCooldown(cooldown) + " &7before doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    activateStomp(event.getPlayer());
                    return;
                }
            }
            if (event.getPlayer().isSneaking()) {
                event.setCancelled(true);
                event.getPlayer().updateInventory();
                event.getPlayer().setVelocity(new Vector(0, -3.5, 0));
            } else {
                event.setCancelled(true);
                event.getPlayer().updateInventory();
                Utils.sendMessage(event.getPlayer(), "&cYou must be shifting in order to Stomp effectively!");
            }
        }
    }

    private void activateStomp(Player player) {
        player.setVelocity(new Vector(0, 3.8, 0));
        stomperCharge.add(player);
    }

    @EventHandler
    public void falldamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (stomperCharge.contains(player)){
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
                    Collection<Entity> sorroundingEntities = player.getNearbyEntities(4, 4, 4);
                    for(Entity e : sorroundingEntities){
                        if (e instanceof Player){
                            Player p = (Player) e;
                            p.damage(10);
                            p.setVelocity(new Vector(0, 1, 0));
                        }
                    }
                    Utils.playSound(player.getLocation(), Sound.EXPLODE);
                    stomperCharge.remove(player);

                    CooldownManagement cooldowns = new CooldownManagement(player);
                    cooldowns.setCooldown("Stomper", 30);
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
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &9&lStomper"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7On Right-Click, you will be blasted into the air!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7To inflict damage by ''Stomping'', you must Shift and"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Right Click while in the air to STOMP on the heads of"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7the players near you."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
