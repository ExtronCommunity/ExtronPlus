package com.redsponge.extron.plus.item;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.utils.Reference;
import org.bukkit.inventory.meta.ItemMeta;

public class HeatWalkerBoots extends CustomItem {
    public HeatWalkerBoots() {
        super(Reference.ItemData.HEATWALK_BOOTS);
        addUnsafeEnchantment(ExtronPlus.INSTANCE.getCustomEnchant(101),2);
    }
}
