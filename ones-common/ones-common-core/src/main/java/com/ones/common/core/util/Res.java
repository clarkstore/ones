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

package com.ones.common.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T> 泛型
 * @author Clark
 * @version 2020-07-22
 */
@Getter
@Setter
@ToString
public class Res<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回标记：成功标记=0，失败标记=1
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> ok() {
        return restResult(MsgCode.SUCCESS, null, null);
    }

    /**
     * 成功
     * @param msg 消息
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> ok(String msg, T data) {
        return restResult(MsgCode.SUCCESS, msg, data);
    }

    /**
     * 成功
     * @param data 数据
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> ok(T data) {
        return restResult(MsgCode.SUCCESS, null, data);
    }
    /**
     * 失败
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> failed() {
        return restResult(MsgCode.FAIL, null, null);
    }
    /**
     * 失败
     * @param msg 失败消息
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> failed(String msg) {
        return restResult(MsgCode.FAIL, msg, null);
    }

    /**
     * 失败
     * @param data 数据
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> failed(T data) {
        return restResult(MsgCode.FAIL, null, data);
    }

    /**
     * 失败
     * @param code 错误码
     * @param msg 失败消息
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> failed(int code, String msg) {
        return restResult(code, msg, null);
    }

    /**
     * 返回结果
     * @param code 结果标识
     * @param msg 消息
     * @param data 数据
     * @param <T> 泛型
     * @return Res 返回结果
     */
    public static <T> Res<T> restResult(int code, String msg, T data) {
        Res<T> apiResult = new Res<T>();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }

    /**
     * 消息常量类
     * code：0 / 1将不返回消息
     * 提示消息：1XX
     * 错误消息：2XX
     */
    public static class MsgCode {
        /**
         * 成功标记：0
         */
        public static final Integer SUCCESS = 0;
        /**
         * 失败标记：1
         */
        public static final Integer FAIL = 1;
    }
}