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
            plugin.getMessageManager().send(sender, "permissions.no-perms");
            return true;
        }

        Player  player = (Player) sender;
        plugin.getDuelManager().sendSpawn(player);
        return true;
    }
}
