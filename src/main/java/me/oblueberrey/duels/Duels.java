package me.oblueberrey.duels;

import me.oblueberrey.duels.duels.duelManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Duels extends JavaPlugin {

    private duelManager duelManager;

    @Override
    public void onEnable() {
        this.duelManager = new duelManager(this);
        getLogger().info("Duels enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Duels disabled.");
    }

    public duelManager getDuelManager() {
        return duelManager;
    }
}