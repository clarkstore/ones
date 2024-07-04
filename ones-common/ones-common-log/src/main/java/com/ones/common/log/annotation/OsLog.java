package com.ones.common.log.annotation;

import java.lang.annotation.*;

/**
 * 业务日志注解
 *
 * @author Clark
 * @version 2022-03-01
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OsLog {
    /**
     * 接口名
     */
    String value() default "";
}
