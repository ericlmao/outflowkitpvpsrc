package network.outflowkits.clans.commands;

import net.md_5.bungee.api.chat.*;
import network.outflowkits.KitPvP;
import network.outflowkits.clans.ClansManager;
import network.outflowkits.kitpvp.management.CooldownManagement;
import network.outflowkits.kitpvp.management.Leaderboard;
import network.outflowkits.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class ClanCMD implements CommandExecutor {
    private KitPvP plugin;
    public ClanCMD() {
        plugin = KitPvP.getPlugin(KitPvP.class);
    }

    public ClansManager getManager(){
        return new ClansManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0){
            Utils.sendMessage(player, "&6--- Outflow Clans ---");
            Utils.sendMessage(player, "&e/clan create (name)");
            Utils.sendMessage(player, "&e/clan disband");
            Utils.sendMessage(player, "&e/clan invite (player)");
            Utils.sendMessage(player, "&e/clan kick (player)");
            Utils.sendMessage(player, "&6------------------");
            return true;
        }

        switch (args[0].toLowerCase()){
            case "create":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan create (name)");
                    return true;
                }
                if (getManager().clanExists(args[1])){
                    Utils.sendMessage(player, "&6[Clans] &eThis Clan already exists.");
                    return true;
                }
                if (getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are already in a Clan.");
                    return true;
                }
                if (args[1].length() >= 16){
                    Utils.sendMessage(player, "&6[Clans] &eYou cannot have a Clan name longer than 16 characters.");
                    return true;
                }
                if (args[1].length() <= 2){
                    Utils.sendMessage(player, "&6[Clans] &eYou cannot have a Clan name shorter than 2 characters.");
                    return true;
                }

                if (args[1].contains(" ") || args[1].contains("!") || args[1].contains("/") || args[1].contains("\"") || args[1].contains("#") || args[1].contains("$")
                        || args[1].contains("%") || args[1].contains("^") || args[1].contains("&") || args[1].contains("*") || args[1].contains("(") || args[1].contains(")") ||
                        args[1].contains("{") || args[1].contains("}") || args[1].contains("[") || args[1].contains("]") || args[1].contains("|") || args[1].contains("'") ||
                        args[1].contains(":") || args[1].contains(";") || args[1].contains(",") || args[1].contains(".") || args[1].contains("<") || args[1].contains(">") ||
                        args[1].contains("§")){
                    Utils.sendMessage(player, "&6[Clans] &eYour Clan name contains one or more illegal characters.");
                    return true;
                }

                getManager().createClan(args[1], player.getUniqueId());

                Utils.sendMessage(player, "&6[Clans] &eCreated Clan &6" + args[1] + "&e.");
                return true;
            }
            case "disband":{
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                if (!getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are not the Leader of the Clan.");
                    return true;
                }
                getManager().disbandClan(getManager().getClan(player.getUniqueId()));
                Utils.sendMessage(player, "&6[Clans] &eYou have disbanded your Clan.");
                return true;
            }
            case "invite":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan invite (player)");
                    return true;
                }
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                if (!getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are not the Leader of the Clan.");
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UUID targetId = target.getUniqueId();

                if (getManager().alreadyInClan(targetId)){
                    Utils.sendMessage(player, "&6[Clans] &eThis player is already in a Clan.");
                    return true;
                }

                String playerClan = getManager().getClan(player.getUniqueId());
                ArrayList<String> activeInvites = getManager().getInvitations(playerClan);
                if (activeInvites.contains(targetId.toString())){
                    Utils.sendMessage(player, "&6[Clans] &eYou have already invited this player to your Clan, if you wish to uninvite this player" +
                            ", do &6/clan uninvite (player)&e.");
                    return true;
                }
                getManager().invite(getManager().getClan(player.getUniqueId()), targetId);
                Utils.sendMessage(player, "&6[Clans] &eYou have invited &6" + target.getName() + " &eto the Clan.");

                if (target instanceof Player){
                    Player p = Bukkit.getPlayer(targetId);

                    TextComponent component = new TextComponent(TextComponent.fromLegacyText(
                            ChatColor.translateAlternateColorCodes('&',
                                    "&6[Clans] &eYou have been invited to the &6" + getManager().getClan(player.getUniqueId())
                                            + " &eClan. &7(Click Here)")));

                    // Add a click event to the component.
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan join " + getManager().getClan(player.getUniqueId())));
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join " +
                            getManager().getClan(player.getUniqueId())).color(ChatColor.GOLD.asBungee()).create()));

                    // Send it!
                    p.spigot().sendMessage(component);
                }
                return true;
            }
            case "uninvite":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan invite (player)");
                    return true;
                }
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                if (!getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are not the Leader of the Clan.");
                    return true;
                }
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UUID targetId = target.getUniqueId();

                String playerClan = getManager().getClan(player.getUniqueId());
                ArrayList<String> activeInvites = getManager().getInvitations(playerClan);
                if (!activeInvites.contains(targetId.toString())){
                    Utils.sendMessage(player, "&6[Clans] &eThis player does not have an active Clan invitation.");
                    return true;
                }
                getManager().uninvite(getManager().getClan(player.getUniqueId()), targetId);
                Utils.sendMessage(player, "&6[Clans] &eYou have revoked &6" + target.getName() + "&e's Clan invitation.");

                if (target instanceof Player){
                    Player p = Bukkit.getPlayer(targetId);
                    Utils.sendMessage(p, "&6[Clans] &eYour invitation to &6" + getManager().getClan(player.getUniqueId()) + " &ehas been revoked.");
                }

                return true;
            }
            case "kick":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan kick (player)");
                    return true;
                }
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                if (!getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are not the Leader of the Clan.");
                    return true;
                }
                String clan = getManager().getClan(player.getUniqueId());
                ArrayList<String> members = getManager().getMembers(clan);

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UUID targetId = target.getUniqueId();

                if (player.getUniqueId().equals(targetId)){
                    Utils.sendMessage(player, "&6[Clans] &eYou cannot kick the Leader from the Clan.");
                    return true;
                }
                if (!members.contains(targetId.toString())){
                    Utils.sendMessage(player, "&6[Clans] &eThis player is not in your Clan.");
                    return true;
                }
                String oldClan = getManager().getClan(targetId);
                getManager().leaveClan(targetId);

                sendMessageToClan(oldClan, "&6[Clans] &6" + target.getName() + " &ehas been kicked from the Clan.");

                if (target instanceof Player){
                    Player p = (Player) target;
                    Utils.sendMessage(p, "&6[Clans] &eYou have been kicked from the Clan.");
                }
                return true;
            }
            case "join":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan join (clan)");
                    return true;
                }
                if (!getManager().clanExists(args[1])){
                    Utils.sendMessage(player, "&6[Clans] &eThis Clan does not exist.");
                    return true;
                }
                if (getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are already in a Clan.");
                    return true;
                }
                String clanName = getManager().getClanName(args[1]);
                ArrayList<String> clanInvites = getManager().getInvitations(clanName);
                if (!clanInvites.contains(player.getUniqueId().toString())){
                    Utils.sendMessage(player, "&6[Clans] &eYou do not have any active invitations from this Clan.");
                    return true;
                }
                if (getManager().getMembers(clanName).size() >= 10){
                    Utils.sendMessage(player, "&6[Clans] &eThe Clan is currently full!");
                    return true;
                }
                getManager().joinClan(player.getUniqueId(), clanName);

                sendMessageToClan(clanName, "&6[Clans] &6" + player.getName() + " &ehas joined the Clan!");
                return true;
            }
            case "leave":{
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                if (getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are the leader of this Clan!");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eYou can disband the Clan by doing &6/clan disband&e.");
                    Utils.sendMessage(player, "&eOr by transferring ownership to a fellow member by doing &6/clan setOwner (player)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                String oldClan = getManager().getClan(player.getUniqueId());
                getManager().leaveClan(player.getUniqueId());

                sendMessageToClan(oldClan, "&6[Clans] &6" + player.getName() + " &ehas left the Clan!");

                Utils.sendMessage(player, "&6[Clans] &eYou have left the Clan.");
            }
            case "setowner":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan setOwner (player)");
                    return true;
                }
                if (!getManager().isLeader(player.getUniqueId())){
                    Utils.sendMessage(player, "&6[Clans] &eYou are not the Leader of the Clan.");
                    return true;
                }
                String clan = getManager().getClan(player.getUniqueId());
                ArrayList<String> members = getManager().getMembers(clan);

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UUID targetId = target.getUniqueId();

                if (player.getUniqueId().equals(targetId)){
                    Utils.sendMessage(player, "&6[Clans] &eYou are already the Leader!");
                    return true;
                }
                if (!members.contains(targetId.toString())){
                    Utils.sendMessage(player, "&6[Clans] &eThis player is not in your Clan.");
                    return true;
                }
                getManager().transferOwnership(targetId);

                sendMessageToClan(getManager().getClan(targetId), "&6[Clans] &6" + player.getName() + " &ehas transferred ownership of the Clan to &6" + target.getName() + "&e!");
                return true;
            }
            case "info":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan info (clan)");
                    return true;
                }
                if (!getManager().clanExists(args[1])){
                    Utils.sendMessage(player, "&6[Clans] &eThis Clan does not exists.");
                    return true;
                }
                String rawName = args[1];
                String clanName = getManager().getClanName(rawName);

                ArrayList<String> membersUUID = getManager().getMembers(clanName);

                ArrayList<String> memberNames = new ArrayList<>();
                for (String member : membersUUID){
                    UUID memberUUIDS = UUID.fromString(member);
                    OfflinePlayer memberPlayer = Bukkit.getOfflinePlayer(memberUUIDS);
                    memberNames.add(memberPlayer.getName());
                }

                DecimalFormat df = new DecimalFormat("###,###,###,###.##");

                int kills = getManager().getKills(clanName);
                int deaths = getManager().getDeaths(clanName);

                double kdrkills = getManager().getKills(clanName);
                double kdrdeaths = getManager().getDeaths(clanName);

                double kdr = kdrkills / kdrdeaths;

                Utils.sendMessage(player, "&6----< &e" + clanName + " &6>----");
                Utils.sendMessage(player, " ");
                Utils.sendMessage(player, "&6Kills: &f" + df.format(kills));
                Utils.sendMessage(player, "&6Deaths: &f" + df.format(deaths));
                Utils.sendMessage(player, "&6KDR: &f" + df.format(kdr));
                Utils.sendMessage(player, " ");
                Utils.sendMessage(player, "&6Leader: &f" + getManager().getLeader(clanName).getName());
                Utils.sendMessage(player, " ");
                Utils.sendMessage(player, "&6Members (" + memberNames.size() + ")");

                for (String memberList : memberNames){
                    Utils.sendMessage(player, " &6- &f" + memberList);
                }

                Utils.sendMessage(player, " ");
                Utils.sendMessage(player, "&6----< &e" + clanName + " &6>----");
                return true;
            }
            case "top":{
                CooldownManagement cooldown = new CooldownManagement(player);

                if (cooldown.hasCooldown("Leaderboard")){
                    long current = cooldown.getCooldown("Leaderboard");

                    Utils.sendMessage(player, "&cPlease wait &e" + cooldown.formatCooldown(current) + " &cbefore refreshing the leaderboard again!");
                    return true;
                }
                cooldown.setCooldown("Leaderboard", 10);

                Leaderboard leaderboard = new Leaderboard();
                Utils.sendMessage(player, "&8&m-----------------------------------");
                Utils.sendMessage(player, "&6&lOutflow Clans Leaderboard");
                Utils.sendMessage(player, "&7&oThis leaderboard is determined by the amount");
                Utils.sendMessage(player, "&7&oof kills a Clan has!");
                Utils.sendMessage(player, " ");
                Utils.sendMessage(player, "&61. &e" + leaderboard.topClanName(1) + "&e: &f" + leaderboard.topClanValue(1));
                Utils.sendMessage(player, "&62. &e" + leaderboard.topClanName(2) + "&e: &f" + leaderboard.topClanValue(2));
                Utils.sendMessage(player, "&63. &e" + leaderboard.topClanName(3) + "&e: &f" + leaderboard.topClanValue(3));
                Utils.sendMessage(player, "&8&m-----------------------------------");
                return true;
            }
            case "chat":{
                if (args.length == 1){
                    Utils.sendMessage(player, "&e/clan chat (message)");
                    return true;
                }
                if (!getManager().alreadyInClan(player.getUniqueId())){
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&6&lOutflow Clans");
                    Utils.sendMessage(player, "&eYou are not in a Clan.");
                    Utils.sendMessage(player, " ");
                    Utils.sendMessage(player, "&eIf you would like to join a Clan, get invited to one and do &6/clan join (name)&e!");
                    Utils.sendMessage(player, "&eIf you wish to create a Clan, do &6/clan create (name)&e!");
                    Utils.sendMessage(player, " ");
                    return true;
                }
                final StringBuilder msgBuilder = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    msgBuilder.append(args[i]).append(" ");
                }
                final String msg = msgBuilder.toString().trim();

                String clan = getManager().getClan(player.getUniqueId());
                String clanName = getManager().getClanName(clan);
                for (Player p : Bukkit.getOnlinePlayers()){
                    String playerClan = getManager().getClan(p.getUniqueId());
                    if (playerClan == null)continue;
                    if (playerClan.equalsIgnoreCase(clanName)){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Clan Chat&7] &6&o" + player.getName() + "&6: &e") + msg);
                    }
                }

                return true;
            }
            default:{
                Utils.sendMessage(player, "&6[Clans] &eInvalid argument!");
                return true;
            }
        }
    }
    public void sendMessageToClan(String clan, String message){
        String clanName = getManager().getClanName(clan);
        for (Player p : Bukkit.getOnlinePlayers()){
            String playerClan = getManager().getClan(p.getUniqueId());
            if (playerClan == null)continue;
            if (playerClan.equalsIgnoreCase(clanName)){
                Utils.sendMessage(p, message);
            }
        }
    }
}
