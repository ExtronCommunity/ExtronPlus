package com.redsponge.extron.plus.enchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CurseOfBreaking extends Enchantment implements Listener {

    @EventHandler
    public void onPlayerLoseDurability(PlayerItemDamageEvent e) {
        if(e.getItem().getItemMeta().hasEnchant(this)) {
            e.setDamage(e.getDamage() * 2);
        }
    }

    public CurseOfBreaking() {
        super(102);
    }

    @Override
    public String getName() {
        return "§cCurse of Breaking";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }
}
