package com.supercity.main.item;

import com.supercity.main.config.ConfigManager;
import com.supercity.main.nbt.NBTUtils;
import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reference.ItemData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class ItemBackPack extends CustomItem {

    public static final HashMap<Integer, ItemBackPack> backpacks = new HashMap<>();
    public static final HashMap<Inventory, Integer> backpackInventories = new HashMap<>();

    public static int idCount = ConfigManager.backpackConfig.get().getInt("idCount", 0);

    public ItemBackPack() {
        super(ItemData.BACKPACK);
    }

    public static void assignID(ItemStack item) {
        assignID(item, idCount);
        idCount++;
        ConfigManager.backpackConfig.get().set("idCount", idCount);
        ConfigManager.backpackConfig.save();
    }

    public static void assignID(ItemStack item, int id) {
        ItemMeta meta = item.getItemMeta();
        meta.setLocalizedName(ItemData.BACKPACK.getLocName() + id);
        System.out.println("Assigned id " + id + " to backpack!");
        updateIDLore(item, id);
        item.setItemMeta(meta);

    }

    public static void updateIDLore(ItemStack item, int id) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "#" + id));
        item.setItemMeta(meta);
    }

    public static void displayToPlayer(Player p, int id) {
        updateIDLore(p.getInventory().getItemInMainHand(), id);
        Inventory inventory = Bukkit.createInventory(null, 9 * Reference.ROWS_IN_BACKPACK, Reference.BACKPACK_TITLE);
        ItemStack[] items = getItemsFromConfig(id);
        for(int i = 0; i < Reference.ROWS_IN_BACKPACK * 9; i++) {
            inventory.setItem(i, items[i]);
        }
        backpackInventories.put(inventory, id);
        p.openInventory(inventory);
    }

    public static void close(Inventory inv) {
        if(!backpackInventories.keySet().contains(inv)) throw new RuntimeException("Backpack isn't opened! cannot be closed");
        saveToConfig(inv.getContents(), backpackInventories.get(inv));
    }

    public static void saveToConfig(ItemStack[] items, int id) {
        for(int i = 0; i < Reference.ROWS_IN_BACKPACK * 9; i++){
            saveItemToConfig(i, items[i], id);
        }
    }

    public static void saveItemToConfig(int index, ItemStack item, int id) {
        try {
            String mat = item.getType().toString();
            int count = item.getAmount();
            short damage = item.getDurability();
            String configPath = "" + id + "." + index;
            String nbt = NBTUtils.getStringNBT(item);
            ConfigManager.backpackConfig.get().set(configPath + ".material", mat);
            ConfigManager.backpackConfig.get().set(configPath + ".count", count);
            ConfigManager.backpackConfig.get().set(configPath + ".damage", damage);
            ConfigManager.backpackConfig.get().set(configPath + ".nbt", nbt);
            ConfigManager.backpackConfig.save();
        } catch (Exception e) {
            ConfigManager.backpackConfig.get().set("" + id + "." + index, null);
            ConfigManager.backpackConfig.save();
        }
    }

    public static ItemStack[] getItemsFromConfig(int id) {
        ItemStack[] items = new ItemStack[Reference.ROWS_IN_BACKPACK * 9];
        for(int i = 0; i < Reference.ROWS_IN_BACKPACK * 9; i++) {
            items[i] = loadItemFromConfig(i, id);
        }
        return items;
    }

    public static ItemStack loadItemFromConfig(int index, int id) {
        FileConfiguration config = ConfigManager.backpackConfig.get();
        String configPath = "" + id + "." + index;
        String material = config.getString(configPath + ".material");
        int count = config.getInt(configPath + ".count");
        short damage = (short) config.getInt(configPath + ".damage");
        try {
            ItemStack item = new ItemStack(Material.valueOf(material), count, damage);
            String nbt = config.getString(configPath + ".nbt");
            if(!nbt.trim().isEmpty()) {
                item = NBTUtils.applyStringNBTToItem(item, config.getString(configPath + ".nbt"));
            }
            return item;
        } catch(Exception e) {
            return new ItemStack(Material.AIR);
        }
    }

    public static void loadAllBackpacks() {
        Set<String> hashes = ConfigManager.backpackConfig.get().getKeys(false);
        System.out.println(hashes.size());
    }


    public static boolean isBackpack(ItemStack i) {
        try {
            return i.getItemMeta().getLocalizedName().startsWith(ItemData.BACKPACK.getLocName());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasId(ItemStack i) {
        try {
            int id = Integer.parseInt(i.getItemMeta().getLocalizedName().substring(ItemData.BACKPACK.getLocName().length()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getBackpackId(ItemStack i) {
        return Integer.parseInt(i.getItemMeta().getLocalizedName().substring(ItemData.BACKPACK.getLocName().length()));
    }

}
