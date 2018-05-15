package com.supercity.main;

import com.supercity.main.commands.*;
import com.supercity.main.backpack.ItemBackPack;
import com.supercity.main.commands.CommandDontSkipNight;
import com.supercity.main.commands.CommandGetCustomItem;
import com.supercity.main.commands.CommandReEnableOnePlayerSleep;
import com.supercity.main.config.ConfigManager;
import com.supercity.main.crafting.RecipeBackpack;
import com.supercity.main.crafting.RecipeCraftingStick;
import com.supercity.main.crafting.RecipeJetpack;
import com.supercity.main.crafting.SmeltingIncSack;
import com.supercity.main.enchants.*;
import com.supercity.main.event.*;
import com.supercity.main.event.ItemEnchantEvent;
import com.supercity.main.event.PlayerCloseInventoryEvent;
import com.supercity.main.event.PlayerCraftItemEvent;
import com.supercity.main.event.PlayerDieEvent;
import com.supercity.main.event.PlayerInteractionEvent;
import com.supercity.main.event.PlayerJoinGameEvent;
import com.supercity.main.event.PlayerToggleShiftEvent;
import com.supercity.main.event.custom.CustomEventListener;
import com.supercity.main.jetpack.JetpackHandler;
import com.supercity.main.sleep.OnePlayerSleepHandler;
import com.supercity.main.spawner.SpawnerMovingHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SuperCity extends JavaPlugin implements Listener {

    public static SuperCity INSTANCE;
    private JetpackHandler jetpackHandler;
    private OnePlayerSleepHandler onePlayerSleepHandler;

    public List<CustomEnchant> customEnchants = new ArrayList<>();

    public void onEnable() {
        INSTANCE = this;
        registerCommands();
        onePlayerSleepHandler = new OnePlayerSleepHandler();
        registerEvents();
        registerHandlers();

        initiatePlayers();
        registerEnchants();
        getLogger().info(ChatColor.GREEN.toString() + "Extron Plus has been successfully loaded!");
        ConfigManager.init();
        ItemBackPack.loadAllBackpacks();

        registerRecipes();
    }

    public void onDisable() {
        //RecordingManager.setAllNotAFK();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractionEvent(), this);
        pm.registerEvents(new PlayerJoinGameEvent(), this);
        pm.registerEvents(new PlayerDieEvent(), this);
        pm.registerEvents(new PlayerToggleShiftEvent(), this);
        pm.registerEvents(new ItemEnchantEvent(), this);
        pm.registerEvents(new CustomEventListener(), this);
        pm.registerEvents(new SpawnerMovingHandler(), this);
        pm.registerEvents(new MobSpawnEvent(),this);
        //pm.registerEvents(new ChatMessageEvent(),this);
        //pm.registerEvents(new PlayerMovedEvent(),this);
        pm.registerEvents(this,this);
        pm.registerEvents(onePlayerSleepHandler, this);
        //Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperCity.INSTANCE, RecordingManager::tick, 0, 1);
        pm.registerEvents(new PlayerCloseInventoryEvent(), this);
        pm.registerEvents(new PlayerCraftItemEvent(), this);
        pm.registerEvents(new LegacyEnchantConvertor(),this);
    }

    private void registerCommands() {
        getCommand("getCustomItem").setExecutor(new CommandGetCustomItem());
        getCommand("dontSkipNight").setExecutor(new CommandDontSkipNight());
        getCommand("enableOnePlayerSleep").setExecutor(new CommandReEnableOnePlayerSleep());
        getCommand("customEnchant").setExecutor(new CommandCustomEnchant());
        //getCommand("recording").setExecutor(new CommandRecording());
        //getCommand("togglescoreboard").setExecutor(new CommandToggleSB());
        //getCommand("togglechat").setExecutor(new CommandToggleChat());
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
        getServer().addRecipe(new RecipeCraftingStick());
        getServer().addRecipe(new RecipeBackpack());

        getServer().addRecipe(new SmeltingIncSack());
    }

    public JetpackHandler getJetpackHandler() {
        return jetpackHandler;
    }

    private void registerEnchants() {
        customEnchants.add(new CurseOfBreaking());
        customEnchants.add(new HeatWalker());
        customEnchants.add(new Lifesteal());
        customEnchants.add(new IceAspect());
        customEnchants.add(new AutoSmelt());
        customEnchants.add(new Sniper());
        customEnchants.add(new FancyTrail());

        customEnchants.forEach(n -> Bukkit.getPluginManager().registerEvents(n,SuperCity.this));
    }
    public OnePlayerSleepHandler getOnePlayerSleepHandler() {
        return onePlayerSleepHandler;
    }

    public CustomEnchant getCustomEnchant(String id) {
        for (CustomEnchant e : customEnchants) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }
}
