package com.supercity.main.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ListUtils {

    public static <T> List<T> filter(Iterable<T> list, Predicate<T> filter) {
        List<T> newList = new ArrayList<>();
        for (T obj : list) {
            if (filter.test(obj)) {
                newList.add(obj);
            }
        }
        return newList;
    }

    public static boolean containsIgnoreCase(Collection<String> col, String s) {
        for (String str : col) {
            if (str.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T firstMatch(Iterable<T> list, Predicate<T> filter) {
        List<T> l = filter(list,filter);
        return l.size() > 0 ? l.get(0) : null;
    }

    public static <T> int firstIndex(Iterable<T> list, Predicate<T> filter) {
        int i = 0;
        for (T obj : list) {
            if (filter.test(obj)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T,R> Collection<R> convertAll(Iterable<T> list, Function<T,R> converter) {
        Collection<R> newList = new ArrayList<>();
        for (T obj : list) {
            newList.add(converter.apply(obj));
        }
        return newList;
    }

    public static <T,R> Supplier<Collection<R>> convertAllSupply(Supplier<Iterable<T>> listSupply, Function<T,R> converter) {
        return () -> {
            Iterable<T> iter = listSupply.get();
            Collection<R> newList = new ArrayList<>();
            for (T t : iter) {
                newList.add(converter.apply(t));
            }
            return newList;
        };
    }

    public static Collection<String> toStringAll(Collection<?> list) {
        Collection<String> newList = new ArrayList<>();
        for (Object o : list) {
            newList.add(o.toString());
        }
        return newList;
    }

    public static <T,R> Collection<R> convertAllArray(T[] arr, Function<T,R> converter) {
        Collection<R> newList = new ArrayList<>();
        for (T obj : arr) {
            newList.add(converter.apply(obj));
        }
        return newList;
    }

    public static <T> T[] subArray(T[] arr, int startIndex) {
        return Arrays.copyOfRange(arr,startIndex,arr.length);
    }

    public static <T> T[] subArray(T[] arr, int startIndex, int endIndex) {
        return Arrays.copyOfRange(arr,startIndex,endIndex);
    }

    public static <T> Collection<T> eliminate(Iterable<T> list, Predicate<T> filter) {
        Collection<T> newList = new ArrayList<>();
        for (T obj : list) {
            if (!filter.test(obj)) {
                newList.add(obj);
            }
        }
        return newList;
    }

    public static <T> Collection<String> lowerCaseStringAll(Iterable<T> list) {
        Collection<String> newList = new ArrayList<>();
        for (T s : list) {
            newList.add(s.toString().toLowerCase());
        }
        return newList;
    }

    public static <T> Collection<Object> lowerCaseStringAll(T[] arr) {
        Collection<Object> newList = new ArrayList<>();
        for (T s : arr) {
            newList.add(s.toString().toLowerCase());
        }
        return newList;
    }

    public static <T> boolean arrayContains(T[] arr, T obj) {
        for (T t : arr) {
            if (t != null && obj != null) {
                if (t.equals(obj)) {
                    return true;
                }
            } else if (t == obj) {
                return true;
            }
        }
        return false;
    }
}
