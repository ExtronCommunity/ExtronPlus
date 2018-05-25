//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supercity.main.enchants;

import com.supercity.main.event.result.MoveResult;
import com.supercity.main.item.HeatWalkerBoots;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class HeatWalker extends CustomEnchant {

    @Override
    public String getId() {
        return "heat_walker";
    }

    public String getName() {
        return "Heat Walker";
    }

    public int getMaxLevel() {
        return 2;
    }

    @Override
    public EnchantmentTarget getTarget() {
        return EnchantmentTarget.ARMOR_FEET;
    }

    @Override
    public boolean hasConflict(Enchantment e) {
        return e == Enchantment.FROST_WALKER;
    }

    @Override
    public boolean hasConflict(CustomEnchant e) {
        return false;
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean isCursed() {
        return false;
    }

    @Override
    public EnchRarity getRarity() {
        return EnchRarity.EPIC;
    }

    @Override
    protected MoveResult onMove(Player p, Location from, Location to) {
        if (p.isOnGround()) {
            int level = this.getEnchantLevel(p);
            for(int i = -(level + 3); i < level + 3; ++i) {
                for(int j = -(level + 3); j < level + 3; ++j) {
                    Location loc = p.getLocation().add((double)i, -1.0D, (double)j);
                    if (loc.getBlock() != null && p.getLocation().distance(loc) <= (double)(level + 2) && (loc.getBlock().getType() == Material.LAVA || loc.getBlock().getType() == Material.STATIONARY_LAVA)) {
                        loc.getBlock().setType(Material.OBSIDIAN);
                    }
                }
            }

        }
        return super.onMove(p, from, to);
    }
}
