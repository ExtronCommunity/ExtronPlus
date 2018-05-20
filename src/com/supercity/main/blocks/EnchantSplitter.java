package com.supercity.main.blocks;

import com.supercity.main.tileentity.TileEntityEnchantSplitter;
import net.minecraft.server.v1_12_R1.TileEntityDropper;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class EnchantSplitter extends CustomInventoryBlock<TileEntityDropper,TileEntityEnchantSplitter> {
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
    public TileEntityEnchantSplitter createTileEntity(TileEntityDropper realTileEntity, Block b) {
        return new TileEntityEnchantSplitter(realTileEntity,b);
    }
}
