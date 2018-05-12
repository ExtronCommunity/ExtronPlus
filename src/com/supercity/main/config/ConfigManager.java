package com.supercity.main.config;

import com.supercity.main.SuperCity;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static FileConfiguration config;
    public static ConfigFile backpackConfig;

    public static void init() {
        config = SuperCity.INSTANCE.getConfig();
        backpackConfig = new ConfigFile("backpacks.yml");
    }

    public static void saveConfig() {
        SuperCity.INSTANCE.saveConfig();
    }

}
