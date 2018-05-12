package com.supercity.main.crafting;

import com.supercity.main.SuperCity;
import com.supercity.main.backpack.ItemBackPack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeBackpack extends ShapedRecipe {

    public RecipeBackpack() {
        super(new NamespacedKey(SuperCity.INSTANCE, "recipeBackpack"), new ItemBackPack());
        shape("LLL","LCL","III");
        setIngredient('L', Material.LEATHER);
        setIngredient('C', Material.CHEST);
        setIngredient('I', Material.IRON_INGOT);
    }
}
