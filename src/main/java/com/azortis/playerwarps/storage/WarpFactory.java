package com.azortis.playerwarps.storage;


import org.bukkit.Location;

import java.util.Set;
import java.util.UUID;

public class WarpFactory {
    private UUID player;
    private Location location;
    private Set<UUID> listedUsers;
    private boolean blacklist;

    public WarpFactory setPlayer(UUID player) {
        this.player = player;
        return this;
    }

    public WarpFactory setLocation(Location location) {
        this.location = location;
        return this;
    }

    public WarpFactory setListedUsers(Set<UUID> listedUsers) {
        this.listedUsers = listedUsers;
        return this;
    }

    public WarpFactory setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
        return this;
    }

    public Warp build() {
        return new Warp(player, location, listedUsers, blacklist);
    }
}
