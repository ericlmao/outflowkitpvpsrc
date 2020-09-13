package network.outflowkits.data;

import network.outflowkits.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerData {
    private KitPvP plugin;
    public FileConfiguration config;
    public File file;

    public PlayerData() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public void setupData() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "data.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the data.yml file. Please contact the dev.");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return config;
    }

    public void saveData() {
        try {
            config.save(file);
        }
        catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the data.yml file.");
        }
    }

    public void reloadData() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
