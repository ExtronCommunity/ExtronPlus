package com.supercity.main.blocks;

import net.minecraft.server.v1_12_R1.TileEntity;
import org.bukkit.block.Block;

public class CustomTileEntity<TE extends TileEntity> {


    private final TE tileEntity;
    private final Block block;

    public CustomTileEntity(TE realTileEntity, Block b) {
        this.tileEntity = realTileEntity;
        this.block = b;
    }
}
