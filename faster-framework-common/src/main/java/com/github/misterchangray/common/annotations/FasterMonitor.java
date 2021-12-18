package com.github.misterchangray.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 配置monitor食用更佳
 * @author: Ray.chang
 * @create: 2021-12-16 17:39
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface FasterMonitor {
    /**
     * 期望毫秒
     * @return
     */
    int expectMaxDelay() default 2000;

    /**
     * 作者
     * @return
     */
    String author() default "";
}
