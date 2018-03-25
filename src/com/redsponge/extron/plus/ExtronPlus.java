package com.redsponge.extron.plus;

import com.redsponge.extron.plus.event.PlayerInteractionEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtronPlus extends JavaPlugin {

    public static ExtronPlus INSTANCE;

    public void onEnable() {
        INSTANCE = this;
        registerEvents();
    }

    public void onDisable() {

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractionEvent(), this);
    }

    private void registerCommands() {
        //getCommand("TEMPLATE").setExecutor(null);
    }
}
