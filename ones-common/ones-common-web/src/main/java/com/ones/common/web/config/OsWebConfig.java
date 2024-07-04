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

package com.ones.common.web.config;

import com.ones.common.web.interceptor.OsTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB初始化配置基类
 * 可以继承实现拦截器重写等
 *
 * @author Clark
 * @version 2020-04-06
 */
//@RestControllerAdvice
@Configuration
public class OsWebConfig implements WebMvcConfigurer {
    // TODO 可以继承自定义Token拦截器
    @Autowired(required = false)
    protected OsTokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (this.tokenInterceptor != null) {
            registry.addInterceptor(this.tokenInterceptor)
                    .addPathPatterns("/**");
        //  .excludePathPatterns("/不被拦截路径 通常为登录注册或者首页");
        }
    }

    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Access-Control-Allow-Origin:*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
// TODO 根据业务重写以下方法
// TODO 是否需要对前端通过header传递参数，进行统一处理
//    @ModelAttribute(name = "openid")
//    public String getHeaderOpenid() {
//        return request.getHeader(WxConsts.HEADER_OPENID);
//    }
}
