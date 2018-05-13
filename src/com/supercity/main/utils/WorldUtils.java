package com.supercity.main.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldUtils {

    public static World getOverworld() {
        return Bukkit.getWorlds().get(0);
    }

    public static Location parseLocation(String locString) {
        String[] strings = locString.split(",");
        if (strings.length < 4) {
            return null;
        }
        World w = Bukkit.getWorld(strings[0]);
        if (w == null) {
            return null;
        }
        try {
            int x = Integer.parseInt(strings[1]);
            int y = Integer.parseInt(strings[2]);
            int z = Integer.parseInt(strings[3]);
            return new Location(w,x,y,z);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Location parseLocation(String locString, World w) {
        String[] strings = locString.split(",");
        if (strings.length < 3) {
            return null;
        }
        try {
            int x = Integer.parseInt(strings[0]);
            int y = Integer.parseInt(strings[1]);
            int z = Integer.parseInt(strings[2]);
            return new Location(w,x,y,z);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String encodeLocation(Location loc, boolean world) {
        if (loc == null) {
            return null;
        }
        return (world ? loc.getWorld().getName() + "," : "") + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }
}
