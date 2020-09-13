package network.outflowkits.kitpvp.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitPurchaseEvent extends Event {
    boolean cancelled;
    String kit;
    int cost;
    Player player;

    public KitPurchaseEvent(Player player, String kit, int cost){
        this.kit = kit;
        this.player = player;
        this.cost = cost;
    }

    public Player getPlayer(){ return this.player; }

    public String getKit(){return this.kit; }

    public int getCost(){return this.cost;}


    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
