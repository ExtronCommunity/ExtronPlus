package com.supercity.main.crafting;

import com.supercity.main.SuperCity;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeUncraftQuartz extends ShapelessRecipe {
    public RecipeUncraftQuartz() {
        super(new NamespacedKey(SuperCity.INSTANCE,"unquartz"), new ItemStack(Material.QUARTZ,4));
        this.addIngredient(Material.QUARTZ_BLOCK);
    }
}
