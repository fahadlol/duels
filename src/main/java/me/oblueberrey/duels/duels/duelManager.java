package me.oblueberrey.duels.duels;

import me.oblueberrey.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class duelManager {
    private final Duels plugin;

    HashMap<UUID, UUID> requestedDuels = new HashMap<>();
    HashMap<UUID, UUID> requestedBy = new HashMap<>();
    HashMap<UUID, UUID> perparingDuel = new HashMap<>();

    public duelManager(Duels plugin) {
        this.plugin = plugin;
    }

    public void addRequestedPlayers(UUID requester, UUID target){
        requestedDuels.put(requester, target);
        requestedBy.put(target, requester);
    }

    public void removeRequestedPlayer(UUID requester){
        UUID target = requestedDuels.remove(requester);
        if (target != null) {
            requestedBy.remove(target);
        }
    }

    public UUID getRequestedPlayer(UUID requester){
        return requestedDuels.get(requester);
    }

    public UUID getRequester(UUID target){
        return requestedBy.get(target);
    }

    public boolean isRequested(UUID requester){
        return requestedDuels.containsKey(requester);
    }

    public boolean hasRequest(UUID target){
        return requestedBy.containsKey(target);
    }

    public void transferToPerparing(UUID requester, UUID target){
        if (requestedDuels.containsKey(requester) && requestedDuels.get(requester).equals(target)) {
            perparingDuel.put(requester, target);
            requestedDuels.remove(requester);
            requestedBy.remove(target);
        }
    }
    public void prepareDuel(UUID requester, UUID target) {
        if (!(perparingDuel.containsKey(requester) && perparingDuel.containsKey(target))) {
            perparingDuel.remove(requester);

            Player targetPlayer = plugin.getServer().getPlayer(target);
            Player requesterPlayer = plugin.getServer().getPlayer(requester);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                plugin.getMessageManager().send(targetPlayer, "duels.duel-failed");
            }
            if(requesterPlayer != null && requesterPlayer.isOnline()) {
                plugin.getMessageManager().send(requesterPlayer, "duels.duel-failed");
            }
        }
    }
    public void setSpawn(Player player){

        Location loc = player.getLocation();
        plugin.getConfig().set("spawn.location", loc);
        plugin.saveConfig();
        player.sendMessage("§aSpawn set");
    }
    public void sendSpawn(Player player){
        if(plugin.getConfig().get("spawn.location") == null){
            plugin.getMessageManager().send(player, "util.spawn.spawn-not-found");
            return;
        }
        Location loc = plugin.getConfig().getLocation("spawn.location");
        player.teleport(loc);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.SURVIVAL);
        plugin.getMessageManager().send(player, "teleport-message");

    }
    public boolean arenaExists(String name) {
        return plugin.getConfigManager()
                .getConfig("arenas.yml")
                .contains("arenas." + name);
    }
    public boolean kitExists(String name){
        return plugin.getConfigManager()
                .getConfig("kits.yml")
                .contains("kits." + name);
    }
    public void reload() {
        // clear caches, re-read arenas/kits from files
    }

}