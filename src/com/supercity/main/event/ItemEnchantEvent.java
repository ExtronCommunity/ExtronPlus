//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supercity.main.event;

import com.supercity.main.SuperCity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEnchantEvent implements Listener {
    public ItemEnchantEvent() {
    }

    @EventHandler
    public void enchant(EnchantItemEvent e) {
        Random r = new Random();
        List<Enchantment> validEnchs = new ArrayList();
        Iterator var4 = SuperCity.INSTANCE.customEnchants.iterator();

        while(var4.hasNext()) {
            Enchantment en = (Enchantment)var4.next();
            if (en.getItemTarget().includes(e.getItem())) {
                validEnchs.add(en);
            }
        }

        if (r.nextInt(e.getEnchantsToAdd().size() * 2) == 0) {
            Enchantment ench = null;
            if (validEnchs.size() > 1) {
                ench = (Enchantment)validEnchs.get(r.nextInt(validEnchs.size()));
            } else if (validEnchs.size() == 1 && r.nextInt(5) == 0) {
                ench = (Enchantment)validEnchs.get(0);
            }

            if (ench != null) {
                int level = this.getPreferredLevel(ench, e.getEnchantsToAdd().size(), e.getExpLevelCost(), r);
                e.getEnchantsToAdd().put(ench, level);
                ItemMeta m = e.getItem().getItemMeta();
                List<String> lore = m.getLore();
                if (lore == null) {
                    lore = new ArrayList();
                }

                ((List)lore).add(ChatColor.GRAY + ench.getName() + " " + getLevelSign(level, ench.getMaxLevel()));
                m.setLore((List)lore);
                e.getItem().setItemMeta(m);
            }
        }

    }

    public static String getLevelSign(int level, int maxLevel) {
        if (maxLevel == 1) {
            return "";
        } else if (level == 1) {
            return "I";
        } else if (level == 2) {
            return "II";
        } else if (level == 3) {
            return "III";
        } else if (level == 4) {
            return "IV";
        } else {
            return level == 5 ? "V" : String.valueOf(level);
        }
    }

    private int getPreferredLevel(Enchantment ench, int size, int cost, Random r) {
        int max = ench.getMaxLevel();
        if (max == 1) {
            return 1;
        } else if (cost == 3) {
            return max > 4 ? max - 1 : max;
        } else if (cost == 2) {
            return max > 2 && size > 1 && r.nextInt(size) == 0 ? max - 2 : max - 1;
        } else {
            return cost == 1 ? r.nextInt(2) + 1 : 1;
        }
    }
}
