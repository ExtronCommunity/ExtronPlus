package com.supercity.main.creepers;

import com.supercity.main.config.ConfigFile;
import com.supercity.main.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.*;

public class CreeperChecker {

    private static Map<Creeper, Player> targets = new HashMap<>();
    private static List<ExplosionInfo> infos = new ArrayList<>();
    private static ConfigFile config;

    public static void explosion(EntityExplodeEvent e) {
        if (targets.containsKey(e.getEntity())) {
            Player p = targets.get(e.getEntity());
            ExplosionInfo info = new ExplosionInfo(ExplosionInfo.nextID,new Date(),p.getUniqueId(),e.getLocation().getWorld());
            ExplosionInfo.nextID++;
            infos.add(info);
            for (Block b : e.blockList()) {
                info.addBlock(b.getLocation());
            }
            System.out.println("A Creeper was exploded in the server at " + e.getLocation().getBlockX() + ", " + e.getLocation().getBlockY() + ", " + e.getLocation().getBlockZ() + " potentially by " + p.getName());
        }
    }

    public static void target(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
            targets.put((Creeper)e.getEntity(),(Player)e.getTarget());
        } else {
            targets.remove(e.getEntity());
        }
    }

    public static ExplosionInfo getExplosionAt(Location loc) {
        for (ExplosionInfo ei : infos) {
            if (ei.getBlocks().contains(loc)) {
                return ei;
            }
        }
        return null;
    }

    public static void save() {
        FileConfiguration fc = config.get();
        for (ExplosionInfo info : infos) {
            fc.set(info.getId() + ".date",info.getDate().getTime());
            fc.set(info.getId() + ".cause",info.getCause().toString());
            fc.set(info.getId() + ".world",info.getWorld().getName());
            List<String> locs = new ArrayList<>();
            for (Location loc : info.getBlocks()) {
                locs.add(WorldUtils.encodeLocation(loc,false));
            }
            fc.set(info.getId() + ".blocks",locs);
            System.out.println("saving explosion id=" + info.getId());
        }
        config.save();
    }

    public static void load() {
        config = new ConfigFile("creepers.yml");
        FileConfiguration fc = config.get();
        for (String s : fc.getKeys(false)) {
            ConfigurationSection section = fc.getConfigurationSection(s);
            int id = Integer.parseInt(s);
            Date d = new Date(section.getLong("date"));
            UUID cause = UUID.fromString(section.getString("cause"));
            World w = Bukkit.getWorld(section.getString("world"));
            ExplosionInfo info = new ExplosionInfo(id,d,cause,w);
            if (section.getStringList("blocks") != null) {
                for (String l : section.getStringList("blocks")) {
                    Location loc = WorldUtils.parseLocation(l,w);
                    if (loc != null) {
                        info.addBlock(loc);
                    }
                }
            }
            infos.add(info);
            System.out.println("loaded explosion id=" + id);
            ExplosionInfo.nextID = id + 1;
        }
    }

    public static void death(EntityDeathEvent e) {
        targets.remove(e.getEntity());
    }
}
