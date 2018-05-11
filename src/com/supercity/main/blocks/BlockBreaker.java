package com.supercity.main.blocks;

import org.bukkit.Material;

public class BlockBreaker extends CustomBlockContainer {

    @Override
    public String getId() {
        return "block_breaker";
    }

    @Override
    public String getDisplayName() {
        return "Block Breaker";
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
    public void onRedstoneActivated() {
        this.activate();
    }

    private void activate() {

    }
}
