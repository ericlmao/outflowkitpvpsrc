package network.outflowkits.kitpvp.listeners;

import me.activated.core.api.rank.RankData;
import me.activated.core.plugin.AquaCoreAPI;
import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.LevelManagement;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.kitpvp.management.Spawn;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Death implements Listener {

    private KitPvP plugin;
    public Death(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void kill(PlayerDeathEvent event){
        Player victim = event.getEntity();
        if (!victim.getWorld().getName().equals("Kit"))return;
        resetCooldowns(victim);
        event.setDeathMessage("");
        event.getDrops().clear();
        if (!Utils.isInMainWorld(event.getEntity().getKiller()))return;
        if (!Utils.isInMainWorld(event.getEntity()))return;
        if (event.getEntity().getKiller() != null){
            Player killer = event.getEntity().getKiller();
            if (killer == victim){
                PlayerManagement victimManagement = new PlayerManagement(victim);
                victimManagement.addDeath();
                victimManagement.endKillStreak();
                plugin.combat.remove(victim);
                plugin.combatwith.get(plugin.combatwith.get(victim));
                plugin.combatwith.remove(victim);
                victimManagement.addKitDeath(victimManagement.getKit());

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> victim.spigot().respawn(), 2);
                return;
            }
            PlayerManagement killerManagement = new PlayerManagement(killer);

            Random random = new Random();
            int amount = random.nextInt(25 + 1);
            int xpamount = random.nextInt(30 + 1);

            double multiplier = 1;
            double actualCoins;

            RankData data = AquaCoreAPI.INSTANCE.getPlayerRank(killer.getUniqueId());
            String rank = data.getName();
            switch (rank.toLowerCase()){
                case "gold":
                case "platinum":
                case "ruby":
                case "youtuber":
                case "famous":
                case "partner":
                case "builder":
                case "head-builder":
                case "trial-mod":
                case "mod":
                case "mod+":
                case "seniormod":
                case "admin":
                case "senioradmin":
                case "developer":
                case "platformadmin":
                case "manager":
                case "owner":{
                    multiplier = 1.25;
                    break;
                }
                case "default":{
                    multiplier = 1.0;
                    break;
                }
            }
            actualCoins = amount * multiplier;

            Utils.sendMessage(killer, "&7&lYou have killed &c&l" + victim.getName() + " &7&land earned &6&l+" + (int) actualCoins + " Coins&7&l and &b&l" + xpamount + " XP&7&l!");

            LevelManagement levelManagement = new LevelManagement(killer);
            levelManagement.addExperience(xpamount);

            killerManagement.addCoins((int) actualCoins);
            killerManagement.addKill();
            killerManagement.addKillStreak();
            for(int ksgoal : killerManagement.getKSGoal()){
                if (killerManagement.getCurrentKillStreak() == ksgoal){
                    ksreward(killer);
                }
            }
            killerManagement.addKitKill(killerManagement.getKit());

            Utils.sendMessage(victim, "&7&lYou have been killed by &c&l" + killer.getName() + "&7&l!");


            ItemStack item;
            if (killerManagement.potionsEnabled()){
                item = new ItemStack(Material.POTION,1, (short) 16421);
            } else {
                item = new ItemStack(Material.MUSHROOM_SOUP);
            }
            for (int i = 0; i < 6; i++) {
                killer.getInventory().addItem(item);
            }
            PlayerManagement victimManagement = new PlayerManagement(victim);
            int victimks = victimManagement.getCurrentKillStreak();
            if (victimks >= 15){
                killerManagement.addCoins(victimks);
                Utils.broadcast("&9&l" + killer.getName() + " &7has broken &9&l" + victim.getName() + "&7's killstreak of &9&l" + victimks + "&7!");
                Utils.sendMessage(killer, "&7You have gained an extra &9" + victimks + " &7Coins from breaking the killstreak!");
            }

            victimManagement.addDeath();
            victimManagement.endKillStreak();
            plugin.combat.remove(victim);
            plugin.combatwith.get(plugin.combatwith.get(victim));
            plugin.combatwith.remove(victim);
            victimManagement.addKitDeath(victimManagement.getKit());
            return;
        }
        PlayerManagement victimManagement = new PlayerManagement(victim);
        victimManagement.addDeath();
        victimManagement.endKillStreak();
        plugin.combat.remove(victim);
        plugin.combatwith.get(plugin.combatwith.get(victim));
        plugin.combatwith.remove(victim);
        victimManagement.addKitDeath(victimManagement.getKit());
    }

    private void resetCooldowns(Player player) {
        plugin.stomper_cooldown.remove(player);
        plugin.avatar_cooldown.remove(player);
        plugin.barbarian_cooldown.remove(player);
        plugin.chemist_cooldown.remove(player);
        plugin.fisherman_cooldown.remove(player);
        plugin.kangaroo_cooldown.remove(player);
        plugin.kidnapper_cooldown.remove(player);
        plugin.mario_cooldown.remove(player);
        plugin.ninja_cooldown.remove(player);
        plugin.switcher_cooldown.remove(player);
    }

    private void ksreward(Player player) {
        PlayerManagement management = new PlayerManagement(player);
        LevelManagement lManagement = new LevelManagement(player);
        int coinreward = 2;
        int xpreward = 5;
        int ks = management.getCurrentKillStreak();

        management.addCoins(coinreward * ks);
        lManagement.addExperience(xpreward * ks);

        Utils.sendMessage(player, "&8[&aKillstreak Reward&8] &aYou have been rewarded with &6" + coinreward * ks + " Coins &aand &b" +
                xpreward * ks + " XP &afor reaching a killstreak of " + ks + ".");

        Utils.broadcast("&8[&aKillstreaks&8] &a" + player.getName() + " is currently on a killstreak of " + ks + "!");

        Utils.playSound(player, Sound.FIREWORK_BLAST);
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event){
        Spawn spawn = new Spawn(event.getPlayer());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, spawn::send,1 );
    }
}
