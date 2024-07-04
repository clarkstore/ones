package com.ones.common.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重复提交
 * @author Clark
 * @version 2023/3/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OsRepeatSubmit {
    /**
     * 过期时间：单位秒，默认值：5秒
     */
    int expireTime() default 5;
}
