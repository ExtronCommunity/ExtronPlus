package com.redsponge.extron.plus.item;

import com.redsponge.extron.plus.enchants.CurseOfBreaking;
import com.redsponge.extron.plus.utils.Reference;

public class PickaxeCurseOfBreaking extends CustomItem {

    public PickaxeCurseOfBreaking() {
        super(Reference.ItemData.PICK);
        addUnsafeEnchantment(new CurseOfBreaking(), 1);
    }
}
