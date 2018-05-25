package com.supercity.main.item;

import com.supercity.main.utils.Reference.ItemData;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class CustomItem extends ItemStack {

    private ItemData data;

    public CustomItem(ItemData data) {
        super(data.getMaterial());
        this.data = data;
        ItemMeta meta = getItemMeta();
        meta.setLocalizedName(data.getLocName());
        meta.setDisplayName(ChatColor.WHITE + data.getDisplayName());
        if(data.hasEnchantedGlint()) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if(data.hasLore()) {
            meta.setLore(Arrays.asList(data.getLore()));
        }
        setItemMeta(meta);
    }

    public ItemData getItemData() {
        return data;
    }

    protected void setNBT(NBTTagCompound tag) {
        net.minecraft.server.v1_12_R1.ItemStack nms = this.getNMS();
        if (nms.getTag() == null) {
            nms.setTag(tag);
        } else {
            nms.getTag().a(tag);
        }
    }

    public CraftItemStack getCraftItem() {
        return CraftItemStack.asCraftCopy(this);
    }

    public net.minecraft.server.v1_12_R1.ItemStack getNMS() {
        return CraftItemStack.asNMSCopy(this);
    }
}
