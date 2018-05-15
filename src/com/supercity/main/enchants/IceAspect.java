package com.supercity.main.enchants;

import com.supercity.main.SuperCity;
import com.supercity.main.event.result.DamageResult;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class IceAspect extends CustomEnchant {
    @Override
    public String getId() {
        return "ice_aspect";
    }

    @Override
    public String getName() {
        return "Ice Aspect";
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public EnchantmentTarget getTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean hasConflict(Enchantment e) {
        return e == Enchantment.FIRE_ASPECT;
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
        return EnchRarity.EPIC;
    }

    @Override
    protected DamageResult onUseAtEntity(Player damager, Entity target, double damage) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,50 * getEnchantLevel(damager),2));
        }
        return super.onUseAtEntity(damager, target, damage);
    }
}
