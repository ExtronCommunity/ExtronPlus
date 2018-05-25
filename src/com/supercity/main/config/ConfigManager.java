package com.supercity.main.config;

import com.supercity.main.SuperCity;
import com.supercity.main.utils.Reference;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static FileConfiguration config;
    public static ConfigFile backpackConfig;
    public static ConfigFile cityDataConfig;

    public static void init() {
        config = SuperCity.INSTANCE.getConfig();
        backpackConfig = new ConfigFile("backpacks.yml");
        cityDataConfig = new ConfigFile("city.yml");
        cityDataConfig.get().addDefault(Reference.CITY_COORDS_CONFIG_PATH + ".x", 0);
        cityDataConfig.get().addDefault(Reference.CITY_COORDS_CONFIG_PATH + ".y", 0);
        cityDataConfig.get().addDefault(Reference.CITY_COORDS_CONFIG_PATH + ".z", 0);
        cityDataConfig.save();
    }

    public static void saveConfig() {
        SuperCity.INSTANCE.saveConfig();
    }

}
