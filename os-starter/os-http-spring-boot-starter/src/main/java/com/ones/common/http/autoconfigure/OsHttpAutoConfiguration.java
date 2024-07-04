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

package com.ones.common.http.autoconfigure;

import com.ones.common.http.aspect.OsRepeatSubmitAspect;
import com.ones.common.http.interceptor.OsAccessLimitInterceptor;
import com.ones.common.redis.util.OsRedisUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * os-common-http配置
 * @author Clark
 * @version 2022-05-26
 */
@AutoConfiguration
public class OsHttpAutoConfiguration {
    /**
     * 限流拦截器
     *
     * @return OsAccessLimitInterceptor
     */
    @Bean
    @ConditionalOnBean(OsRedisUtils.class)
    public OsAccessLimitInterceptor osAccessLimitInterceptor() {
        OsAccessLimitInterceptor osAccessLimitInterceptor = new OsAccessLimitInterceptor();
        return osAccessLimitInterceptor;
    }
    /**
     * 重复提交Aop切面
     *
     * @return OsRepeatSubmitAspect
     */
    @Bean
    @ConditionalOnBean(OsRedisUtils.class)
    public OsRepeatSubmitAspect osRepeatSubmitAspect() {
        OsRepeatSubmitAspect osRepeatSubmitAspect = new OsRepeatSubmitAspect();
        return osRepeatSubmitAspect;
    }
}
