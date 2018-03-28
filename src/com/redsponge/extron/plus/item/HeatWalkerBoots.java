package com.redsponge.extron.plus.item;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.utils.Reference.ItemData;

public class HeatWalkerBoots extends CustomItem {
    public HeatWalkerBoots() {
        super(ItemData.HEATWALK_BOOTS);
        this.addUnsafeEnchantment(ExtronPlus.INSTANCE.getCustomEnchant(101), 2);
    }
}
