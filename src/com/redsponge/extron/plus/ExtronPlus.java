package com.redsponge.extron.plus;

import com.redsponge.extron.plus.commands.CommandGetCustomItem;
import com.redsponge.extron.plus.crafting.RecipeJetpack;
import com.redsponge.extron.plus.event.PlayerDieEvent;
import com.redsponge.extron.plus.event.PlayerInteractionEvent;
import com.redsponge.extron.plus.event.PlayerJoinGameEvent;
import com.redsponge.extron.plus.event.PlayerToggleShiftEvent;
import com.redsponge.extron.plus.jetpack.JetpackHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtronPlus extends JavaPlugin {

    public static ExtronPlus INSTANCE;
    private JetpackHandler jetpackHandler;

    public void onEnable() {
        INSTANCE = this;
        registerCommands();
        registerEvents();
        registerHandlers();
        registerRecipes();

        initiatePlayers();
        getLogger().info(ChatColor.GREEN.toString() + "Extron Plus has been successfully loaded!");
    }

    public void onDisable() {

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractionEvent(), this);
        pm.registerEvents(new PlayerJoinGameEvent(), this);
        pm.registerEvents(new PlayerDieEvent(), this);
        pm.registerEvents(new PlayerToggleShiftEvent(), this);
    }

    private void registerCommands() {
        getCommand("getCustomItem").setExecutor(new CommandGetCustomItem());
    }

    private void registerHandlers() {
        jetpackHandler = new JetpackHandler();
    }

    private void initiatePlayers() {
        for(Player p : getServer().getOnlinePlayers()) {
            jetpackHandler.initiatePlayer(p);
        }
    }

    private void registerRecipes() {
        getServer().addRecipe(new RecipeJetpack());
    }

    public JetpackHandler getJetpackHandler() {
        return jetpackHandler;
    }
}
