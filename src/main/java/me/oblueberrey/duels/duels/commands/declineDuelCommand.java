package me.oblueberrey.duels.duels.commands;

import me.oblueberrey.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class declineDuelCommand implements CommandExecutor {

    private final Duels plugin;

    public declineDuelCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cOnly players can run this command");
            return true;
        }

        Player player = (Player) commandSender;
        UUID requesterUUID = plugin.getDuelManager().getRequester(player.getUniqueId());
        Player requester = Bukkit.getPlayer(requesterUUID);


        if (!player.hasPermission("duels.acceptduel")) {
            List<String> messageList = plugin.getMessages().getStringList("permissions.no-perms");
            for (String line : messageList) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }               return true;
        }


        return true;
    }
}
