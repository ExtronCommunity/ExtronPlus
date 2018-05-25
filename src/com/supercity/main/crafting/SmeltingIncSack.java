package com.supercity.main.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class SmeltingIncSack extends FurnaceRecipe {
    public SmeltingIncSack() {
        super(new ItemStack(Material.INK_SACK), new MaterialData(Material.FEATHER),0.7f);
    }
}
