package com.supercity.main.enchants;

import com.supercity.main.SuperCity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LegacyEnchantConvertor implements Listener {

    @EventHandler
    public void heldItem(PlayerItemHeldEvent e) {
        ItemStack stack = e.getPlayer().getInventory().getItem(e.getNewSlot());
        ItemMeta m = stack.getItemMeta();
        List<String> lore = m.getLore();
        CustomEnchant ench = null;
        if (m.hasEnchants() && !CustomEnchant.hasCustomEnchants(stack)) {
            for (int i = 0; i < lore.size(); i++) {
                ench = getEnchantFromLore(lore.get(i));
                if (ench == null) {
                    lore.remove(lore.get(i));
                }
            }
        }
        m.setLore(lore);
        stack.setItemMeta(m);
        if (ench != null) {
            ench.addToItem(stack,ench.getMaxLevel());
        }
    }

    private CustomEnchant getEnchantFromLore(String s) {
        for (CustomEnchant e : SuperCity.INSTANCE.customEnchants) {
            if (s.contains(e.getName())) {
                return e;
            }
        }
        return null;
    }

}
