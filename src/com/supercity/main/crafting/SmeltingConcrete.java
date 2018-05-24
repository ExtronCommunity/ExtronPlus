package com.supercity.main.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class SmeltingConcrete extends FurnaceRecipe {

    public SmeltingConcrete(ItemStack result, MaterialData source) {
        super(result, source);
    }

    public static void addAll() {
        for (int i = 0; i < 16; i++) {
            add(i);
        }
    }

    private static void add(int i) {
        MaterialData data = new MaterialData(Material.CONCRETE);
        data.setData((byte)i);
        SmeltingConcrete sc = new SmeltingConcrete(new ItemStack(Material.CONCRETE_POWDER,1,(byte)i),data);
        Bukkit.addRecipe(sc);
    }

}
