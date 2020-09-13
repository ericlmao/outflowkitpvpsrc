package network.outflowkits.kitpvp.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitSelectEvent extends Event {
    boolean cancelled;
    String kit;
    Player player;

    public KitSelectEvent(Player player, String kit){
        this.kit = kit;
        this.player = player;
    }

    public Player getPlayer(){ return this.player; }

    public String getKit(){return this.kit; }

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
