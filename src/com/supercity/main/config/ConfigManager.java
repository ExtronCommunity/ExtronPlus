package com.supercity.main.config;

import com.supercity.main.SuperCity;
import com.supercity.main.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    public static FileConfiguration config;
    public static ConfigFile backpackConfig;
    public static ConfigFile trappedSignsConfig;

    public static void init() {
        config = SuperCity.INSTANCE.getConfig();
        backpackConfig = new ConfigFile("backpacks.yml");
        trappedSignsConfig = new ConfigFile("trapped_signs.yml");
        List<Location> locations = Reference.TRAPPED_SIGNS_LOCATIONS;
        for (String s : trappedSignsConfig.get().getKeys(false)) {
            ConfigurationSection section = trappedSignsConfig.get().getConfigurationSection(s);
            Location loc = new Location(Bukkit.getWorld(section.getString("world")),section.getInt("x"),section.getInt("y"),section.getInt("z"));
            locations.add(loc);
        }
    }

    public static void saveConfig() {
        SuperCity.INSTANCE.saveConfig();
    }

}
