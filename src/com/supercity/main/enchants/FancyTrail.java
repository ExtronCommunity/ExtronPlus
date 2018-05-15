package com.supercity.main.enchants;

import com.supercity.main.SuperCity;
import com.supercity.main.event.result.EntityResult;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class FancyTrail extends CustomEnchant {
    @Override
    public String getId() {
        return "fancy_trail";
    }

    @Override
    public String getName() {
        return "Fancy Trail";
    }

    @Override
    public int getMaxLevel() {
        return 5;
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
        new BukkitRunnable() {
            @Override
            public void run() {
                if (projectile.isDead() || projectile.isOnGround()) {
                    this.cancel();
                    return;
                }
                Particle p = null;
                switch (getEnchantLevel(shooter)) {
                    case 1:
                        p = Particle.VILLAGER_HAPPY;
                        break;
                    case 2:
                        p = Particle.HEART;
                        break;
                    case 3:
                        p = Particle.DRAGON_BREATH;
                        break;
                    case 4:
                        p = Particle.SPELL_MOB;
                        break;
                    case 5:
                        p = Particle.END_ROD;
                        projectile.getWorld().spawnParticle(Particle.PORTAL,projectile.getLocation().subtract(0,0.2,0),8,0.2,0.1,0.1,0.1);
                        break;
                }
                if (p != null) {
                    projectile.getWorld().spawnParticle(p,projectile.getLocation(),3,0.1,0.1,0.1,0.01);
                }
            }
        }.runTaskTimer(SuperCity.INSTANCE,1,1);
        return super.onShootArrow(shooter, bow, force, projectile);
    }
}
