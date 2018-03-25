package com.redsponge.extron.plus.jetpack;

import com.redsponge.extron.plus.item.CustomItem;
import com.redsponge.extron.plus.utils.Reference;
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
