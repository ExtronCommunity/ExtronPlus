//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.redsponge.extron.plus.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class HeatWalker extends Enchantment implements Listener {
    public HeatWalker() {
        super(101);
    }

    public String getName() {
        return "Heat Walker";
    }

    public int getMaxLevel() {
        return 2;
    }

    public int getStartLevel() {
        return 1;
    }

    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_FEET;
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean isCursed() {
        return false;
    }

    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getBoots() != null) {
            ItemStack boots = p.getInventory().getBoots();
            if (boots.containsEnchantment(this)) {
                if (p.isOnGround()) {
                    for(int i = -(boots.getEnchantmentLevel(this) + 3); i < boots.getEnchantmentLevel(this) + 3; ++i) {
                        for(int j = -(boots.getEnchantmentLevel(this) + 3); j < boots.getEnchantmentLevel(this) + 3; ++j) {
                            Location loc = p.getLocation().add((double)i, -1.0D, (double)j);
                            if (loc.getBlock() != null && p.getLocation().distance(loc) <= (double)(boots.getEnchantmentLevel(this) + 2) && (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == Material.STATIONARY_LAVA)) {
                                loc.getBlock().setType(Material.OBSIDIAN);
                            }
                        }
                    }

                }
            }
        }
    }
}
