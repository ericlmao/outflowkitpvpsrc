package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.customevents.KitSelectEvent;
import network.outflowkits.kitpvp.kits.*;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;

public class RecentKit implements Listener {
    private KitPvP plugin;
    public RecentKit(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    @EventHandler
    public void use(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null)return;
        if (!(player.getInventory().getItemInHand().hasItemMeta()))return;
        if (!(player.getInventory().getItemInHand().getItemMeta().hasDisplayName()))return;
        if (!Utils.isInMainWorld(player)){
            Utils.sendMessage(player, "&cYou cannot use this in this world.");
            return;
        }
        if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eSelect Recent Kit"))){
            selectRecentKit(player);
        }
    }

    private void selectRecentKit(Player player) {
        String kit = plugin.recentKit.get(player);

        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.closeInventory();
        switch (kit.toLowerCase()){

            case "pvp":{
                PvP.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "barbarian":{
                Barbarian.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "chemist":{
                Chemist.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "fisherman":{
                Fisherman.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "avatar":{
                Avatar.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "kidnapper":{
                Kidnapper.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "mario":{
                Mario.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "kangaroo":{
                Kangaroo.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "ninja":{
                Ninja.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "stomper":{
                Stomper.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "dwarf":{
                Dwarf.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "teleporter":{
                Teleporter.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "switcher":{
                Switcher.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "gank":{
                Gank.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
            case "tank":{
                Tank.getKit(player);
                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, kit));
                return;
            }
        }
    }
}
