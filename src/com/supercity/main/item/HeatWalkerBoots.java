package com.supercity.main.item;

import com.supercity.main.SuperCity;
import com.supercity.main.utils.Reference.ItemData;

public class HeatWalkerBoots extends CustomItem {
    public HeatWalkerBoots() {
        super(ItemData.HEATWALK_BOOTS);
        this.addUnsafeEnchantment(SuperCity.INSTANCE.getCustomEnchant(101), 2);
    }
}
