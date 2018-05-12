package com.supercity.main.config;

import com.supercity.main.SuperCity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private FileConfiguration config;
    private File f;
    private String path;

    public ConfigFile(String path) {
        this.path = path;
        File dataFolder = SuperCity.INSTANCE.getDataFolder();
        if(!dataFolder.exists()) {
            if(dataFolder.mkdir()) {
                System.out.println("Creating Data Folder");
            } else {
                System.out.println("Data Folder Already Exists");
            }
        }
        this.f = new File(dataFolder.toString() + File.separator + path);
        if(!f.exists()) {
            try {
                if(f.createNewFile()) {
                    System.out.println("Creating new file for config " + path);
                } else {
                    System.out.println("Using already made file for config " + path);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(f);
    }

    public FileConfiguration get() {
        return config;
    }

    public boolean save() {
        try {
            config.save(f);
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
