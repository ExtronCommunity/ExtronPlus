package com.supercity.main.blocks;

import net.minecraft.server.v1_12_R1.IInventory;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.TileEntityDispenser;
import org.bukkit.block.Block;

public abstract class CustomInventoryBlock<RTE extends TileEntity & IInventory, CTE extends CustomTileEntity<?>> extends CustomBlock {

    public abstract CTE createTileEntity(RTE realTileEntity, Block b);

}
