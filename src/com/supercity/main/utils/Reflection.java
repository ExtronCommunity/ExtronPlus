package com.supercity.main.utils;

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
}
