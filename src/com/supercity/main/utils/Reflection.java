package com.supercity.main.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

    private static String NMS;

    static {
        NMS = Bukkit.getServer().getClass().getPackage().getName();
        NMS = NMS.substring(NMS.lastIndexOf(".") + 1);
    }

    public static Object getField(Object obj, Class<?> clazz, String name) {
        Object value = null;
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            value = f.get(obj);
        } catch (NoSuchFieldException e) {
            System.out.println("Can't reflect field " + name + " on class " + clazz.getSimpleName());
        } catch (IllegalAccessException e) {
            System.out.println("Can't get field value of " + name + " on class " + clazz.getSimpleName());
        }
        return value;
    }

    public static void setField(Object obj, Class<?> clazz, String name, Object value) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            f.set(obj,value);
        } catch (NoSuchFieldException e) {
            System.out.println("Can't reflect field " + name + " on class " + clazz.getSimpleName());
        } catch (IllegalAccessException e) {
            System.out.println("Can't set field value of " + name + " on class " + clazz.getSimpleName());
        }
    }

    public static void sendActionBar(Player p, String message) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + NMS + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(p);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + NMS + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + NMS + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + NMS + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + NMS + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + NMS + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            for (Object obj : chatMessageTypes) {
                if (obj.toString().equals("GAME_INFO")) {
                    chatMessageType = obj;
                }
            }
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, chatMessageTypeClass}).newInstance(o, chatMessageType);
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public static Class<?> getNMSClass(String clazz) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getCraftBukkitClass(String clazz) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
