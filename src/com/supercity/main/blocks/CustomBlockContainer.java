package com.supercity.main.blocks;

import com.supercity.main.utils.WorldUtils;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.TileEntity;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomBlockContainer extends CustomBlock {

    public boolean onInsertItem(Player p, ItemStack inserted) {
        return false;
    }

    public TileEntity getTileEntity(Block b) {
        WorldServer ws = getWorld(b);
        return ws.getTileEntity(WorldUtils.toBlockPos(b.getLocation()));
    }

    public WorldServer getWorld(Block b) {
        return ((CraftWorld)b.getWorld()).getHandle();
    }

}
