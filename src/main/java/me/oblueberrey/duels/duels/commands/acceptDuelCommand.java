package me.oblueberrey.duels.duels.commands;

import me.oblueberrey.duels.Duels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class acceptDuelCommand implements CommandExecutor {
    private final Duels plugin;

    public acceptDuelCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can run this command");
            return true;
        }

        Player player = (Player) sender;
        UUID requesterUUID = plugin.getDuelManager().getRequester(player.getUniqueId());
        Player requester = Bukkit.getPlayer(requesterUUID);


        if (!player.hasPermission("duels.acceptduel")) {
            player.sendMessage("§cYou don't have the permissions to run this command");
            return true;
        }

        if (args.length != 0) {
            player.sendMessage("§cIncorrect syntax /acceptduel");
            return true;
        }


        if (requesterUUID == null) {
            player.sendMessage("§7You have no duel requests");
            return true;
        }

        plugin.getDuelManager().transferToPerparing(requesterUUID, player.getUniqueId());

        return true;
    }
}