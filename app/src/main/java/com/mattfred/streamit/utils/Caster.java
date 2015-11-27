package com.mattfred.streamit.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for casting objects
 */
public class Caster {

    public static <T> List<T> castCollection(List srcList, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Object object : srcList) {
            if (object != null && clazz.isAssignableFrom(object.getClass()))
                list.add(clazz.cast(object));
        }
        return list;
    }
}
