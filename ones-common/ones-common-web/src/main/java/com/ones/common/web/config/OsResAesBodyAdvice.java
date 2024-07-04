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

import cn.hutool.json.JSONUtil;
import com.ones.common.core.util.Res;
import com.ones.common.web.annotation.OsResAes;
import com.ones.common.web.util.OsAesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

/**
 * 实现统一处理controller返回
 *
 * @author Clark
 * @version 2022-12-27
 */
@RestControllerAdvice("com.ones")
public class OsResAesBodyAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 加密工具类
     */
    @Autowired(required = false)
    protected OsAesUtils aesUtils;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        boolean isIntercept = true;
        //拦截OsResAesAnnotation注释
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        OsResAes resAesAnnotation = AnnotationUtils.findAnnotation(annotatedElement, OsResAes.class);
        if (resAesAnnotation == null) {
            isIntercept = false;
        }

        //拦截指定方法
//        Method method = returnType.getMethod();
//        if ("test".equals(method.getName())) {
//            isIntercept = true;
//        }
        return isIntercept;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType
            , Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 全局未定义加密配置
        if (this.aesUtils == null) {
            return body;
        }
        if (body instanceof Res) {
            Res res = (Res) body;
            // 继承类可重写统一返回处理
            if (res.getData() != null) {
                // 数据加密
                String encrypt = this.aesUtils.aesEncrypt(JSONUtil.toJsonStr(res.getData()));
                res.setData(encrypt);
            }
        }

        return body;
    }
}