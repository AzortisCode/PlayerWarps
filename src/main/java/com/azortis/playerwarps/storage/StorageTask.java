package com.azortis.playerwarps.storage;

import java.util.Collection;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class StorageTask extends TimerTask {
    private volatile Set<Warp> dataStore;
    private Timer t;

    @Override
    public void run() {

    }

    public void storeData(Collection collection) {

    }

    public void addStorage(Warp warp) {
        dataStore.add(warp);
    }

    public void addStorage(Collection<Warp> collection) {
        dataStore.addAll(collection);
    }
}
