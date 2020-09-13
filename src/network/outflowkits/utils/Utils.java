package network.outflowkits.utils;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static KitPvP plugin;

    public Utils() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    public static void sendMessage(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcast(String message){
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void playSound(Player player, Sound sound){
        player.playSound(player.getLocation(), sound, 1, 1);
    }
    public static void playSound(Location loc, Sound sound){ loc.getWorld().playSound(loc, sound, 1, 1); }
    public static void sendTitle(Player player, String title, String subtitle){
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', title),
                ChatColor.translateAlternateColorCodes('&', subtitle));
    }

    public static boolean canUseAbility(Player player){
        for (ProtectedRegion rg : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
            ArrayList<String> triggerRegions = new ArrayList<>();
            ArrayList<String> regions = new ArrayList<>();
            regions.add("spawn");
            regions.add("fpsspawn");
            for(String region : regions){
                triggerRegions.add(region);
            }
            if (triggerRegions.contains(rg.getId().toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public static void giveHealing(Player player){
        ItemStack item;
        PlayerManagement management = new PlayerManagement(player);
        if (management.potionsEnabled()){
            item = new ItemStack(Material.POTION,1, (short) 16421);
        } else {
            item = new ItemStack(Material.MUSHROOM_SOUP);
        }

        for (int i = 0; i < 36; i++) {
            player.getInventory().addItem(item);
        }
    }

    public static boolean isInMainWorld(Player player){
        return player.getWorld().getName().equals("Kit");
    }

    public static Location getSpawnLocation(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String worldname = config.getString("spawn.world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        double yaw = config.getDouble("spawn.yaw");
        double pitch = config.getDouble("spawn.pitch");

        World world = Bukkit.getWorld(worldname);

        return new Location(world, x, y, z, (float) pitch, (float) yaw);
    }

    public static List<Integer> getKSGoals(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getIntegerList("killstreak goals");
    }

    public static int getKitPrice(String kit){
        switch (kit.toLowerCase()){
            case "barbarian":return 12500;
            case "gank":return 25000;

            case "chemist":
            case "stomper":
            case "switcher":
            case "tank":
                return 10000;

            case "fisherman":return 7500;

            case "kangaroo":
            case "teleporter":
                return 5000;

            case "avatar":
            case "kidnapper":
            case "mario":
            case "ninja":
            case "dwarf":
                return 15000;

        }
        return 0;
    }

}
