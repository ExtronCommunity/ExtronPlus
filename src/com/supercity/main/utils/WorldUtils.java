package com.supercity.main.utils;

import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldUtils {

    public static World getOverworld() {
        return Bukkit.getWorlds().get(0);
    }

    public static BlockPosition toBlockPos(Location loc) {
        return new BlockPosition(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
    }
}
