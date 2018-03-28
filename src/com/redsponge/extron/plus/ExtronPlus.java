package com.redsponge.extron.plus;

import com.redsponge.extron.plus.commands.CommandGetCustomItem;
import com.redsponge.extron.plus.crafting.RecipeJetpack;
import com.redsponge.extron.plus.enchants.CurseOfBreaking;
import com.redsponge.extron.plus.enchants.HeatWalker;
import com.redsponge.extron.plus.event.PlayerDieEvent;
import com.redsponge.extron.plus.event.PlayerInteractionEvent;
import com.redsponge.extron.plus.event.PlayerJoinGameEvent;
import com.redsponge.extron.plus.event.PlayerToggleShiftEvent;
import com.redsponge.extron.plus.event.*;
import com.redsponge.extron.plus.jetpack.JetpackHandler;
import com.redsponge.extron.plus.utils.Reflection;
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

public class ExtronPlus extends JavaPlugin implements Listener {

    public static ExtronPlus INSTANCE;
    private JetpackHandler jetpackHandler;

    public List<Enchantment> customEnchants = new ArrayList<>();

    public void onEnable() {
        INSTANCE = this;
        registerCommands();
        registerEvents();
        registerHandlers();
        registerRecipes();

        initiatePlayers();
        getLogger().info(ChatColor.GREEN.toString() + "Extron Plus has been successfully loaded!");
        registerEnchants();
    }

    public void onDisable() {
        unregisterEnchants();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractionEvent(), this);
        pm.registerEvents(new PlayerJoinGameEvent(), this);
        pm.registerEvents(new PlayerDieEvent(), this);
        pm.registerEvents(new PlayerToggleShiftEvent(), this);
        pm.registerEvents(new ItemEnchantEvent(), this);
        pm.registerEvents(this,this);
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
                if (byId.containsKey(e.getId())) {
                    byId.remove(e.getId());
                }
                if (byName.containsKey(e.getName())) {
                    byName.remove(e.getName());
                }
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
}
