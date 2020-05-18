package com.azortis.playerwarps.storage;

import com.azortis.playerwarps.PlayerWarps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

public class SettingsManager {
    private enum Path {
        ECONOMY_INTEGRATION("economyIntegration"),
        DEFAULT_WARP("warpDefaults"),
        WARP_COST("defaultWarpCost"),
        CREATION_COST("defaultCreationCost"),
        MAX_WARPS("defaultMaxWarps"),
        PARTICLES("particles"),
        CUSTOMIZATION("customization"),
        FILE_VERSION("fileVersion"),
        MAIN_MENU_TITLE("mainMenuTitle");
        private String path;

        Path(String path) {

            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private File settingsFile;
    private Map<String, Object> settingsMap;
    private Settings settings;
    private final double VERSION = 1.0;

    public SettingsManager(PlayerWarps plugin) {
        if (plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        settingsFile = new File(plugin.getDataFolder(), "conf.json");
        if (!settingsFile.exists()) {
            plugin.saveResource(settingsFile.getName(), false);
        }
        try {
            settingsMap = gson.fromJson(new FileReader(settingsFile), Map.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if ((Double) settingsMap.get(Path.FILE_VERSION.getPath()) != VERSION) {
            plugin.getLogger().info("ERROR: File Version not up to date. Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        settings = new Settings();
        fillSettings();
    }

    public void saveSettingsFile() {
        saveSettings();
        try {
            final String json = gson.toJson(settingsMap);
            settingsFile.delete();
            Files.write(settingsFile.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadSettingsFile() {
        try {
            settingsMap = gson.fromJson(new FileReader(settingsFile), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings = new Settings();
        fillSettings();
    }

    public Settings getSettings() {
        return settings;
    }

    private void fillSettings() {
        // -----------------------------------------
        settings.setEconomyIntegration((Boolean) settingsMap.get(Path.ECONOMY_INTEGRATION.getPath()));
        // -----------------------------------------
        Map<String, Object> defaultWarps = (Map<String, Object>) settingsMap.get(Path.DEFAULT_WARP.getPath());
        settings.setCreationCost((Double) defaultWarps.get(Path.CREATION_COST.getPath()));
        settings.setMaxWarps((Double) defaultWarps.get(Path.MAX_WARPS.getPath()));
        settings.setWarpCost((Double) defaultWarps.get(Path.WARP_COST.getPath()));
        // -------------
        //
        // ----------------------------
        Map<String, Object> customization = (Map<String, Object>) settingsMap.get(Path.CUSTOMIZATION.getPath());
        settings.setParticles((Boolean) customization.get(Path.PARTICLES.getPath()));
        settings.setMainMenuTitle((String) customization.get(Path.MAIN_MENU_TITLE.getPath()));
    }

    private void saveSettings() {
        // -----------------------------------------
        Map<String, Object> defaultWarps = (Map<String, Object>) settingsMap.get(Path.DEFAULT_WARP.getPath());
        defaultWarps.put(Path.CREATION_COST.getPath(), settings.getCreationCost());
        defaultWarps.put(Path.WARP_COST.getPath(), settings.getWarpCost());
        defaultWarps.put(Path.MAX_WARPS.getPath(), settings.getMaxWarps());
        settingsMap.put(Path.DEFAULT_WARP.getPath(), defaultWarps);
        // -----------------------------------------
        settingsMap.put(Path.ECONOMY_INTEGRATION.getPath(), settings.isEconomyIntegration());
        // -----------------------------------------
        Map<String, Object> customization = (Map<String, Object>) settingsMap.get(Path.CUSTOMIZATION.getPath());
        customization.put(Path.PARTICLES.getPath(), settings.isParticles());
        customization.put(Path.MAIN_MENU_TITLE.getPath(), settings.getMainMenuTitle());
        // -----------------------------------------
        settingsMap.put(Path.CUSTOMIZATION.getPath(), customization);
    }

}
