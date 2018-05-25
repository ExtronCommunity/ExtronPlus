package com.supercity.main.blocks;

import com.supercity.main.utils.WorldUtils;
import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBlockStorage {

    private static Map<BlockPosition,CustomBlock> blockMap = new HashMap<>();
    public List<CustomBlock> blocks = new ArrayList<>();

    public static boolean onRightClickBlock(Player player, Block block, BlockFace clickedFace) {
        BlockPosition pos = WorldUtils.toBlockPos(block.getLocation());
        if (blockMap.get(pos) != null) {
            blockMap.get(pos).onRightClick(player,block,clickedFace);
            return true;
        }
        return false;
    }

    public static boolean onPlaceBlock(Player p, Block b, ItemStack skull) {
        CustomBlock block = getBlockOfSkull(skull);
        if (block == null) return false;
        return onPlaceBlock(p,b.getLocation(),block);
    }

    public static boolean onPlaceBlock(Player p, Location loc, CustomBlock block) {
        if (block.onPlacedBy(p,loc)) return false;
        BlockPosition pos = WorldUtils.toBlockPos(loc);
        blockMap.put(pos,block);
        loc.getBlock().setType(block.getRealBlockType());
        loc.getBlock().setData(block.getRealBlockData());
        return true;
    }

    public static CustomBlock getBlockOfSkull(ItemStack skull) {
        return null;
    }

    public static boolean onBreakBlock(Player p, Block b, ItemStack item) {
        BlockPosition pos = WorldUtils.toBlockPos(b.getLocation());
        if (blockMap.get(pos) == null) {
            return false;
        }
        CustomBlock cb = blockMap.get(pos);
        if (cb.onBrokenBy(p,item)) return false;
        for (ItemStack s : cb.getDrops()) {
            b.getWorld().dropItemNaturally(b.getLocation(),s);
        }
        return true;
    }


}
