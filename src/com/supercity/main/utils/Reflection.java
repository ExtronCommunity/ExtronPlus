package com.supercity.main.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class Reflection {

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
