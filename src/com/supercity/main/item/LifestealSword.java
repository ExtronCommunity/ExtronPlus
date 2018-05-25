package com.supercity.main.item;

import com.supercity.main.SuperCity;
import com.supercity.main.enchants.Lifesteal;
import com.supercity.main.utils.Reference;

public class LifestealSword extends CustomItem {
    public LifestealSword() {
        super(Reference.ItemData.LIFESTEAL_SWORD);
        Lifesteal.addTo(this,1);
    }
}
