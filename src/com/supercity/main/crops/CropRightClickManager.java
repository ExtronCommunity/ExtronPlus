package com.supercity.main.crops;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Crops;

public class CropRightClickManager {

    public static void handleCropClick(Block block) {
        Material old = block.getType();
        block.breakNaturally();
        block.setType(old);
        //block.getState().
    }

    public static boolean isMature(Block block) {
//        Crops c = (Crops) block;
//        return c.getState() == CropState.RIPE;
        if(block.getState().getData() instanceof Crops) {
            System.out.println(((Crops)block.getState().getData()).getState());
            ((Crops)block.getState().getData()).setState(CropState.RIPE);
            return ((Crops)block.getState().getData()).getState() == CropState.RIPE;
        }
        return false;
    }
}
