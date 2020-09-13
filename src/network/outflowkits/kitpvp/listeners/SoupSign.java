package network.outflowkits.kitpvp.listeners;

import net.md_5.bungee.api.ChatColor;
import network.outflowkits.KitPvP;
import network.outflowkits.kitpvp.management.PlayerManagement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoupSign implements Listener {
    private KitPvP plugin;
    public SoupSign(){
        plugin = KitPvP.getPlugin(KitPvP.class);
    }
    @EventHandler
    public void soupsign(SignChangeEvent event){
        if (event.getLine(0).equalsIgnoreCase("[soup]")){
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', "&9[Soup]"));
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', "&eRight-Click &8for"));
            event.setLine(2, ChatColor.translateAlternateColorCodes('&', "&8free Soup!"));
        }
    }

    @EventHandler
    public void soupsignevent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK))return;
        if (!(event.getClickedBlock().getState() instanceof Sign))return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!(sign.getLine(0).equals(ChatColor.translateAlternateColorCodes('&', "&9[Soup]"))))return;
        if (!(sign.getLine(1).equals(ChatColor.translateAlternateColorCodes('&', "&eRight-Click &8for"))))return;
        if (!(sign.getLine(2).equals(ChatColor.translateAlternateColorCodes('&', "&8free Soup!"))))return;

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Soup Refill");

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        PlayerManagement management = new PlayerManagement(player);
        ItemStack healing;
        if (management.potionsEnabled()){
            healing = new ItemStack(Material.POTION, 1, (short) 16421);
        } else {
            healing = new ItemStack(Material.MUSHROOM_SOUP);
        }
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, healing);
        }

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, filler);
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, filler);
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void soupinvclick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null)return;
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Soup Refill")){
            if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void soupinvclose(InventoryCloseEvent event){
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Soup Refill")){
            event.getPlayer().getItemOnCursor().setType(Material.AIR);
        }
    }
}
