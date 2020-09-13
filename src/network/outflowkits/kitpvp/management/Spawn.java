package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.io.File;

public class Spawn {
    private Player player;
    private KitPvP plugin;
    public Spawn(Player player){
        plugin = KitPvP.getPlugin(KitPvP.class);
        this.player = player;
    }

    public void send(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String worldname = config.getString("spawn.world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        double yaw = config.getDouble("spawn.yaw");
        double pitch = config.getDouble("spawn.pitch");

        World world = Bukkit.getWorld(worldname);

        Location loc = new Location(world, x, y, z, (float) pitch, (float) yaw);

        player.teleport(loc);

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack selector = new ItemStack(Material.COMPASS);
        ItemMeta selMeta = selector.getItemMeta();

        selMeta.setDisplayName(ChatColor.AQUA + "Kit Selector");
        selector.setItemMeta(selMeta);


        player.getInventory().setItem(0, getSelector());
        player.getInventory().setItem(1, getShop());

        if (plugin.recentKit.containsKey(player)){
            player.getInventory().setItem(7, getRecent());
        }
        player.getInventory().setItem(8, getSettings());

        Utils.playSound(player, Sound.ENDERMAN_TELEPORT);

        PlayerManagement management = new PlayerManagement(player);
        management.setKit("None");

        plugin.combat.put(player, 0.1);
    }

    private ItemStack getSelector(){
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Kit Selector");
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack getShop(){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Kit Shop");
        item.setItemMeta(meta);

        return item;
    }
    private ItemStack getRecent(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Select Recent Kit");
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack getSettings(){
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + "User Settings");
        item.setItemMeta(meta);

        return item;
    }

}
