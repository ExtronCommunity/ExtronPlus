package com.supercity.main.item;

import com.supercity.main.utils.Reference;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemLirra extends CustomItem {

    public ItemLirra(int value) {
        super(Reference.ItemData.LIRRA);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("Value",value);
        this.setNBT(tag);
    }

    public ItemLirra() {
        this(1);
    }

    public static boolean isLirra(ItemStack stack) {
        try {
            return stack.getItemMeta().getLocalizedName().equalsIgnoreCase(Reference.ItemData.LIRRA.getLocName());
        } catch (Exception e) {
            return false;
        }
    }

    public static int getValue(ItemStack stack) {
        if (isLirra(stack)) {
            net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
            NBTTagCompound c = nms.getTag();
            if (c == null) return 1;
            int value = c.getInt("Value");
            if (value <= 0) value = 1;
            return value;
        }
        return 0;
    }
}
