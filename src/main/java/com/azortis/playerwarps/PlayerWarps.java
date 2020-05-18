package com.azortis.playerwarps;

import com.azortis.playerwarps.command.WarpCommand;
import com.azortis.playerwarps.hook.VaultHook;
import com.azortis.playerwarps.impl.inventory.InventoryListener;
import com.azortis.playerwarps.inventory.InventoryManager;
import com.azortis.playerwarps.storage.SettingsManager;
import com.azortis.playerwarps.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerWarps extends JavaPlugin {
    private VaultHook vaultHook;
    private InventoryManager inventoryManager;
    private SettingsManager settingsManager;
    private StorageManager storageManager;

    public StorageManager getStorageManager() {
        return storageManager;
    }

    @Override
    public void onEnable() {
        InventoryListener.initialize(this);
        getLogger().info("Getting Settings");
        settingsManager = new SettingsManager(this);
        getLogger().info("Connecting to storage database.");
        storageManager = new StorageManager(this);
        vaultHook = new VaultHook();
        if (settingsManager.getSettings().isEconomyIntegration() && !vaultHook.initialize()) {
            getLogger().info("Shutting down plugin. Could not detect Vault dependency!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Creating Inventories");
        inventoryManager = new InventoryManager(this);

        getLogger().info("Creating Commands");
        new WarpCommand(this);

        getLogger().info("Setup finished.");

    }

    @Override
    public void onDisable() {
        getLogger().info("Closing all inventories.");
        inventoryManager.closeInventories();
        storageManager.closeConnections();
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
