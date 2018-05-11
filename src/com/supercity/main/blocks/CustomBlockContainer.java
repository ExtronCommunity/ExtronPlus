package com.supercity.main.blocks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomBlockContainer extends CustomBlock {

    public boolean onInsertItem(Player p, ItemStack inserted) {
        return false;
    }

}
