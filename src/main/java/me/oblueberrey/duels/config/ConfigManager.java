package me.oblueberrey.duels.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> files = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig(String name) {
        File file = new File(plugin.getDataFolder(), name);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        files.put(name, file);
        configs.put(name, config);
    }

    public FileConfiguration getConfig(String name) {
        return configs.get(name);
    }

    public void reloadConfig(String name) {
        File file = files.get(name);
        if (file != null) {
            configs.put(name, YamlConfiguration.loadConfiguration(file));
        }
    }

    public void saveConfig(String name) {
        try {
            configs.get(name).save(files.get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reloadConfigs() {
        reloadConfig("kits.yml");
        reloadConfig("arenas.yml");
        reloadConfig("messages.yml");
    }
}