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

import com.supercity.main.enchants.CustomEnchant;
import com.supercity.main.enchants.EnchRarity;
import net.minecraft.server.v1_12_R1.ContainerEnchantTable;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEnchantEvent implements Listener {
    public ItemEnchantEvent() {
    }

    @EventHandler
    public void enchant(EnchantItemEvent e) {
        Random r = new Random();
        List<CustomEnchant> validEnchs = new ArrayList<>();

        for (CustomEnchant ce : SuperCity.INSTANCE.customEnchants) {
            if (ce.isValid(e) && ce.isCustomValid(validEnchs)) {
                validEnchs.add(ce);
            }
        }
        if (validEnchs.isEmpty()) return;
        if (r.nextInt(3) == 0) {
            int i = r.nextInt(100);
            EnchRarity rarity = EnchRarity.get(i);
            if (rarity != null) {
                validEnchs.removeIf((n)->n.getRarity() != rarity);
                CustomEnchant ench;
                if (validEnchs.isEmpty()) {
                    return;
                }
                if (validEnchs.size() == 1) {
                    ench = validEnchs.get(0);
                } else {
                    ench = validEnchs.get(r.nextInt(validEnchs.size()));
                }
                if (ench != null) {
                    int level = getPreferredLevel(e.getExpLevelCost(), ench.getMaxLevel(), rarity, r);
                    if (level <= 0) {
                        return;
                    }
                    EntityPlayer p = ((CraftPlayer)e.getEnchanter()).getHandle();
                    if (p.activeContainer instanceof ContainerEnchantTable) {
                        ContainerEnchantTable table = (ContainerEnchantTable) p.activeContainer;
                        ench.addToItem(e.getItem(),level);
                        table.enchantSlots.update();
                    }
                }
            }
        }
    }

    public int getPreferredLevel(int cost, int max, EnchRarity rarity, Random r) {
        if (cost == 2) {
            if (rarity == EnchRarity.COMMON) {
                return r.nextInt(2) == 0 ? 0 : max;
            }
            if (rarity == EnchRarity.RARE) {
                return max;
            }
            if (max > 3) {
                return max - 1;
            }
            return max;
        }
        if (cost == 1) {
            if (rarity == EnchRarity.LEGENDARY) {
                return 0;
            }
            if (max >= 5) {
                return max - 3;
            }
            if (max > 3) {
                return r.nextInt(max - 2) + 1;
            }
            return max;
        }
        if (cost == 0) {
            if (rarity == EnchRarity.EPIC || rarity == EnchRarity.LEGENDARY) {
                return 0;
            }
            if (max >= 3) {
                return 2;
            }
            return r.nextInt(2) + 1;
        }
        return 1;
    }

}
