package com.supercity.main.crafting;

import com.supercity.main.SuperCity;
import com.supercity.main.item.CraftingTableStick;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeCraftingStick extends ShapelessRecipe {

    public RecipeCraftingStick() {
        super(new NamespacedKey(SuperCity.INSTANCE, "recipeCraftingStick"), new CraftingTableStick());
        addIngredient(Material.SIGN);
        addIngredient(Material.WORKBENCH);
    }
}
