package com.supercity.main.item;

import com.supercity.main.utils.Reference.ItemData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class CustomItem extends ItemStack {

    private ItemData data;

    public CustomItem(ItemData data) {
        super(data.getMaterial());
        this.data = data;
        ItemMeta meta = getItemMeta();
        meta.setLocalizedName(data.getLocName());
        meta.setDisplayName(data.getDisplayName());
        if(data.hasEnchantedGlint()) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if(data.hasLore()) {
            meta.setLore(Arrays.asList(data.getLore()));
        }
        setItemMeta(meta);
    }

    public ItemData getItemData() {
        return data;
    }
}
