package com.supercity.main.nbt;

import com.supercity.main.utils.Reflection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTUtils {

    public static String getStringNBT(ItemStack i) {
        return getStringNBTTagFromItemStack(i);
    }

    public static ItemStack applyStringNBTToItem(ItemStack i, String nbt) {
        Object stack = getNMSItemStack(i);
        try {
            setTag(stack, stringToNBT(nbt));
            return nmsItemToBukkitItem(stack);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    private static ItemStack nmsItemToBukkitItem(Object nmsItem) {
        try {
            Class<?> craftItemStack = Reflection.getCraftBukkitClass("inventory.CraftItemStack");
            Method bukkitCopy = craftItemStack.getDeclaredMethod("asBukkitCopy", Reflection.getNMSClass("ItemStack"));
            return (ItemStack) bukkitCopy.invoke(null, nmsItem);
            //return (ItemStack) Reflection.getCraftBukkitClass("CraftItemStack").getDeclaredMethod("asBukkitCopy", Reflection.getNMSClass("ItemStack")).invoke(null, nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setTag(Object item, Object tag) {
        try {
            item.getClass().getDeclaredMethod("setTag", Reflection.getNMSClass("NBTTagCompound")).invoke(item, tag);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Object stringToNBT(String string) {
        try {
            return Reflection.getNMSClass("MojangsonParser").getDeclaredMethod("parse", String.class).invoke(null, string);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static Object getNMSItemStack(ItemStack i) {
        try {
            return Reflection.getCraftBukkitClass("inventory.CraftItemStack").getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, i);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getStringNBTTagFromItemStack(Object nmsItemStack) {
        try {
            Object tag = nmsItemStack.getClass().getDeclaredMethod("getTag").invoke(nmsItemStack);
            if(tag == null) {
                return "{}";
            }
            return (String) tag.getClass().getDeclaredMethod("toString").invoke(tag);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getStringNBTTagFromItemStack(ItemStack i) {
        return getStringNBTTagFromItemStack(getNMSItemStack(i));
    }

}
