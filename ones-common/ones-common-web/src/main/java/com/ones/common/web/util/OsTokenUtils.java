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

package com.ones.common.web.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;

import java.util.Date;
import java.util.Map;

/**
 * Token工具类：基于hutool JWT
 *
 * @author Clark
 * @version 2021-09-13
 */
public class OsTokenUtils {
    /**
     * 设置token的Secret
     */
    private String secret;

    public OsTokenUtils(String secret) {
        this.secret = secret;
    }

    /**
     * 生成签名
     *
     * @return 签名
     */
    public String sign() {
        return this.sign(null);
    }

    /**
     * 生成签名
     * 设置默认过期时间：7200秒
     *
     * @param payload 载荷信息
     * @return 签名
     */
    public String sign(Map<String, Object> payload) {
        //判断是否有过期时间
        if (!payload.containsKey(JWTPayload.EXPIRES_AT)) {
            // 设置默认过期时间：7200秒
            Date date = DateUtil.offsetSecond(DateUtil.date(), 7200);
            payload.put(JWTPayload.EXPIRES_AT, date);
        }

        // 密钥
        byte[] key = this.secret.getBytes();
        String token = JWTUtil.createToken(payload, key);
        return token;
    }

    /**
     * 检验token是否正确
     *
     * @param token Token
     * @return boolean
     */
    public boolean verify(String token) {
        // 密钥
        byte[] key = this.secret.getBytes();
        JWT jwt = JWT.of(token).setKey(key);
        try {
            JWTValidator.of(jwt).validateDate();
        } catch (ValidateException e) {
            return false;
        }

        return jwt.verify();
    }
}
