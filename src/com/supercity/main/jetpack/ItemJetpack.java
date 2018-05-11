package com.supercity.main.jetpack;

import com.supercity.main.item.CustomItem;
import com.supercity.main.utils.Reference;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemJetpack extends CustomItem {

    public ItemJetpack() {
        super(Reference.ItemData.JETPACK);
        ItemMeta m = getItemMeta();
        m.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
        setItemMeta(m);
    }
}
