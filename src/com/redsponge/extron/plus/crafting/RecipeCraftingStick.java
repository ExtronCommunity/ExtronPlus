package com.redsponge.extron.plus.crafting;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.item.CraftingTableStick;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeCraftingStick extends ShapelessRecipe {

    public RecipeCraftingStick() {
        super(new NamespacedKey(ExtronPlus.INSTANCE, "recipeCraftingStick"), new CraftingTableStick());
        addIngredient(Material.SIGN);
        addIngredient(Material.WORKBENCH);
    }
}
