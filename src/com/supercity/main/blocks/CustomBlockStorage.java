package com.supercity.main.blocks;

import com.supercity.main.utils.WorldUtils;
import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBlockStorage {

    private static Map<BlockPosition,CustomBlock> blockMap = new HashMap<>();
    public List<CustomBlock> blocks = new ArrayList<>();

    public static boolean onRightClickBlock(Player player, Block block) {
        BlockPosition pos = WorldUtils.toBlockPos(block.getLocation());
        if (blockMap.get(pos) != null) {
            blockMap.get(pos).onRightClick(player,block);
            return true;
        }
        return false;
    }

    public static void onPlaceBlock(Player p, Block b, ItemStack skull) {
        CustomBlock block = getBlockOfSkull(skull);
        onPlaceBlock(p,b.getLocation(),block);
    }

    public static void onPlaceBlock(Player p, Location loc, CustomBlock block) {
        BlockPosition pos = WorldUtils.toBlockPos(loc);
        blockMap.put(pos,block);
        block.onPlacedBy(p,loc);
    }

    private static CustomBlock getBlockOfSkull(ItemStack skull) {
        return null;
    }


}
