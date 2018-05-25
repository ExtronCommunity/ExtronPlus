package com.supercity.main.enchants;

import com.supercity.main.event.result.EntityResult;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sniper extends CustomEnchant {
    @Override
    public String getId() {
        return "sniper";
    }

    @Override
    public String getName() {
        return "Sniper";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getTarget() {
        return EnchantmentTarget.BOW;
    }

    @Override
    public boolean hasConflict(Enchantment e) {
        return false;
    }

    @Override
    public boolean hasConflict(CustomEnchant e) {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public EnchRarity getRarity() {
        return EnchRarity.LEGENDARY;
    }

    @Override
    protected EntityResult onShootArrow(Player shooter, ItemStack bow, float force, Entity projectile) {
        projectile.setGravity(false);
        return super.onShootArrow(shooter, bow, force, projectile);
    }
}
