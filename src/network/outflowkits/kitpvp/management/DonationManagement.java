package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
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

}
