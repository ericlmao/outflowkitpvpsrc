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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Ninja implements Listener {
    private KitPvP plugin;
    public Ninja(){
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
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lNinja Swerve"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to activate the Ninja Swerve Ability!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&cNinja Swerve &7will teleport you behind the player nearest"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7to you and apply you with Speed 3 for 5 seconds."));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.BLACK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        return item;
    }

    private static ItemStack getChestplate() {
        return new ItemStack(Material.IRON_CHESTPLATE);
    }

    private static ItemStack getLeggings() {
        return new ItemStack(Material.IRON_LEGGINGS);
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.BLACK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 15);
        return item;
    }

    @EventHandler
    public void ability(PlayerInteractEvent event){
        if (event.getItem() == null)return;
        if (event.getItem().getType() == Material.NETHER_STAR){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Ninja")){
                    event.setCancelled(true);
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.ninja_cooldown.containsKey(event.getPlayer())){
                        double time = plugin.ninja_cooldown.get(event.getPlayer());
                        DecimalFormat df = new DecimalFormat("###,###.#");
                        Utils.sendMessage(event.getPlayer(), "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    teleportBehindNearPlayers(event.getPlayer());
                }
            }
        }
    }

    private void teleportBehindNearPlayers(Player player) {
        Collection<Entity> sorroundingEntities = player.getNearbyEntities(6, 6, 6);
        for(Entity e : sorroundingEntities){
            if (e instanceof Player) {
                plugin.ninja_cooldown.put(player, 35.0);
                Player p = (Player) e;
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    double newX;
                    double newZ;
                    float nang = p.getLocation().getYaw() + 90;

                    if (nang < 0) nang += 360;

                    newX = Math.cos(Math.toRadians(nang));
                    newZ = Math.sin(Math.toRadians(nang));

                    Location newDamagerLoc = new Location(p.getLocation().getWorld(), p.getLocation().getX() - newX,
                            p.getLocation().getY(), p.getLocation().getZ() - newZ, p.getLocation().getYaw(), p.getLocation().getPitch());

                    player.teleport(newDamagerLoc);

                    player.removePotionEffect(PotionEffectType.SPEED);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 2));
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            player.removePotionEffect(PotionEffectType.SPEED);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 0));
                        }
                    }.runTaskLater(plugin, 20 * 5 + 1);
                    break;
                }
            }
        }
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Ninja";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cNinja"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Is this Ninjago?!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Helmet (Protection 3, Unbreaking 15)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Chestplate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Iron Leggings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Leather Boots (Protection 3, Unbreaking 15)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Diamond Sword (Sharpness 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &c&lNinja Swerve"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7On Right-Click, will teleport you behind the nearest"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7player to you, and will apply you with Speed 3 for 5 seconds."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
