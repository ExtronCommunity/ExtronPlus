package com.supercity.main.creepers;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExplosionInfo {

    public static int nextID;

    private int id;
    private final Date date;
    private final UUID causePlayer;
    private List<Location> blocks;
    private World world;

    public ExplosionInfo(int id, Date date, UUID cause, World world) {
        this.id = id;
        this.date = date;
        this.causePlayer = cause;
        this.world = world;
        this.blocks = new ArrayList<>();
    }

    public void addBlock(Location loc) {
        this.blocks.add(loc);
    }

    public Date getDate() {
        return date;
    }

    public UUID getCause() {
        return causePlayer;
    }

    public List<Location> getBlocks() {
        return blocks;
    }

    public int getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }
}
