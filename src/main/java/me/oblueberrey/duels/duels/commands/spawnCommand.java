package me.oblueberrey.duels.duels.commands;

import me.oblueberrey.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class spawnCommand implements CommandExecutor {
    private final Duels plugin;

    public spawnCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            List<String> messageList = plugin.getMessages().getStringList("permissions.no-perms");
            for (String line : messageList) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        }


        if(plugin.getConfig().get("spawn.location") == null){

        }

        Player  player = (Player) sender;
        Location loc = plugin.getConfig().getLocation("spawn.location");
        player.teleport(loc);
        List<String> messageList = plugin.getMessages().getStringList("teleport-message");
        for (String line : messageList) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
        return true;
    }
}
