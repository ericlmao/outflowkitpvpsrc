package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Chemist implements Listener {
    private KitPvP plugin;
    public Chemist(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player){
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getSword());
        player.getInventory().setItem(1, getPotion1());
        player.getInventory().setItem(2, getPotion2());
        player.getInventory().setItem(3, getAbilityItem());

        player.updateInventory();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1800000, 0));
    }


    private static ItemStack getSword() {
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        return item;
    }

    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_CHARGE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&lSmoke Bomb"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right-Click to activate the Smoke Bomb Ability!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8Smoke Bomb &7will apply players around you with"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Slowness, Nausea, and Blindness, for 15 seconds!"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getPotion1() {
        ItemStack item = new ItemStack(Material.POTION, 4, (short)16460);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lInstant Damage Potion &7(Splash)"));
        item.setItemMeta(meta);
        return item;
    }
    private static ItemStack getPotion2() {
        ItemStack item = new ItemStack(Material.POTION, 2, (short)16388);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lPoison Potion &7(Splash)"));
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        return item;
    }
    @EventHandler
    public void launchpots(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null)return;
        ItemStack item = event.getItem();
        if (item.getType() == Material.POTION){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKit().equals("Chemist")){
                    if (!Utils.canUseAbility(player)){
                        event.setCancelled(true);
                        player.updateInventory();
                        Utils.sendMessage(player, "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(player, Sound.VILLAGER_NO);
                    }
                }
            }
        }
    }
    @EventHandler
    public void ability(PlayerInteractEvent event){
        if (event.getItem() == null)return;
        if (event.getItem().getType() == Material.FIREWORK_CHARGE){
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                PlayerManagement management = new PlayerManagement(event.getPlayer());
                if (management.getKit().equalsIgnoreCase("Chemist")) {
                    if (!Utils.canUseAbility(event.getPlayer())){
                        event.setCancelled(true);
                        Utils.sendMessage(event.getPlayer(), "&c&lYou cannot use abilities while in a protected area");
                        Utils.playSound(event.getPlayer(), Sound.VILLAGER_NO);
                        return;
                    }
                    if (plugin.chemist_cooldown.containsKey(event.getPlayer())){
                        double time = plugin.chemist_cooldown.get(event.getPlayer());
                        DecimalFormat df = new DecimalFormat("###,###.#");
                        Utils.sendMessage(event.getPlayer(), "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                        event.setCancelled(true);
                        return;
                    }
                    smokeBombActivate(event.getPlayer());
                    plugin.chemist_cooldown.put(event.getPlayer(), 30.0);
                }
            }
        }
    }

    private void smokeBombActivate(Player player) {
        List<Entity> entities = player.getNearbyEntities(5, 5, 5);
        if (entities.isEmpty()){
            return;
        }
        ArrayList<String> playersHit = new ArrayList<>();
        for(Entity entity : entities){
            if (entity instanceof Player){
                Player p = (Player) entity;
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 16, 3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 8, 3));

                    Utils.sendMessage(p, "&c&l" + player.getName() + " &7has hit you with a &8&lSmoke Bomb&7!");
                    playersHit.add(p.getName());
                }
            }
        }

        String message = "&7You have hit &c&l%players% &7with &8&lSmoke Bomb&7&l!";
        if (playersHit.size() == 1) {
            message = message.replaceAll("%players%", playersHit.get(0));
            Utils.sendMessage(player, message);
            return;
        }
        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 0; i < playersHit.size(); ++i) {
            msgBuilder.append(playersHit.get(i)).append("&7, &c&l");
        }

        message = message.replaceAll("%players%", msgBuilder.toString().trim());

        Utils.sendMessage(player, message);

    }

    @EventHandler
    public void chemistKill(PlayerDeathEvent event){
        Player killer = event.getEntity().getKiller();

        PlayerManagement management = new PlayerManagement(killer);

        if (management.getKit().equals("Chemist")){
            ItemStack damage = new ItemStack(Material.POTION, 1, (short)16460);
            ItemMeta damageMeta = damage.getItemMeta();
            damageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lInstant Damage Potion &7(Splash)"));
            damage.setItemMeta(damageMeta);

            ItemStack poison = new ItemStack(Material.POTION, 2, (short)16388);
            ItemMeta poisonMeta = poison.getItemMeta();
            poisonMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lPoison Potion &7(Splash)"));
            poison.setItemMeta(poisonMeta);

            if (!killer.getInventory().contains(damage)){
                for (int i = 0; i < 3; i++) {
                    killer.getInventory().addItem(damage);
                }
            } else {
                killer.getInventory().addItem(damage);
            }
            if (!killer.getInventory().contains(poison)){
                for (int i = 0; i < 2; i++) {
                    killer.getInventory().addItem(poison);
                }
            } else {
                killer.getInventory().addItem(poison);
            }
        }
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.POTION, 1, (short)16460);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Chemist";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dChemist"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7''What is on the next chemistry test?!''"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Helmet (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Chestplate (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Leggings (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Chain Boots (Protection 1)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Stone Sword (Sharpness 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Speed 1"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &8&lSmoke Bomb"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7On Right-Click, will apply players around you with"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Slowness, Nausea, and Blindness, for 15 seconds!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
