package com.github.misterchangray.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ray.chang on 2022/1/15 on 2018-12-31
 */
public final class SetUtils {

    private SetUtils() {
        //empty
    }

    public static <T> Set<T> of(T... t) {
        return new HashSet<>(Arrays.asList(t));
    }

    public static <T> Set<T> createHashSet(int size) {
        return new HashSet<>((int) (size / .75) + 1);
    }
}
