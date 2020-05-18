package com.azortis.playerwarps.storage;

public class Settings {
    private boolean economyIntegration, particles;
    private String mainMenuTitle;

    public String getMainMenuTitle() {
        return mainMenuTitle;
    }

    public void setMainMenuTitle(String mainMenuTitle) {
        this.mainMenuTitle = mainMenuTitle;
    }

    public boolean isParticles() {
        return particles;
    }

    public void setParticles(boolean particles) {
        this.particles = particles;
    }

    private double warpCost, creationCost, maxWarps;

    public boolean isEconomyIntegration() {
        return economyIntegration;
    }

    public void setEconomyIntegration(boolean economyIntegration) {
        this.economyIntegration = economyIntegration;
    }

    public double getWarpCost() {
        return warpCost;
    }

    public void setWarpCost(double warpCost) {
        this.warpCost = warpCost;
    }

    public double getCreationCost() {
        return creationCost;
    }

    public void setCreationCost(double creationCost) {
        this.creationCost = creationCost;
    }

    public double getMaxWarps() {
        return maxWarps;
    }

    public void setMaxWarps(double maxWarps) {
        this.maxWarps = maxWarps;
    }
}
