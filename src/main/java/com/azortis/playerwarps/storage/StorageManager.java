package com.azortis.playerwarps.storage;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.Injected;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class StorageManager extends Injected implements Listener {
    private StorageTask storageTask;
    private Timer timer;
    private Map<UUID, Set<Warp>> cachedWarps;

    public StorageManager(PlayerWarps playerWarps) {
        super(playerWarps);
        Bukkit.getPluginManager().registerEvents(this, playerWarps);
        cachedWarps = new HashMap<>();
        timer = new Timer();
        storageTask = new StorageTask();
        timer.scheduleAtFixedRate(storageTask, TimeUnit.SECONDS.toMillis(30), TimeUnit.MINUTES.toMillis(30));
    }

    public Set<Warp> getWarp(UUID uuid) {
        return cachedWarps.get(uuid);
    }

    public void addWarp(Warp warp) {
        // todo: add warps to storage task and map
        // todo: Actually make storage task and retrieval part...
    }

    public Set<Warp> getWarps() {
        Set<Warp> warpSet = new HashSet<>();
        cachedWarps.values().forEach(warpSet::addAll);
        return warpSet;
    }

    public void closeConnections() {
        timer.cancel();
    }

}
