package com.azortis.playerwarps.command;

import com.azortis.playerwarps.PlayerWarps;

public class Injected {
    private PlayerWarps playerWarps;

    public Injected(PlayerWarps playerWarps) {
        this.playerWarps = playerWarps;
    }

    public PlayerWarps getPlayerWarps() {
        return playerWarps;
    }
}
