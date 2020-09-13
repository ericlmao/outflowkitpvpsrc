package network.outflowkits.kitpvp.kits.selector;

import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class KitShop {
    private KitPvP plugin;
    private Player player;
    DecimalFormat df = new DecimalFormat("###,###,###,###");
    public KitShop(Player player) {
        plugin = KitPvP.getPlugin(KitPvP.class);
        this.player = player;
    }

    public void openShop(){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Shop");

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

        inv.setItem(9, getBarbarianKit());
        inv.setItem(10, getChemistKit());
        inv.setItem(11, getFishermanKit());
        inv.setItem(12, getAvatarKit());
        inv.setItem(13, getKidnapperKit());
        inv.setItem(14, getMarioKit());
        inv.setItem(15, getKangarooKit());
        inv.setItem(16, getNinjaKit());
        inv.setItem(17, getStomperKit());
        inv.setItem(18, getDwarfKit());
        inv.setItem(19, getTeleporterKit());
        inv.setItem(20, getSwitcherKit());
        inv.setItem(21, getGankKit());
        inv.setItem(22, getTankKit());

        player.openInventory(inv);
    }

    private ItemStack getKidnapperKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);
        DecimalFormat df = new DecimalFormat("###,###,###,### Coins");

        if (management.getUnlockedKits().contains("Kidnapper")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Kidnapper");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.SADDLE;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Kidnapper");
            String kit = "Kidnapper";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getAvatarKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Avatar")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Avatar");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.LEATHER_HELMET;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Avatar");
            String kit = "Avatar";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getFishermanKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Fisherman")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Fisherman");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.FISHING_ROD;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Fisherman");
            String kit = "Fisherman";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getChemistKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Chemist")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Chemist");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.POTION;

            ItemStack item = new ItemStack(material, 1, (short) 16460);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Chemist");
            String kit = "Chemist";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getBarbarianKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Barbarian")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Barbarian");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.STONE_AXE;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Barbarian");
            String kit = "Barbarian";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getMarioKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Mario")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Mario");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.FIREBALL;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Mario");
            String kit = "Mario";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getKangarooKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Kangaroo")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Kangaroo");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.LEATHER_BOOTS;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Kangaroo");
            String kit = "Kangaroo";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getNinjaKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Ninja")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Ninja");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.NETHER_STAR;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Ninja");
            String kit = "Ninja";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getStomperKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Stomper")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Stomper");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.ANVIL;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Stomper");
            String kit = "Stomper";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }
    private ItemStack getDwarfKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Dwarf")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Dwarf");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.GOLD_AXE;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Dwarf");
            String kit = "Dwarf";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getTeleporterKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Teleporter")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Teleporter");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.ENDER_PORTAL_FRAME;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Teleporter");
            String kit = "Teleporter";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getSwitcherKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Switcher")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Switcher");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.SNOW_BALL;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Switcher");
            String kit = "Switcher";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getGankKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Gank")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Gank");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.STONE_SWORD;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Gank");
            String kit = "Gank";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

    private ItemStack getTankKit() {
        Material material;
        PlayerManagement management = new PlayerManagement(player);

        if (management.getUnlockedKits().contains("Tank")){
            material = Material.BEDROCK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.RED + "Tank");
            meta.setLore(Arrays.asList("", ChatColor.translateAlternateColorCodes('&', "&4&lYOU ALREADY HAVE THIS KIT UNLOCKED")));
            item.setItemMeta(meta);

            return item;
        } else {
            material = Material.DIAMOND_CHESTPLATE;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + "Tank");
            String kit = "Tank";
            int cost = Utils.getKitPrice(kit);
            ArrayList<String> lore = new ArrayList<>();

            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: &6" + df.format(cost)));
            lore.add(ChatColor.translateAlternateColorCodes('&', " "));
            lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to purchase"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }

}
