package com.redsponge.extron.plus.crafting;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.jetpack.ItemJetpack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeJetpack extends ShapedRecipe {

    public RecipeJetpack() {
        super(new NamespacedKey(ExtronPlus.INSTANCE, "recipeJetPack"), new ItemJetpack());
        shape("DED","BIB","C C");
        setIngredient('D', Material.DIAMOND_BLOCK);
        setIngredient('B', Material.BLAZE_ROD);
        setIngredient('I', Material.IRON_BLOCK);
        setIngredient('E', Material.ELYTRA);
        setIngredient('C', Material.COAL);
    }
}
