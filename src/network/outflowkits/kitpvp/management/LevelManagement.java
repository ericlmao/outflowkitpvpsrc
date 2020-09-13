package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class LevelManagement {

    private KitPvP plugin;
    private Player player;
    public LevelManagement(Player player){
        this.player = player;
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    public int getExperience(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".levels.xp");
    }

    public void addExperience(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".levels.xp", getExperience() + amount);
        plugin.data.saveData();

        attemptLevelUp();
    }
    public void removeExperience(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".levels.xp", getExperience() - amount);
        plugin.data.saveData();
    }

    public int getLevel(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".levels.current");
    }
    public int getRequiredExperience(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".levels.required");
    }
    public void levelUpRequired(){
        plugin.data.config.set(player.getUniqueId().toString() + ".levels.required", getRequiredExperience() * 1.05);
        plugin.data.saveData();
    }

    public String getLevelDisplay(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        int level = getLevel();

        return config.getString("display." + level);
    }

    public int getMaxLevel(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getInt("max level");
    }

    public void attemptLevelUp(){
        if (getLevel() < getMaxLevel()) {
            if (getExperience() >= getRequiredExperience()) {
                levelup();
            }
        }
    }

    private void levelup() {
        plugin.data.config.set(player.getUniqueId().toString() + ".levels.current", getLevel() + 1);
        int required = getRequiredExperience();
        removeExperience(required);
        levelUpRequired();

        Utils.sendMessage(player, "&a&lYou have successfully leveled up to &7[" + getLevelDisplay() + "✰&7]");
    }

}
