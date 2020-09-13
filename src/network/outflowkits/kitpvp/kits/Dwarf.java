package network.outflowkits.kitpvp.kits;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Dwarf implements Listener {
    private KitPvP plugin;
    private ArrayList<Player> dwarfCharge = new ArrayList<>();
    public Dwarf() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public static void getKit(Player player) {
        Utils.giveHealing(player);

        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());

        player.getInventory().setItem(0, getAbilityItem());

        player.updateInventory();
    }
    // Ability item / Weapon
    private static ItemStack getAbilityItem() {
        ItemStack item = new ItemStack(Material.GOLD_AXE);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 20);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lDwarf's Trusty Axe"));
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Shift up to 5 seconds to be able to activate"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6&lDwarf YEET"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Dwarf YEET will send your enemies flying while doubling"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7the initial damage dealt to the player!"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack getHelmet() {
        ItemStack item = new ItemStack(Material.GOLD_HELMET);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getChestplate() {
        ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getLeggings() {
        ItemStack item = new ItemStack(Material.GOLD_LEGGINGS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    private static ItemStack getBoots() {
        ItemStack item = new ItemStack(Material.GOLD_BOOTS);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        return item;
    }

    @EventHandler
    public void ability(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player){
            if (event.getEntity() instanceof Player){
                Player victim = (Player) event.getEntity();
                Player attacker = (Player) event.getDamager();

                if (attacker.getInventory().getItemInHand().getType() == Material.GOLD_AXE){
                    PlayerManagement attackerManagement = new PlayerManagement(attacker);
                    if (attackerManagement.getKit().equals("Dwarf")) {
                        if (attacker.isSneaking()) {
                            if (!Utils.canUseAbility(attacker)){
                                event.setCancelled(true);
                                Utils.sendMessage(attacker, "&c&lYou cannot use abilities while in a protected area");
                                Utils.playSound(attacker, Sound.VILLAGER_NO);
                                return;
                            }
                            if (plugin.dwarf_cooldown.containsKey(attacker)){
                                double time = plugin.dwarf_cooldown.get(attacker);
                                DecimalFormat df = new DecimalFormat("###,###.#");
                                Utils.sendMessage(attacker, "&cPlease wait &e" + df.format(time) + " seconds &cbefore doing this again!");
                                event.setCancelled(true);
                                return;
                            }
                            if (plugin.dwarf_warmup.containsKey(attacker)) {
                                if (plugin.dwarf_warmup.get(attacker) < 5) {
                                    Utils.sendMessage(attacker, "&c&lYour ability is not fully charged yet!");
                                    event.setCancelled(true);
                                    attacker.updateInventory();
                                } else {
                                    yeetPlayer(attacker, victim);
                                    plugin.dwarf_cooldown.put(attacker, 30.0);
                                    plugin.dwarf_warmup.remove(attacker);
                                    event.setDamage(event.getDamage() * 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void yeetPlayer(Player attacker, Player victim) {
        Vector vector = attacker.getLocation().getDirection().multiply(8).setY(7);
        victim.setVelocity(vector);
    }

    public static ItemStack getSelectorIcon(Player player){
        ItemStack item = new ItemStack(Material.GOLD_AXE);
        ItemMeta meta = item.getItemMeta();

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        PlayerManagement management = new PlayerManagement(player);
        String kit = "Dwarf";
        int kills = management.getKitKills(kit);
        int deaths = management.getKitDeaths(kit);
        double kdr = management.getKitKDR(kit);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dwarf"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7I'm gonna yeet this guy outta my sight"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lKit Statistics"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Kills &c" + df.format(kills)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Deaths &c" + df.format(deaths)));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7KDR &c" + df.format(kdr)));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lArmor"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Helmet (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Chestplate (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Leggings (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Boots (Protection 1, Unbreaking 5)"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lWeapons"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 Gold Axe (Sharpness"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lPotion Effects"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8 None"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&9&lAbilities"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &6&lDwarf YEET"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7Shift up to 5 seconds to be able to use &6&lDwarf YEET"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7on the next person you attack."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7This ability will send the absolutely flying! And will also"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " &7double the initial damage."));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to select"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
