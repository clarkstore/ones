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

package com.ones.common.http.config;

import com.ones.common.http.interceptor.OsAccessLimitInterceptor;
import com.ones.common.web.config.OsWebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * WEB初始化配置基类
 * 可以继承实现拦截器重写等
 *
 * @author Clark
 * @version 2022-04-26
 */
//@RestControllerAdvice
@Configuration
public class OsHttpWebConfig extends OsWebConfig {
    // TODO 可以继承自定义限流拦截器
    @Autowired(required = false)
    private OsAccessLimitInterceptor osAccessLimitInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (this.osAccessLimitInterceptor != null) {
            registry.addInterceptor(this.osAccessLimitInterceptor)
                    .addPathPatterns("/**");
        //  .excludePathPatterns("/不被拦截路径 通常为登录注册或者首页");
        }
    }
}
