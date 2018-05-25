package com.supercity.main.item;

import com.supercity.main.SuperCity;
import com.supercity.main.enchants.CustomEnchant;
import com.supercity.main.enchants.HeatWalker;
import com.supercity.main.utils.Reference.ItemData;

public class HeatWalkerBoots extends CustomItem {
    public HeatWalkerBoots() {
        super(ItemData.HEATWALK_BOOTS);
        HeatWalker.addTo(this,2);
    }
}
