package com.azortis.playerwarps.storage;

import org.bukkit.Location;

import java.util.Set;
import java.util.UUID;

public class Warp {
    private UUID player, warpUUID;
    private Location location;
    private Set<UUID> listedUsers;
    private boolean blacklist;


    public UUID getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public Set<UUID> getListedUsers() {
        return listedUsers;
    }

    public boolean isBlacklist() {
        return blacklist;
    }
}
