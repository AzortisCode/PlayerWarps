package com.azortis.playerwarps.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultHook implements IHook<Economy> {
    private Economy economy;

    @Override
    public boolean initialize() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null)
            economy = rsp.getProvider();
        return rsp != null;
    }

    @Override
    public Economy getHookData() {
        if(economy == null) initialize();
        return economy;
    }
}
