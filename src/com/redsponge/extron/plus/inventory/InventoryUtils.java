package com.redsponge.extron.plus.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static void removeOneItemFromInventory(Player p, Material mat) {
        for(int i = 0; i < 36; i++) {
            ItemStack it = p.getInventory().getItem(i);
            if(it != null) {
                if (it.getType() == mat) {
                    it.setAmount(it.getAmount() - 1);
                    return;
                }
            }
        }
    }

}
