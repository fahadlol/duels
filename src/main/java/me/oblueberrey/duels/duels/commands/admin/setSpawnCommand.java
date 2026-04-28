package me.oblueberrey.duels.duels.commands.admin;

import me.oblueberrey.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class setSpawnCommand implements CommandExecutor {
    private final Duels plugin;

    public setSpawnCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){sender.sendMessage("§cOnly players can do this.");return true;}
        if(!(sender.hasPermission("duels.setspawn"))){
            plugin.getMessageManager().send(sender, "permissions.no-perms");
            return true;}

        Player player = (Player) sender;

        if(args.length > 0){
            plugin.getMessageManager().send(player, "setspawn.help-message");
        }


        return true;
    }
}
