package com.supercity.main.jetpack;

import com.supercity.main.SuperCity;
import com.supercity.main.inventory.InventoryUtils;
import com.supercity.main.utils.ParticleEffect;
import com.supercity.main.utils.Reference;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JetpackHandler {

    private final List<Player> flying;
    private final Map<Player, Integer> fuelConsumption;
    private final HashMap<Player, FuelType> lastMaterialConsumed;
    private final Map<Player, BossBar> fuelBars;
    private final List<Player> initiated;
    private List<Player> toRemove;

    public JetpackHandler() {
        flying = new ArrayList<Player>();
        toRemove = new ArrayList<Player>();
        fuelConsumption = new HashMap<Player, Integer>();
        fuelBars = new HashMap<Player, BossBar>();
        initiated = new ArrayList<Player>();
        lastMaterialConsumed = new HashMap<Player, FuelType>();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperCity.INSTANCE, this::tick, 0, 1);
        SuperCity.INSTANCE.getLogger().info("Started jetpack task, it will now repeat every tick!");
    }


    public boolean wearingJetpack(Player p) {
        if(p.getInventory().getChestplate() != null) {
            if(p.getInventory().getChestplate().getItemMeta().getLocalizedName() != null) {
                return p.getInventory().getChestplate().getItemMeta().getLocalizedName().equalsIgnoreCase(Reference.ItemData.JETPACK.getLocName());
            }
        }
        return false;
    }

    public void initiatePlayer(Player p) {
        if(initiated.contains(p)) {
            return;
        }
        initiated.add(p);
        fuelConsumption.put(p, 0);
        BossBar b = Bukkit.createBossBar(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Fuel Bar", BarColor.YELLOW, BarStyle.SOLID);
        fuelBars.put(p, b);
    }

    public void playerDeath(Player p) {
        initiated.remove(p);
        initiatePlayer(p);
    }

    public void tick() {
        tickFindFliers();
        tickFuel();
        tickFuelBar();
        tickFlying();
        showParticles();
    }

    private void tickFindFliers() {
        for(Player p : SuperCity.INSTANCE.getServer().getOnlinePlayers()) {
            boolean added = false;
            if(p.getInventory().getChestplate() != null) {
                if(p.getInventory().getChestplate().getItemMeta().getLocalizedName() != null) {
                    if (p.getInventory().getChestplate().getItemMeta().getLocalizedName().equalsIgnoreCase(Reference.ItemData.JETPACK.getLocName())) {
                        if (p.getLocation().getPitch() >= 85) {
                            if (p.isSneaking()) {
                                if (!flying.contains(p)) {
                                    addPlayerToFlight(p);
                                }
                                added = true;
                            }
                        }
                    }
                }
            }
            if(!added && flying.contains(p)) {
                removePlayerFromFlight(p);
            }
        }
    }

    public boolean hasFuelInInventory(Player p) {
        for(FuelType t : FuelType.values()) {
            if(p.getInventory().contains(t.getMaterial())) {
                return true;
            }
        }
        return false;
    }

    private void tickFuel() {
        toRemove.clear();
        for(Player p : flying) {
            if(hasFuel(p)) {
                fuelConsumption.put(p, fuelConsumption.get(p) - 1);
            } else {
                boolean usedFuel = false;
                for(FuelType t : FuelType.values()) {
                    Material m = t.getMaterial();
                    if(p.getInventory().contains(m)) {
                        InventoryUtils.removeOneItemFromInventory(p, m);
                        if(t.hasReturnType()) {
                            p.getInventory().addItem(new ItemStack(t.getReturnType(), 1));
                        }
                        fuelConsumption.put(p, t.getFuel());
                        lastMaterialConsumed.put(p, t);
                        usedFuel = true;
                        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_CHORUS_FLOWER_GROW, 100, 1);
                        break;
                    }
                }
                if(!usedFuel) {
                    toRemove.add(p);
                }
            }
        }
        for(Player p : toRemove) {
            removePlayerFromFlight(p);
        }
    }

    private void tickFlying() {
        for(Player p : flying) {
            p.setAllowFlight(true);
            p.setVelocity(p.getVelocity().add(new Vector(0, 0.3, 0)));
            if(p.getVelocity().getY() > 2) {
                p.setVelocity(p.getVelocity().setY(2));
            }
            p.setFallDistance(0);
        }
    }

    private void tickFuelBar() {
        for(Player p : SuperCity.INSTANCE.getServer().getOnlinePlayers()) {
            BossBar b = fuelBars.get(p);
            if(isFlying(p)) {
                b.setProgress((double) fuelConsumption.get(p) / lastMaterialConsumed.get(p).getFuel());
                b.setTitle(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Fuel Left: " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + fuelConsumption.get(p) + ChatColor.WHITE.toString() + "||"
                + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Fuel Type Used: " + lastMaterialConsumed.get(p).getDisplayName());
                b.addPlayer(p);
            } else {
                b.removePlayer(p);
            }
        }
    }

    private void showParticles() {
        for(Player p : flying) {
            ParticleEffect.FLAME.display(p.getLocation(), 0.2f, 0.2f, 0.2f, 0, 10);
        }
    }

    private void addPlayerToFlight(Player p) {
        flying.add(p);
        p.setAllowFlight(true);
    }

    private void removePlayerFromFlight(Player p) {
        flying.remove(p);
        if(p.getGameMode() != GameMode.SPECTATOR && p.getGameMode() != GameMode.CREATIVE) {
            p.setAllowFlight(false);
        }
    }

    public boolean hasFuel(Player player) {
        if(fuelConsumption.get(player) == null) {
            fuelConsumption.put(player, 0);
            return false;
        }
        return fuelConsumption.get(player) > 0;
    }

    private boolean isFlying(Player player) {
        return flying.contains(player);
    }

    public enum FuelType {
        COAL(Material.COAL, 100, ChatColor.BLACK + "Coal"),
        COAL_BLOCK(Material.COAL_BLOCK, 1000, ChatColor.BLACK + "Block of Coal"),
        BLAZE_ROD(Material.BLAZE_ROD, 150, ChatColor.GOLD + "Blaze rod"),
        LAVA_BUCKET(Material.LAVA_BUCKET, 1250, ChatColor.GOLD + "Lava " + ChatColor.GRAY + "Bucket", Material.BUCKET);


        private Material m;
        private int fuel;
        private String displayName;
        private Material returnType;
        FuelType(Material m, int fuel, String displayName) {
            this.m = m;
            this.fuel = fuel;
            this.displayName = displayName;
        }
        FuelType(Material m, int fuel, String displayName, Material returnType) {
            this(m, fuel, displayName);
            this.returnType = returnType;
        }

        public Material getMaterial() {
            return m;
        }

        public int getFuel() {
            return fuel;
        }

        public String getDisplayName() {
            return displayName;
        }

        public boolean hasReturnType() {
            return returnType != null;
        }

        public Material getReturnType() {
            return returnType;
        }
    }
}
