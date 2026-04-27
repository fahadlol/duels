package me.oblueberrey.duels.duels.commands.admin;

import me.oblueberrey.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class duelAdminCommand implements CommandExecutor {
    private final Duels plugin;

    public duelAdminCommand(Duels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender.hasPermission("duels.setspawn"))) {
            List<String> messageList = plugin.getMessages().getStringList("permissions.no-perms");
            for (String line : messageList) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            return true;
        }
        Player player = (Player) sender;


        if (args.length == 0) {
            List<String> messageList = plugin.getMessages().getStringList("dueladmin.help-message");
            for (String line : messageList) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        String arg1 = args[0].toLowerCase();
        switch (args[0]) {
            case "setspawn":
                setSpawn(player);
                break;
            case "arena":
                switch (args[1]) {
                    case "create":
                        // create logic
                        if(args.length != 3) {
                            List<String> messageList = plugin.getMessages().getStringList("dueladmin.arena-create-usage");
                            for (String line : messageList) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                            }

                        }
                        createArena(args[2],  player);

                    case "pos":
                        switch (args[2]) {
                            case "1":
                                //code
                            case "2":
                                //code
                        }
                    case "wandcopy":
                        //get arena name and check worldedit
                        break;
                    case "setspawn":
                        switch (args[2]) {
                            case "1":
                                //code
                            case "2":
                                //code
                        }
                    case "delete":
                        //code
                    case "enable":
                        //code
                    default:
                        List<String> messageList = plugin.getMessages().getStringList("dueladmin.admin-help-message");
                        for (String line : messageList) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }

                }
            case "kit":
                switch (args[1]) {
                    case "create":
                        //code
                    case "delete":
                        //code
                    case "set":
                        //code
                    case "load":
                        //code
                    default:
                        List<String> messageList = plugin.getMessages().getStringList("dueladmin.kit-help-message");
                        for (String line : messageList) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                }
            default:
                List<String> messageList = plugin.getMessages().getStringList("dueladmin.kit-help-message");
                for (String line : messageList) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                }

        }

        return true;
    }

    public void setSpawn(Player player) {
        plugin.getDuelManager().setSpawn(player);
    }
    public void createArena(String arenaName, Player player) {
        if(plugin.getDuelManager().arenaExists(arenaName)){
            List<String> messageList = plugin.getMessages().getStringList("dueladmin.arena-create-exists");
            for (String line : messageList) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            return;
        }
        FileConfiguration arenas = plugin.getConfigManager().getConfig("arenas.yml");
        arenas.set("arenas.yml", arenaName);

    }

}