package com.supercity.main.enchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Boing extends Enchantment implements Listener {

    private static final Map<UUID,Long> delay = new HashMap<>();
    private static final Map<UUID,Integer> count = new HashMap<>();

    public Boing() {
        super(104);
    }

    @Override
    public String getName() {
        return "Boing";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
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
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getInventory().getItemInMainHand().containsEnchantment(this)) {
                int level = p.getInventory().getItemInMainHand().getEnchantmentLevel(this);
                if (level > 0) {
                    long time = System.currentTimeMillis();
                    long time2 = delay.get(p.getUniqueId());
                    if (time - time2 < 700) {
                        count.put(p.getUniqueId(),count.get(p.getUniqueId()) + 1);
                        if (count.get(p.getUniqueId()) > new Random().nextInt(5)+3) {
                            e.getEntity().setVelocity(new Vector(0,0.3*level,0));
                            count.put(p.getUniqueId(),0);
                        }
                    } else {
                        count.put(p.getUniqueId(),0);
                    }
                    delay.put(p.getUniqueId(),time);
                }
            }
        }
    }
}
