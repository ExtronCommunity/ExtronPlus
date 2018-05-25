package com.supercity.main.enchants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum EnchRarity {

    COMMON(80), RARE(40), EPIC(20), LEGENDARY(10);

    private final int chance;

    EnchRarity(int chance) {
        this.chance = chance;
    }

    public static EnchRarity get(int n) {
        List<EnchRarity> list = new ArrayList<>(Arrays.asList(values()));
        Collections.shuffle(list);
        for (EnchRarity r : list) {
            if (r.getChance() > n) {
                return r;
            }
        }
        return null;
    }

    public int getChance() {
        return chance;
    }
}
