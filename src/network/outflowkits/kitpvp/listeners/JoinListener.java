package network.outflowkits.kitpvp.listeners;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.kitpvp.management.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class JoinListener implements Listener {
    private KitPvP plugin;

    public JoinListener() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @EventHandler
    public void join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        checkPlayer(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Spawn spawn = new Spawn(player);
            spawn.send();
        }, 1);
        plugin.scoreboard.scoreboard(player);
    }

    // Checking the player's statistics
    private void checkPlayer(Player player) {
        String UUIDString = player.getUniqueId().toString();
        // Checking everything
        if (plugin.data.config.get(UUIDString) == null){
            plugin.data.config.set(UUIDString + ".kills", 0);
            plugin.data.config.set(UUIDString + ".deaths", 0);
            plugin.data.config.set(UUIDString + ".coins", 0);
            plugin.data.config.set(UUIDString + ".current-killstreak", 0);
            plugin.data.config.set(UUIDString + ".best-killstreak", 0);
            plugin.data.config.set(UUIDString + ".settings.potions-enabled", false);
            plugin.data.config.set(UUIDString + ".settings.drops-enabled", true);
            plugin.data.config.set(UUIDString + ".settings.stats-viewing-enabled", true);
            plugin.data.saveData();
        }
        // Checking kills
        if (plugin.data.config.get(UUIDString + ".kills") == null){
            plugin.data.config.set(UUIDString + ".kills", 0);
        }
        // Checking deaths
        if (plugin.data.config.get(UUIDString + ".deaths") == null){
            plugin.data.config.set(UUIDString + ".deaths", 0);
        }
        // Checking coins
        if (plugin.data.config.get(UUIDString + ".coins") == null){
            plugin.data.config.set(UUIDString + ".coins", 0);
        }
        // Checking (current) killstreak
        if (plugin.data.config.get(UUIDString + ".current-killstreak") == null){
            plugin.data.config.set(UUIDString + ".current-killstreak", 0);
        }
        // Checking (best) killstreak
        if (plugin.data.config.get(UUIDString + ".best-killstreak") == null){
            plugin.data.config.set(UUIDString + ".best-killstreak", 0);
        }
        if (plugin.data.config.get(UUIDString + ".settings") == null){
            plugin.data.config.set(UUIDString + ".settings.potions-enabled", false);
            plugin.data.config.set(UUIDString + ".settings.drops-enabled", true);
            plugin.data.config.set(UUIDString + ".settings.stats-viewing-enabled", true);
        }
        if (plugin.data.config.get(UUIDString + ".settings.potions-enabled") == null){
            plugin.data.config.set(UUIDString + ".settings.potions-enabled", false);
        }
        if (plugin.data.config.get(UUIDString + ".settings.drops-enabled") == null){
            plugin.data.config.set(UUIDString + ".settings.drops-enabled", true);
        }
        if (plugin.data.config.get(UUIDString + ".settings.stats-viewing-enabled") == null){
            plugin.data.config.set(UUIDString + ".settings.stats-viewing-enabled", true);
        }
        if (plugin.data.config.get(UUIDString + ".unlocked") == null){
            ArrayList<String> kits = new ArrayList<>();
            kits.add("PvP");
            kits.add("Archer");
            plugin.data.config.set(UUIDString + ".unlocked", kits);
        }
        if (plugin.data.config.get(UUIDString + ".levels") == null){
            plugin.data.config.set(UUIDString + ".levels.xp", 0);
            plugin.data.config.set(UUIDString + ".levels.required", 250);
            plugin.data.config.set(UUIDString + ".levels.current", 0);
            plugin.data.saveData();
        }

    }
}
