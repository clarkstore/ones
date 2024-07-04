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

package com.ones.wx.mini.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 小程序配置
 *
 * @author Clark
 * @version 2021-03-18
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "os.wxmini")
public class OsWxminiProperties {
    /**
     * 设置微信小程序的appid
     */
    private String appid;
    /**
     * 设置微信小程序的Secret
     */
    private String secret;
    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token = "onestop";
    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey = "SRXP07cF633daFYh7jasMRDHYMKjANeEQe8kwrfWB5p";
    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat = "JSON";
}
