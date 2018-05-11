package com.supercity.main.backpack;

import com.supercity.main.item.CustomItem;
import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reference.ItemData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemBackPack extends CustomItem {

    public static final HashMap<ItemStack, ItemBackPack> backpacks = new HashMap<>();

    private ItemStack[] items;

    public ItemBackPack() {
        super(ItemData.BACKPACK);
        items = new ItemStack[Reference.ROWS_IN_BACKPACK * 9];
        backpacks.put(this, this);
    }

    public void updateLore() {

    }

    public void displayToPlayer(Player p) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CREATIVE);
        p.openInventory(inventory);
    }

    public static boolean isBackpack(ItemStack i) {
        return backpacks.keySet().contains(i);
    }

    public static ItemBackPack toBackpack(ItemStack i) {
        return backpacks.get(i);
    }

}
