package com.supercity.main.tileentity;

import com.supercity.main.blocks.CustomTileEntity;
import net.minecraft.server.v1_12_R1.TileEntityDropper;
import org.bukkit.block.Block;

public class TileEntityEnchantSplitter extends CustomTileEntity<TileEntityDropper> {
    public TileEntityEnchantSplitter(TileEntityDropper realTileEntity, Block b) {
        super(realTileEntity,b);
    }
}
