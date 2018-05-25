package com.supercity.main.enchants;

import com.supercity.main.event.result.DamageResult;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Lifesteal extends CustomEnchant {
    @Override
    public String getId() {
        return "lifesteal";
    }

    @Override
    public String getName() {
        return "Lifesteal";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getTarget() {
        return EnchantmentTarget.WEAPON;
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
    protected DamageResult onUseAtEntity(Player damager, Entity target, double damage) {
        if (damager.getHealth() <= 19.0) {
            damager.setHealth(damager.getHealth() + 1);
        }
        return super.onUseAtEntity(damager, target, damage);
    }
}
