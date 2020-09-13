package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.customevents.KitSelectEvent;
import network.outflowkits.kitpvp.kits.*;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KitSelector implements Listener {
    private KitPvP plugin;
    private DecimalFormat df = new DecimalFormat("###,###,###,###.##");
    public KitSelector(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public ItemStack getKitUnlockers(Player player){
        PlayerManagement management = new PlayerManagement(player);

        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lKit Unlockers"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7You have &a" + management.getKitUnlockers() + " &7Kit Unlockers left!"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void kitselectoruse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null)return;
        if (!(player.getInventory().getItemInHand().hasItemMeta()))return;
        if (!(player.getInventory().getItemInHand().getItemMeta().hasDisplayName()))return;
        if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&BKit Selector"))){
            openselector(player);
        }
    }

    private void openselector(Player player) {
        if (!Utils.isInMainWorld(player)){
            Utils.sendMessage(player, "&cYou cannot use this in this world.");
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Select a Kit");

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, filler);
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, filler);
        }
        inv.setItem(9, PvP.getSelectorIcon(player));
        inv.setItem(49, getKitUnlockers(player));

        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Barbarian")){
            inv.addItem(Barbarian.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Chemist")){
            inv.addItem(Chemist.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Fisherman")){
            inv.addItem(Fisherman.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Avatar")){
            inv.addItem(Avatar.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Kidnapper")){
            inv.addItem(Kidnapper.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Mario")){
            inv.addItem(Mario.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Kangaroo")){
            inv.addItem(Kangaroo.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Ninja")){
            inv.addItem(Ninja.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Stomper")){
            inv.addItem(Stomper.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Dwarf")){
            inv.addItem(Dwarf.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Teleporter")){
            inv.addItem(Teleporter.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Switcher")){
            inv.addItem(Switcher.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Gank")){
            inv.addItem(Gank.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Snowman")){
            inv.addItem(Dwarf.getSelectorIcon(player));
        }
        if (management.getUnlockedKits().contains("Tank")){
            inv.addItem(Tank.getSelectorIcon(player));
        }

        player.openInventory(inv);
    }


    @EventHandler
    public void selectorinvclick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null)return;
        if (event.getView().getTitle().equals(ChatColor.AQUA + "Select a Kit")){
            event.setCancelled(true);
            if (!(event.getCurrentItem().hasItemMeta()))return;
            if (!(event.getCurrentItem().getItemMeta().hasDisplayName()))return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            // PvP Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&aPvP"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                PvP.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "PvP"));
            }
            // Barbarian Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&bBarbarian"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Barbarian.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Barbarian"));
            }

            // Chemist Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&dChemist"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Chemist.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Chemist"));
            }
            // Fisherman Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&eFisherman"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Fisherman.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Fisherman"));
            }
            // Avatar Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&6Avatar"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Avatar.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Avatar"));
            }
            // Kidnapper Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&cKidnapper"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Kidnapper.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Kidnapper"));
            }

            // Mario Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&4Mario"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Mario.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Mario"));
            }
            // Kangaroo Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&9Kangaroo"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Kangaroo.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Kangaroo"));
            }
            // Ninja Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&cNinja"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Ninja.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Ninja"));
            }
            // Stomper Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&9Stomper"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Stomper.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Stomper"));
            }
            // Dwarf Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&6Dwarf"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Dwarf.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Dwarf"));
            }
            // Teleporter Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&5Teleporter"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Teleporter.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Teleporter"));
            }
            // Switcher Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&3Switcher"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Switcher.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Switcher"));
            }
            // Gank Kit
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&9Gank"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Gank.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Gank"));
            }
            // Tank
            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&4Tank"))){
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                Tank.getKit(player);
                player.closeInventory();

                Bukkit.getPluginManager().callEvent(new KitSelectEvent(player, "Tank"));
            }

            if (name.equals(ChatColor.translateAlternateColorCodes('&', "&a&lKit Unlockers"))){
                PlayerManagement management = new PlayerManagement(player);
                if (management.getKitUnlockers() == 0) {
                    Utils.sendMessage(player, "&cYou do not have any Kit Unlockers left.");
                    Utils.playSound(player, Sound.VILLAGER_NO);
                    openselector(player);
                    return;
                }
                List<String> unlockable = management.getKits();
                List<String> unlocked = management.getUnlockedKits();

                Random random = new Random();
                int randomNumber = random.nextInt(unlockable.size());
                String unlockerSelected = unlockable.get(randomNumber);

                for (String kitsunlocked : unlocked) {
                    if (unlockerSelected.equals(kitsunlocked)) {
                        management.removeKitUnlockers(1);
                        management.addCoins(5000);
                        Utils.sendMessage(player, "&7The Kit Unlocker has granted you the &a" + unlockerSelected + " &7kit, but because you have it already unlocked you have been given &a5,000 Coins&7.");
                        Utils.playSound(player, Sound.ANVIL_USE);
                        openselector(player);
                        return;
                    }
                }
                management.unlockKit(unlockerSelected);
                Utils.playSound(player, Sound.ANVIL_USE);
                Utils.sendMessage(player, "&7The Kit Unlocker has granted you the &a" + unlockerSelected + " &7kit.");
                openselector(player);
            }
        }
    }
}
