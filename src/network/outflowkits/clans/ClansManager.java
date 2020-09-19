package network.outflowkits.clans;

import network.outflowkits.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClansManager {
    private KitPvP plugin;
    public ClansManager() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public void createClan(String name, UUID leaderUUID){
        plugin.clansData.config.set("clans." + name + ".leader", leaderUUID.toString());
        plugin.clansData.saveData();

        plugin.clansData.config.set("clans." + name + ".kills", 0);
        plugin.clansData.saveData();

        plugin.clansData.config.set("clans." + name + ".deaths", 0);
        plugin.clansData.saveData();

        ArrayList<String> members = new ArrayList<>();
        members.add(leaderUUID.toString());
        plugin.clansData.config.set("clans." + name + ".members", members);
        plugin.clansData.saveData();
    }

    public void disbandClan(String name){
        plugin.clansData.config.set("clans." + name, null);
        plugin.clansData.saveData();
    }

    public boolean clanExists(String name){
        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");

        for (String clan : configuration.getKeys(false)){
            if (clan.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public boolean alreadyInClan(UUID uuid){
        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        String uuidString = uuid.toString();
        String playerClan = getClan(uuid);
        if (playerClan == null)return false;

        for (String clan : configuration.getKeys(false)){
            if (clan.equalsIgnoreCase(playerClan)){
                List<String> memberUUIDs = plugin.clansData.config.getStringList("clans." + clan + ".members");
                if (memberUUIDs.contains(uuidString)){
                    return true;
                }
            }
        }
        return false;
    }

    public String getClan(UUID uuid){
        String uuidString = uuid.toString();

        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        for (String clan : configuration.getKeys(false)){
            List<String> memberUUIDs = plugin.clansData.config.getStringList("clans." + clan + ".members");
            if (memberUUIDs.contains(uuidString)){
                return clan;
            }
        }
        return null;
    }

    public ArrayList<String> getMembers(String name){
        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        for (String clan : configuration.getKeys(false)){
            if (clan.equalsIgnoreCase(name)){
                return (ArrayList<String>) plugin.clansData.config.getStringList("clans." + clan + ".members");
            }
        }
        return null;
    }

    public boolean isLeader(UUID attempterUUID){
        String clanName = getClan(attempterUUID);

        String uuidString = attempterUUID.toString();

        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        for (String clan : configuration.getKeys(false)){
            if (clan.equalsIgnoreCase(clanName)){
                String leaderUUID = plugin.clansData.config.getString("clans." + clanName + ".leader");
                return leaderUUID.equals(uuidString);
            }
        }
        return false;
    }
    public ArrayList<String> getInvitations(String name){
        return (ArrayList<String>) plugin.clansData.config.getStringList("clans." + getClanName(name) + ".active-invites");
    }
    public void invite(String name, UUID invited){
        if (getInvitations(name) == null || getInvitations(name).isEmpty()){
            ArrayList<String> inviteStringList = new ArrayList<>();
            inviteStringList.add(invited.toString());

            plugin.clansData.config.set("clans." + name + ".active-invites", inviteStringList);
            plugin.clansData.saveData();
            return;
        }
        ArrayList<String> activeInvites = getInvitations(name);
        activeInvites.add(invited.toString());
        plugin.clansData.config.set("clans." + name + ".active-invites", activeInvites);
        plugin.clansData.saveData();
    }
    public void uninvite(String name, UUID removedUUID){
        if (getInvitations(name) == null || getInvitations(name).isEmpty())return;
        if (getInvitations(name).size() == 1){
            String uuid = getInvitations(name).get(0);
            String removedStringUUID = removedUUID.toString();

            if (uuid.equals(removedStringUUID)){
                plugin.clansData.config.set("clans." + name + ".active-invites", null);
                plugin.clansData.saveData();
            }
            return;
        }
        ArrayList<String> invites = getInvitations(name);
        for (int i = 0; i < invites.size(); i++) {
            String indexUUID = invites.get(i);
            if (indexUUID.equals(removedUUID.toString())){
                invites.remove(i);
            }
        }
        plugin.clansData.config.set("clans." + name + ".active-invites", invites);
        plugin.clansData.saveData();

    }

    public void joinClan(UUID uuid, String name){
        ArrayList<String> members = getMembers(name);
        members.add(uuid.toString());
        plugin.clansData.config.set("clans." + name + ".members", members);
        plugin.clansData.saveData();

        uninvite(name, uuid);
    }

    public void leaveClan(UUID uuid){
        String clanName = getClanName(getClan(uuid));
        ArrayList<String> members = getMembers(clanName);

        for (int i = 0; i < members.size(); i++) {
            String indexUUID = members.get(i);
            if (indexUUID.equals(uuid.toString())){
                members.remove(i);
            }
        }
        plugin.clansData.config.set("clans." + clanName + ".members", members);
        plugin.clansData.saveData();
    }

    public void transferOwnership(UUID thePlayer){
        String clan = getClan(thePlayer);
        plugin.clansData.config.set("clans." + clan + ".leader", thePlayer.toString());
        plugin.clansData.saveData();
    }

    public String getClanName(String name){
        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        for (String clan : configuration.getKeys(false)){
            if (clan.equalsIgnoreCase(name)){
                return clan;
            }
        }
        return null;
    }

    public void clearActiveInvites(){
        ConfigurationSection configuration = plugin.clansData.config.getConfigurationSection("clans");
        for (String clan : configuration.getKeys(false)){
            ArrayList<String> activeInv = getInvitations(clan);
            if (activeInv != null || !activeInv.isEmpty()){
                plugin.clansData.config.set("clans." + clan + ".active-invites", null);
                plugin.clansData.saveData();
            }
        }
    }

    public OfflinePlayer getLeader(String clan){
        String stringID = plugin.clansData.config.getString("clans." + clan + ".leader");
        UUID uuid = UUID.fromString(stringID);

        return Bukkit.getOfflinePlayer(uuid);
    }

    public int getKills(String clan){
        String clanName = getClanName(clan);
        return plugin.clansData.config.getInt("clans." + clanName + ".kills");
    }

    public int getDeaths(String clan){
        String clanName = getClanName(clan);
        return plugin.clansData.config.getInt("clans." + clanName + ".deaths");
    }

    public void addKill(String clan){
        String clanName = getClanName(clan);
        int current = getKills(clanName);

        plugin.clansData.config.set("clans." + clanName + ".kills", current + 1);
        plugin.clansData.saveData();
    }

    public void addDeath(String clan){
        String clanName = getClanName(clan);
        int current = getDeaths(clanName);

        plugin.clansData.config.set("clans." + clanName + ".deaths", current + 1);
        plugin.clansData.saveData();
    }

}
