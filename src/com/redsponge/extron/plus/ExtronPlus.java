package com.redsponge.extron.plus;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtronPlus extends JavaPlugin {

    public void onEnable() {

    }

    public void onDisable() {

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

    }

    private void registerCommands() {
        //getCommand("TEMPLATE").setExecutor(null);
    }
}
