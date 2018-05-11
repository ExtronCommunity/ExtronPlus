package com.supercity.main.crafting;

import com.supercity.main.SuperCity;
import com.supercity.main.jetpack.ItemJetpack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeJetpack extends ShapedRecipe {

    public RecipeJetpack() {
        super(new NamespacedKey(SuperCity.INSTANCE, "recipeJetPack"), new ItemJetpack());
        shape("DED","BIB","CNC");
        setIngredient('D', Material.DIAMOND_BLOCK);
        setIngredient('B', Material.BLAZE_ROD);
        setIngredient('I', Material.IRON_BLOCK);
        setIngredient('E', Material.ELYTRA);
        setIngredient('C', Material.COAL);
        setIngredient('N', Material.NETHER_STAR);
    }
}
