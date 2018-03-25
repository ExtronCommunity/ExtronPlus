package com.redsponge.extron.plus.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    @Override
    public String getName() {
        return "Heat Walker";
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_FEET;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }



    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getBoots() == null) return;
        ItemStack boots = p.getInventory().getBoots();
        if (!boots.containsEnchantment(this)) return;
        if (!p.isOnGround()) return;
        for (int i = -(boots.getEnchantmentLevel(this) + 3); i < boots.getEnchantmentLevel(this) + 3; i++) {
            for (int j = -(boots.getEnchantmentLevel(this) + 3); j < boots.getEnchantmentLevel(this) + 3; j++) {
                Location loc = p.getLocation().add(i,-1,j);
                if (loc.getBlock() == null) continue;
                if (p.getLocation().distance(loc) > boots.getEnchantmentLevel(this) + 2) continue;
                if (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == Material.STATIONARY_LAVA) {
                    loc.getBlock().setType(Material.OBSIDIAN);
                }
            }
        }
    }
}
