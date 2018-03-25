package com.redsponge.extron.plus.crafting;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.jetpack.ItemJetpack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeJetpack extends ShapedRecipe {

    public RecipeJetpack() {
        super(new NamespacedKey(ExtronPlus.INSTANCE, "recipeJetPack"), new ItemJetpack());
        shape("FNF","CEC","BSB");
        setIngredient('F', Material.FIREWORK);
        setIngredient('N', Material.NETHER_STAR);
        setIngredient('C', Material.COAL);
        setIngredient('E', Material.ELYTRA);
        setIngredient('B', Material.BLAZE_POWDER);
        setIngredient('S', Material.FLINT_AND_STEEL);
    }
}
