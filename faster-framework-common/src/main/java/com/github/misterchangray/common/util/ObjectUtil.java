package com.github.misterchangray.common.util;

import java.util.List;
import java.util.Objects;

public class ObjectUtil {

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public static boolean nonNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public static boolean isNullOrEmpty(List obj) {
        return isNull(obj) || obj.size() == 0;
    }

    public static boolean isNullOrEmpty(String obj) {
        return isNull(obj) || obj.length() == 0;
    }

}
