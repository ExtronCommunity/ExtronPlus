package com.redsponge.extron.plus;

import com.redsponge.extron.plus.event.PlayerInteractionEvent;
import com.redsponge.extron.plus.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExtronPlus extends JavaPlugin implements Listener {

    public static ExtronPlus INSTANCE;

    public List<Enchantment> customEnchants = new ArrayList<>();

    public void onEnable() {
        INSTANCE = this;
        registerEvents();
        registerEnchants();
    }

    public void onDisable() {
        unregisterEnchants();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractionEvent(), this);
        pm.registerEvents(this,this);
    }

    private void registerCommands() {
        //getCommand("TEMPLATE").setExecutor(null);
    }

    private void registerEnchants() {
        //customEnchants.add(new MyEnchant());
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
