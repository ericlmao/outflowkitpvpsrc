package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import network.outflowkits.utils.Utils;
import org.bukkit.entity.Player;

public class DonationManagement {
    private KitPvP plugin;
    public DonationManagement(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public int getDonations(){
        return plugin.donations.config.getInt("donations.current");
    }

    public int getDonationGoal(){
        return plugin.donations.config.getInt("donations.goal");
    }

    public void setDonations(int amount){
        plugin.donations.config.set("donations.current", amount);
        plugin.donations.saveData();
    }

    public void addDonations(int amount){
        plugin.donations.config.set("donations.current", getDonations() + amount);
        plugin.donations.saveData();

        if (getDonations() >= getDonationGoal()){
            plugin.donations.config.set("donations.cooldown", System.currentTimeMillis());
            plugin.donations.config.set("donations.multiplier", System.currentTimeMillis());
            plugin.donations.saveData();
            setDonations(0);

            Utils.broadcast(" ");
            Utils.broadcast("&6&lDonations");
            Utils.broadcast("&7The donation goal of &6100,000 Coins &7has been reached!");
            Utils.broadcast(" ");
            Utils.broadcast("&7All players will now have a &62x Coin &7multiplier for an hour!");
            Utils.broadcast("&7Thank you to all the players who have donated!");
            Utils.broadcast(" ");
        }
    }

    public int getPlayerDonation(Player player){
        String uuidString = player.getUniqueId().toString();
        return plugin.donations.config.getInt("players." + uuidString);
    }
    public void addPlayerDonation(Player player, int amount){
        String uuidString = player.getUniqueId().toString();
        plugin.donations.config.set("players." + uuidString, getPlayerDonation(player) + amount);
        plugin.donations.saveData();
    }

    public boolean canDonate(Player player, int amount){
        int currentDonation = getPlayerDonation(player);
        return currentDonation + amount <= 10000;
    }

    public long getDonationCooldown(){
        return plugin.donations.config.getLong("donations.cooldown");
    }

    public long getMultiplierTime(){
        if (plugin.donations.config.get("donations.multiplier") != null){
            return plugin.donations.config.getLong("donations.multiplier");
        }
        return Long.parseLong(null);
    }

    public boolean isMultiplierActive(){
        long current = System.currentTimeMillis();
        long multiplier = getMultiplierTime();
        long newCurrent = current - multiplier;

        return newCurrent < 3600000;
    }

    public boolean isDonationCooldownActive(){
        long current = System.currentTimeMillis();
        long cooldown = getDonationCooldown();
        long newCurrent = current - cooldown;

        return newCurrent < 7200000;
    }

}
