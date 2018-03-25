package com.redsponge.extron.plus.utils;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
}
