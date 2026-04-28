package me.oblueberrey.duels.duels.util;

import me.oblueberrey.duels.Duels;

public class ReloadService {

    private final Duels plugin;

    public ReloadService(Duels plugin) {
        this.plugin = plugin;
    }

    public void reloadAll() {
        plugin.getConfigManager().reloadConfigs();
        plugin.getDuelManager().reload();
        plugin.getMessageManager().reload();
    }
}