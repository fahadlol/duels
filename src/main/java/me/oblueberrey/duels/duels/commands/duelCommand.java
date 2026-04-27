package me.oblueberrey.duels.duels.commands;

import me.oblueberrey.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class duelCommand implements CommandExecutor {

    private final Duels plugin;

    public duelCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){sender.sendMessage("§cOnly player may run this command"); return true;}
        if(!(sender.hasPermission("duels.sendDuel"))){
            List<String> messageList = plugin.getMessages().getStringList("permissions.no-perms");
            for (String line : messageList) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            return true;}

        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage("§c/duel target");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage("§cPlayer not found");
            return true;
        } else if (!(target.isOnline())) {
            player.sendMessage("§cPlayer is not online");
            return true;
        }

        plugin.getDuelManager().addRequestedPlayers(target.getUniqueId(), player.getUniqueId());
        player.sendMessage("§aDuel request sent");
        target.sendMessage("§eYou have been requested to duel " + player.getDisplayName());
        

        return true;
    }
}
