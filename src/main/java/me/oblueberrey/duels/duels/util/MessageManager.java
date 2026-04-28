package me.oblueberrey.duels.duels.util;

import me.oblueberrey.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageManager {

    private final Duels plugin;

    public MessageManager(Duels plugin) {
        this.plugin = plugin;
    }

    public void send(CommandSender sender, String path) {
        List<String> msg = plugin.getMessages().getStringList(path);

        for (String line : msg) {
            sender.sendMessage(color(line));
        }
    }

    public void send(CommandSender sender, String path, String placeholder, String value) {
        List<String> msg = plugin.getMessages().getStringList(path);

        for (String line : msg) {
            sender.sendMessage(color(line.replace(placeholder, value)));
        }
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public void reload() {
        // re-read messages.yml into memory
    }
}