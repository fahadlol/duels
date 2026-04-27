package me.oblueberrey.duels;

import me.oblueberrey.duels.config.ConfigManager;
import me.oblueberrey.duels.duels.commands.acceptDuelCommand;
import me.oblueberrey.duels.duels.commands.admin.duelAdminCommand;
import me.oblueberrey.duels.duels.commands.admin.setSpawnCommand;
import me.oblueberrey.duels.duels.commands.declineDuelCommand;
import me.oblueberrey.duels.duels.commands.duelCommand;
import me.oblueberrey.duels.duels.commands.spawnCommand;
import me.oblueberrey.duels.duels.duelManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Duels extends JavaPlugin {

    private duelManager duelManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {

        // commands
        getCommand("duel").setExecutor(new duelCommand(this));
        getCommand("acceptduel").setExecutor(new acceptDuelCommand(this));
        getCommand("declineduel").setExecutor(new declineDuelCommand(this));
        getCommand("setspawn").setExecutor(new setSpawnCommand(this));
        getCommand("dueladmin").setExecutor(new duelAdminCommand(this));
        getCommand("spawn").setExecutor(new spawnCommand(this));





        this.configManager = new ConfigManager(this);
        this.duelManager = new duelManager(this);

        saveDefaultConfig();
        loadConfigs();

        getLogger().info("Duels enabled.");
    }

    private void loadConfigs() {
        configManager.loadConfig("messages.yml");
        configManager.loadConfig("arenas.yml");
        configManager.loadConfig("kits.yml");
    }

    @Override
    public void onDisable() {
        getLogger().info("Duels disabled.");
    }

    public duelManager getDuelManager() {
        return duelManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FileConfiguration getMessages() {
        return configManager.getConfig("messages.yml");
    }


}