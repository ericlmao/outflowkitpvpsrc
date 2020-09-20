package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CooldownManagement {
    private KitPvP plugin;
    private Player player;
    public CooldownManagement(Player player){
        this.player = player;
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public long getCooldown(String name){
        ConfigurationSection section = plugin.cooldowns.config.getConfigurationSection("");

        for (String playerUUID : section.getKeys(false)){
            if (playerUUID.equals(player.getUniqueId().toString())){
                ConfigurationSection cooldownSection = plugin.cooldowns.config.getConfigurationSection(playerUUID);
                for (String cooldownValues : cooldownSection.getKeys(false)){
                    if (cooldownValues.equals(name)){
                        return plugin.cooldowns.config.getLong(playerUUID + "." + cooldownValues);
                    }
                }
            }
        }
        return 0;
    }

    public boolean hasCooldown(String name){
        ConfigurationSection section = plugin.cooldowns.config.getConfigurationSection("");

        for (String playerUUID : section.getKeys(false)){
            if (playerUUID.equals(player.getUniqueId().toString())){
                ConfigurationSection cooldownSection = plugin.cooldowns.config.getConfigurationSection(playerUUID);
                for (String cooldownValues : cooldownSection.getKeys(false)){
                    if (cooldownValues.equals(name)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeCooldown(String name){
        ConfigurationSection section = plugin.cooldowns.config.getConfigurationSection("");

        for (String playerUUID : section.getKeys(false)){
            if (playerUUID.equals(player.getUniqueId().toString())){
                ConfigurationSection cooldownSection = plugin.cooldowns.config.getConfigurationSection(playerUUID);
                for (String cooldownValues : cooldownSection.getKeys(false)){
                    if (cooldownValues.equals(name)){
                        plugin.cooldowns.config.set(playerUUID + "." + cooldownValues, null);
                        plugin.cooldowns.saveData();
                    }
                }
            }
        }
    }

    public void setCooldown(String name, int cooldownInSeconds){
        long currentTime = System.currentTimeMillis();
        long cooldownTime = cooldownInSeconds * 1000;
        long newTime = currentTime + cooldownTime;

        plugin.cooldowns.config.set(player.getUniqueId().toString() + "." + name, newTime);
        plugin.cooldowns.saveData();
    }

    public String formatCooldown(long cooldown){
        long currentTime = System.currentTimeMillis();

        long newTime = cooldown - currentTime;

        if (newTime <= 60000L){
            String seconds = "%second%s";

            int toseconds = (int) (newTime / 1000) % 60;

            seconds = seconds.replace("%second%", String.valueOf(toseconds));

            return seconds;
        }
        if (newTime <= 3599000L){
            String seconds = "%second%s";
            String minutes = "%minute%m";

            int toseconds = (int) (newTime / 1000) % 60;
            int tominutes = (int) (newTime / 1000) / 60;

            seconds = seconds.replace("%second%", String.valueOf(toseconds));
            minutes = minutes.replace("%minute%", String.valueOf(tominutes));

            if (toseconds == 0){
                return minutes;
            } else {
                return minutes + " " + seconds;
            }

        }
        if (newTime <= 86399000L){
            String seconds = "%second%s";
            String minutes = "%minute%m";
            String hours = "%hour%h";

            int toseconds = (int) (newTime/ 1000) % 60;
            int tominutes = (int) ((newTime / (1000 * 60)) % 60);
            int tohours = (int) ((newTime / (1000 * 60 * 60)) % 24);

            seconds = seconds.replace("%second%", String.valueOf(toseconds));
            minutes = minutes.replace("%minute%", String.valueOf(tominutes));
            hours = hours.replace("%hour%", String.valueOf(tohours));
            if (tohours == 0){
                return minutes + " " + seconds;
            }
            if (tominutes == 0){
                if (toseconds == 0){
                    return hours;
                } else {
                    return hours + " " + seconds;
                }
            } else {
                return hours + " " + minutes + " " + seconds;
            }
        } else {
            String seconds = "%second%s";
            String minutes = "%minute%m";
            String hours = "%hour%h";
            String days = "%day%d";

            int toseconds = (int) (newTime / 1000) % 60;
            int tominutes = (int) ((newTime / (1000 * 60)) % 60);
            int tohours = (int) ((newTime / (1000 * 60 * 60)) % 24);
            int todays = (int) (newTime / (1000 * 60 * 60 * 24));

            seconds = seconds.replace("%second%", String.valueOf(toseconds));
            minutes = minutes.replace("%minute%", String.valueOf(tominutes));
            hours = hours.replace("%hour%", String.valueOf(tohours));
            days = days.replace("%day%", String.valueOf(todays));

            if (todays == 0){
                return hours + " " + minutes + " " + seconds;
            }
            if (tohours == 0){
                if (tominutes == 0){
                    if (toseconds == 0){
                        return days;
                    } else{
                        return days + " " + seconds;
                    }
                } else {
                    return days + " " + minutes + " " + seconds;
                }
            } else {
                return days + " " + hours + " " + minutes + " " + seconds;
            }
        }
    }
}
