/*
 *
 *  * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.ones.common.http.annotation;

import com.ones.common.http.constant.OsLimitTypeEnum;

import java.lang.annotation.*;

/**
 * 限流
 *
 * @author Clark
 * @version 2021/5/8
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OsAccessLimit {
    /**
     * 自定义Key
     */
    String key() default "";
    /**
     * 单位时间限制通过请求数、默认200次
     */
    long limitCount() default 200;

    /**
     * 单位时间，单位秒、默认10秒
     */
    long limitSec() default 10;

    /**
     * 限制类型、默认方法名为Key
     */
    OsLimitTypeEnum limitType() default OsLimitTypeEnum.METHOD_NAME;
}
