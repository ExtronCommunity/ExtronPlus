package com.supercity.main.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {

    public static World getOverworld() {
        return Bukkit.getWorlds().get(0);
    }

}
