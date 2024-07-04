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

package com.ones.common.http.interceptor;

import cn.hutool.core.util.StrUtil;
import com.ones.common.http.constant.OsLimitTypeEnum;
import com.ones.common.http.exception.OsAccessLimitException;
import com.ones.common.http.util.OsIPUtils;
import com.ones.common.http.annotation.OsAccessLimit;
import com.ones.common.redis.util.OsRedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * 限流拦截器
 *
 * @author Clark
 * @version 2021/5/10
 */

@Slf4j
public class OsAccessLimitInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private OsRedisUtils osRedisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)
            throws Exception {
        if (this.osRedisUtils == null) {
            return true;
        }
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //判断该方法上是否有自定义注解@OsAccessLimit
        if (method.isAnnotationPresent(OsAccessLimit.class)) {
            OsAccessLimit accessLimit = method.getAnnotation(OsAccessLimit.class);
            String key;
            //获取注解属性值
            long count = accessLimit.limitCount();
            long sec = accessLimit.limitSec();
            OsLimitTypeEnum limitType = accessLimit.limitType();

            switch (limitType) {
                case IP:
                    key = OsIPUtils.getClientIpAddress(request);
                    break;
                case CUSTOMER:
                    key = accessLimit.key();
                    // 未配置key，使用方法名为key
                    if (StrUtil.isNotBlank(key)) {
                        break;
                    }
                default:
                    //类名_方法名
                    StringJoiner sj = new StringJoiner(StrUtil.UNDERLINE);
                    sj.add(method.getDeclaringClass().getSimpleName());
                    sj.add(method.getName());
                    key = sj.toString();
            }

            //从redis中获取记录
            log.debug("==========OsAccessLimitInterceptor=========");
            log.debug("====key= " + key);
            Object maxLimit = osRedisUtils.get(key);
            if (maxLimit == null) {
                //第一次，计数器设置为1，设置redis过期时间
                this.osRedisUtils.set(key, 1, sec);
            } else if (Long.parseLong(maxLimit.toString()) < count) {
                //计数器加1
                this.osRedisUtils.incr(key);
            } else {
                throw new OsAccessLimitException();
            }
        }

        return true;
    }
}