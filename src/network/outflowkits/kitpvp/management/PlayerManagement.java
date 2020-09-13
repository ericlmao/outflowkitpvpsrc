package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class PlayerManagement {
    private KitPvP plugin;
    private OfflinePlayer player;
    public PlayerManagement(OfflinePlayer player) {
        plugin = KitPvP.getPlugin(KitPvP.class);
        this.player = player;
    }

    public int getKills(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".kills");
    }
    public int getDeaths(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".deaths");
    }
    public int getCoins(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".coins");
    }
    public int getCurrentKillStreak(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".current-killstreak");
    }
    public int getBestKillStreak(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".best-killstreak");
    }
    public double getKDR(){
        double kills = getKills();
        double deaths = getDeaths();
        double kdr = kills / deaths;
        return kdr;
    }

    public void addKill(){
        plugin.data.config.set(player.getUniqueId().toString() + ".kills", getKills() + 1);
        plugin.data.saveData();
    }
    public void addDeath(){
        plugin.data.config.set(player.getUniqueId().toString() + ".deaths", getDeaths() + 1);
        plugin.data.saveData();
    }

    public void addKillStreak(){
        plugin.data.config.set(player.getUniqueId().toString() + ".current-killstreak", getCurrentKillStreak() + 1);
        plugin.data.saveData();
        if (getCurrentKillStreak() > getBestKillStreak()){
            plugin.data.config.set(player.getUniqueId().toString() + ".best-killstreak", getCurrentKillStreak());
            plugin.data.saveData();
        }
    }
    public void endKillStreak(){
        plugin.data.config.set(player.getUniqueId().toString() + ".current-killstreak", 0);
        plugin.data.saveData();
    }
    public void addCoins(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".coins", getCoins() + amount);
        plugin.data.saveData();
    }
    public void takeCoins(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".coins", getCoins() - amount);
        plugin.data.saveData();
    }
    public void setKit(String name){
        plugin.data.config.set(player.getUniqueId().toString() + ".kits.current", name);
        plugin.data.saveData();
    }

    public List<String> getUnlockedKits(){
        return plugin.data.config.getStringList(player.getUniqueId().toString() + ".unlocked");
    }

    public void unlockKit(String kit){
        List<String> kits = getUnlockedKits();
        kits.add(kit);

        plugin.data.config.set(player.getUniqueId().toString() + ".unlocked", kits);
        plugin.data.saveData();
    }

    public String getKit(){
        return plugin.data.config.getString(player.getUniqueId().toString() + ".kits.current");
    }

    public boolean hasEnough(int cost){
        return getCoins() >= cost;
    }

    public int getKitDeaths(String kit){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".kit-stats." + kit + ".deaths");
    }
    public void addKitDeath(String kit){
        plugin.data.config.set(player.getUniqueId().toString() + ".kit-stats." + kit + ".deaths", getKitDeaths(kit) + 1);
        plugin.data.saveData();
    }

    public int getKitKills(String kit){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".kit-stats." + kit + ".kills");
    }
    public void addKitKill(String kit){
        plugin.data.config.set(player.getUniqueId().toString() + ".kit-stats." + kit + ".kills", getKitKills(kit) + 1);
        plugin.data.saveData();
    }
    public double getKitKDR(String kit){
        double kills = getKitKills(kit);
        double deaths = getKitDeaths(kit);
        double kdr = kills / deaths;
        return kdr;
    }

    public int getLongestCombo(){
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".longest-combo");
    }
    public void setLongestCombo(int combo){
        int longest = getLongestCombo();

        plugin.data.config.set(player.getUniqueId().toString() + ".longest-combo", longest + combo);
        plugin.data.saveData();
    }

    public boolean potionsEnabled(){
        return plugin.data.config.getBoolean(player.getUniqueId().toString() + ".settings.potions-enabled");
    }
    public void setPotionsEnabled(boolean b){
        plugin.data.config.set(player.getUniqueId().toString() + ".settings.potions-enabled", b);
        plugin.data.saveData();
    }

    public boolean dropsEnabled(){
        return plugin.data.config.getBoolean(player.getUniqueId().toString() + ".settings.drops-enabled");
    }
    public void setDropsEnabled(boolean b){
        plugin.data.config.set(player.getUniqueId().toString() + ".settings.drops-enabled", b);
        plugin.data.saveData();
    }

    public boolean statisticsViewingEnabled(){
        return plugin.data.config.getBoolean(player.getUniqueId().toString() + ".settings.stats-viewing-enabled");
    }
    public void setStatisticsViewingEnabled(boolean b){
        plugin.data.config.set(player.getUniqueId().toString() + ".settings.stats-viewing-enabled", b);
        plugin.data.saveData();
    }

    public int getKitUnlockers(){ return plugin.data.config.getInt(player.getUniqueId().toString() + ".kit-unlockers"); }

    public void giveKitUnlockers(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".kit-unlockers", getKitUnlockers() + amount);
        plugin.data.saveData();
    }
    public void removeKitUnlockers(int amount){
        plugin.data.config.set(player.getUniqueId().toString() + ".kit-unlockers", getKitUnlockers() - amount);
        plugin.data.saveData();
    }

    public List<String> getKits(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getStringList("kits");
    }

    public List<Integer> getKSGoal(){
        File file = new File(plugin.getDataFolder(), "settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getIntegerList("killstreak goals");
    }
}
