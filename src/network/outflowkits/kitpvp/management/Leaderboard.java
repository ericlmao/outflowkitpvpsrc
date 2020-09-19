package network.outflowkits.kitpvp.management;

import network.outflowkits.KitPvP;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Leaderboard {

    private KitPvP plugin;
    public Leaderboard(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public String topCoinsName(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("coins");
                    int points2 = ((MemorySection) a2.getValue()).getInt("coins");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("coins")))
                .collect(Collectors.toList());

        Pair<String, Integer> thename = top.get(newpos);
        String name = thename.getKey();
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return player.getName();
    }

    public int topCoinsValue(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("coins");
                    int points2 = ((MemorySection) a2.getValue()).getInt("coins");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("coins")))
                .collect(Collectors.toList());

        Pair<String, Integer> thenumber = top.get(newpos);
        int returnValue = thenumber.getValue();

        return returnValue;
    }

    public String topKillsName(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("kills");
                    int points2 = ((MemorySection) a2.getValue()).getInt("kills");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("kills")))
                .collect(Collectors.toList());

        Pair<String, Integer> thename = top.get(newpos);
        String name = thename.getKey();
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return player.getName();
    }

    public int topKillsValue(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("kills");
                    int points2 = ((MemorySection) a2.getValue()).getInt("kills");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("kills")))
                .collect(Collectors.toList());

        Pair<String, Integer> thenumber = top.get(newpos);
        int returnValue = thenumber.getValue();

        return returnValue;
    }

    public String topDeathsName(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("deaths");
                    int points2 = ((MemorySection) a2.getValue()).getInt("deaths");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("deaths")))
                .collect(Collectors.toList());

        Pair<String, Integer> thename = top.get(newpos);
        String name = thename.getKey();
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return player.getName();
    }

    public int topDeathsValue(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("deaths");
                    int points2 = ((MemorySection) a2.getValue()).getInt("deaths");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("deaths")))
                .collect(Collectors.toList());

        Pair<String, Integer> thenumber = top.get(newpos);
        int returnValue = thenumber.getValue();

        return returnValue;
    }


    public String topKSName(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("best-killstreak");
                    int points2 = ((MemorySection) a2.getValue()).getInt("best-killstreak");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("best-killstreak")))
                .collect(Collectors.toList());

        Pair<String, Integer> thename = top.get(newpos);
        String name = thename.getKey();
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return player.getName();
    }

    public int topKSValue(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.data.config.getConfigurationSection("");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("best-killstreak");
                    int points2 = ((MemorySection) a2.getValue()).getInt("best-killstreak");
                    return points2 - points1;
                })
                .map(e -> Pair.of(Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName(), ((MemorySection) e.getValue()).getInt("best-killstreak")))
                .collect(Collectors.toList());

        Pair<String, Integer> thenumber = top.get(newpos);
        int returnValue = thenumber.getValue();
        return returnValue;
    }

    public String topClanName(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.clansData.config.getConfigurationSection("clans");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("kills");
                    int points2 = ((MemorySection) a2.getValue()).getInt("kills");
                    return points2 - points1;
                })
                .map(e -> Pair.of(e.getKey(), ((MemorySection) e.getValue()).getInt("kills")))
                .collect(Collectors.toList());

        Pair<String, Integer> thename = top.get(newpos);
        String name = thename.getKey();
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);

        return player.getName();
    }

    public int topClanValue(int position){
        int newpos = position - 1;
        ConfigurationSection cf = plugin.clansData.config.getConfigurationSection("clans");

        List<Pair<String, Integer>> top = cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("kills");
                    int points2 = ((MemorySection) a2.getValue()).getInt("kills");
                    return points2 - points1;
                })
                .map(e -> Pair.of(e.getKey(), ((MemorySection) e.getValue()).getInt("kills")))
                .collect(Collectors.toList());

        Pair<String, Integer> thenumber = top.get(newpos);
        int returnValue = thenumber.getValue();
        return returnValue;
    }

}
