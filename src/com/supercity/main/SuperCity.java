package com.supercity.main;

import com.supercity.main.commands.*;
import com.supercity.main.crafting.RecipeCraftingStick;
import com.supercity.main.crafting.RecipeJetpack;
import com.supercity.main.enchants.CurseOfBreaking;
import com.supercity.main.enchants.HeatWalker;
import com.supercity.main.event.*;
import com.supercity.main.event.custom.CustomEventListener;
import com.supercity.main.jetpack.JetpackHandler;
import com.supercity.main.recording.RecordingManager;
import com.supercity.main.sleep.OnePlayerSleepHandler;
import com.supercity.main.spawner.SpawnerMovingHandler;
import com.supercity.main.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuperCity extends JavaPlugin implements Listener {

    public static SuperCity INSTANCE;
    private JetpackHandler jetpackHandler;
    private OnePlayerSleepHandler onePlayerSleepHandler;

    public List<Enchantment> customEnchants = new ArrayList<>();

    public void onEnable() {
        INSTANCE = this;
        registerCommands();
        onePlayerSleepHandler = new OnePlayerSleepHandler();
        registerEvents();
        registerHandlers();
        registerRecipes();

        initiatePlayers();
        getLogger().info(ChatColor.GREEN.toString() + "Extron Plus has been successfully loaded!");
        registerEnchants();
    }

    public void onDisable() {
        unregisterEnchants();
        RecordingManager.setAllNotAFK();
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
        pm.registerEvents(new ChatMessageEvent(),this);
        pm.registerEvents(new PlayerMovedEvent(),this);
        pm.registerEvents(this,this);
        pm.registerEvents(onePlayerSleepHandler, this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperCity.INSTANCE, RecordingManager::tick, 0, 1);
    }

    private void registerCommands() {
        getCommand("getCustomItem").setExecutor(new CommandGetCustomItem());
        getCommand("dontSkipNight").setExecutor(new CommandDontSkipNight());
        getCommand("enableOnePlayerSleep").setExecutor(new CommandReEnableOnePlayerSleep());
        getCommand("recording").setExecutor(new CommandRecording());
        getCommand("togglescoreboard").setExecutor(new CommandToggleSB());
        getCommand("togglechat").setExecutor(new CommandToggleChat());
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
    }

    public JetpackHandler getJetpackHandler() {
        return jetpackHandler;
    }

    private void registerEnchants() {
        customEnchants.add(new CurseOfBreaking());
        customEnchants.add(new HeatWalker());
        try {
            Reflection.setField(null,Enchantment.class,"acceptingNew",true);
            for (Enchantment e : customEnchants) {
                Enchantment.registerEnchantment(e);
                if (e instanceof Listener) {
                    Bukkit.getPluginManager().registerEvents((Listener) e,this);
                }
            }
        } catch (IllegalArgumentException ignored) {

        }
    }

    private void unregisterEnchants() {
        try {
            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) Reflection.getField(null, Enchantment.class, "byId");
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) Reflection.getField(null, Enchantment.class, "byName");
            for (Enchantment e : customEnchants) {
                byId.remove(e.getId());
                byName.remove(e.getName());
            }
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }
    }

    public Enchantment getCustomEnchant(int id) {
        for (Enchantment e : customEnchants) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public OnePlayerSleepHandler getOnePlayerSleepHandler() {
        return onePlayerSleepHandler;
    }
}
