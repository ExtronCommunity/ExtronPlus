package com.supercity.main.spawner;

import com.supercity.main.SuperCity;
import com.supercity.main.event.custom.BlockPistonPoweredByRedstoneBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnerMovingHandler implements Listener {

    private Map<Entity, String> minecarts;
    private ConsoleCommandSender console;

    public SpawnerMovingHandler() {
        minecarts = new HashMap<>();
        console = Bukkit.getServer().getConsoleSender();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperCity.INSTANCE, () -> tickSpawnerMinecarts(), 0, 1);
    }

    @EventHandler
    public void onPistonActivate(BlockPistonPoweredByRedstoneBlockEvent event) {
        if(event.getDirection() != BlockFace.DOWN) return;
        System.out.println("CALLED EVENT!");
        Block piston = event.getBlock();
        Block below = event.getBlock().getRelative(BlockFace.DOWN);
        if(below.getType() == Material.MOB_SPAWNER) {
            Minecart foundMinecart = null;
            for(Entity e : piston.getWorld().getNearbyEntities(below.getLocation(), 1, 1, 1)) {
                if (e instanceof Minecart) {
                    foundMinecart = (Minecart) e;
                    break;
                }
            }
            if(foundMinecart == null) return;
            foundMinecart.remove();
            CreatureSpawner spanwer = (CreatureSpawner) below.getState();
            below.setType(Material.AIR);
            spawnSpawnerMinecart(below.getLocation(), spanwer.getSpawnedType().getName());
        }
    }

    private void spawnSpawnerMinecart(Location l, String mobId) {
        String customTag = "SpanwerMinecart_" + mobId + "_" + UUID.randomUUID().toString();
        String cmd = "execute %playerName ~ ~ ~ summon minecraft:spawner_minecart %x %y %z {SpawnData:{idCount:\"%mobId\"},CustomName:\"%MobId Spawner\",CustomNameVisible:1,Tags:[\"%tag\"]}".replaceAll("%x", Integer.toString(l.getBlockX()))
                .replaceAll("%y", Integer.toString(l.getBlockY())).replaceAll("%z", Integer.toString(l.getBlockZ())).replaceAll("%mobId", mobId).replaceAll("%MobId", mobId.substring(0, 1).toUpperCase() + mobId.substring(1).toLowerCase())
                .replaceAll("%playerName", l.getWorld().getPlayers().get(0).getName()).replaceAll("%tag", customTag);
        System.out.println(cmd);
        Bukkit.dispatchCommand(console, cmd);

        Entity spawned = null;
        for(Entity e : l.getWorld().getNearbyEntities(l, 1, 1, 1)) {
            if(e.getScoreboardTags().contains(customTag)) {
                spawned = e;
                break;
            }
        }
        minecarts.put(spawned, mobId);
    }

    public void tickSpawnerMinecarts() {
        for(Entity e : minecarts.keySet()) {
            Block in = e.getLocation().getBlock();
            if(in.getType() == Material.ACTIVATOR_RAIL) {
                if(in.getBlockPower() > 0) {
                    String mobId = minecarts.get(e);
                    minecarts.remove(e);
                    e.getWorld().spawn(e.getLocation(), Minecart.class);
                    e.remove();
                    String cmd = "execute %playerName ~ ~ ~ setblock %x %y %z mob_spawner 0 replace {SpawnData:{idCount:\"%mobId\"}}".replaceAll("%mobId", mobId).replaceAll("%playerName", in.getWorld().getPlayers().get(0).getName())
                            .replaceAll("%x", Integer.toString(in.getLocation().getBlockX())).replaceAll("%y", Integer.toString(in.getLocation().getBlockY()+1)).replaceAll("%z", Integer.toString(in.getLocation().getBlockZ()));
                    Bukkit.dispatchCommand(console, cmd);
                }
            }
        }
    }
}
