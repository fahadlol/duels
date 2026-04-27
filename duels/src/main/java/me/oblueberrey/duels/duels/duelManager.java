package me.oblueberrey.duels.duels;

import me.oblueberrey.duels.Duels;

import java.util.HashMap;
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
}