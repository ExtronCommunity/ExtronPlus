package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemEnchantEvent implements Listener {

    @EventHandler
    public void enchant(EnchantItemEvent e) {
        Random r = new Random();
        List<Enchantment> validEnchs = new ArrayList<>();
        for (Enchantment en : ExtronPlus.INSTANCE.customEnchants) {
            if (en.getItemTarget().includes(e.getItem())) {
                validEnchs.add(en);
            }
        }
        if (r.nextInt(e.getEnchantsToAdd().size() * 2) == 0) {
            Enchantment ench = null;
            if (validEnchs.size() > 1) {
                ench = validEnchs.get(r.nextInt(validEnchs.size()));

            } else if (validEnchs.size() == 1) {
                if (r.nextInt(5) == 0) {
                    ench = validEnchs.get(0);
                }
            }
            if (ench != null) {
                int level = this.getPreferredLevel(ench, e.getEnchantsToAdd().size(), e.getExpLevelCost(), r);
                e.getEnchantsToAdd().put(ench, level);
                ItemMeta m = e.getItem().getItemMeta();
                List<String> lore = m.getLore();
                if (lore == null) lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + ench.getName() + " " + getLevelSign(level,ench.getMaxLevel()));
                m.setLore(lore);
                e.getItem().setItemMeta(m);
            }
        }
    }

    public static String getLevelSign(int level, int maxLevel) {
        if (maxLevel == 1) return "";
        if (level == 1) return "I";
        if (level == 2) return "II";
        if (level == 3) return "III";
        if (level == 4) return "IV";
        if (level == 5) return "V";
        return String.valueOf(level);
    }

    private int getPreferredLevel(Enchantment ench, int size, int cost, Random r) {
        int max = ench.getMaxLevel();
        if (max == 1) return 1;
        if (cost == 3) {
            if (max > 4) return max - 1;
            else return max;
        }
        if (cost == 2) {
            if (max > 2 && size > 1 && r.nextInt(size) == 0) return max - 2;
            else return max - 1;
        }
        if (cost == 1) {
            return r.nextInt(2) + 1;
        }
        return 1;
    }
}
