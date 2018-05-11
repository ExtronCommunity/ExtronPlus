package com.supercity.main.item;

import com.supercity.main.enchants.CurseOfBreaking;
import com.supercity.main.utils.Reference;

public class PickaxeCurseOfBreaking extends CustomItem {

    public PickaxeCurseOfBreaking() {
        super(Reference.ItemData.PICK);
        addUnsafeEnchantment(new CurseOfBreaking(), 1);
    }
}
