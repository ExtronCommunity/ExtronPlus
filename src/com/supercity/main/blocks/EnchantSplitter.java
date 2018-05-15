package com.supercity.main.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class EnchantSplitter extends CustomBlockContainer {
    @Override
    public String getId() {
        return "enchant_splitter";
    }

    @Override
    public String getDisplayName() {
        return "Enchantment Splitter";
    }

    @Override
    public Material getRealBlockType() {
        return Material.DROPPER;
    }

    @Override
    public byte getRealBlockData() {
        return 0;
    }

    @Override
    public boolean isTickable() {
        return true;
    }

    @Override
    public void onTick(Block b) {

    }
}
