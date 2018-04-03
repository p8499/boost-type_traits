package com.p8499.lang.ime.rime;

import java.util.List;

/**
 * Created by Administrator on 1/20/2018.
 */

public class Utils {
    public static <T> boolean equals(T o1, T o2) {
        return o1 == null && o2 == null
                || o1 != null && o2 != null && o1.equals(o2);
    }

    public static <T> boolean equals(T[] a1, T[] a2) {
        if (a1 == null && a2 == null)
            return true;
        if (a1 == null || a2 == null || a1.length != a2.length)
            return false;
        for (int i = 0; i < a1.length; i++)
            if (!equals(a1[i], a2[i]))
                return false;
        return true;
    }

    public static <T> boolean equals(List<T> a1, List<T> a2) {
        if (a1 == null && a2 == null)
            return true;
        if (a1 == null || a2 == null || a1.size() != a2.size())
            return false;
        for (int i = 0; i < a1.size(); i++)
            if (!equals(a1.get(i), a2.get(i)))
                return false;
        return true;
    }
}
