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

package com.ones.ali.sms.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

/**
 * 阿里短信服务工具类
 *
 * @author Clark
 * @version 2021/8/2
 */
public class OsSmsUtils {
    /**
     * 短信客户端
     */
    private Client client;

    /**
     * 初始化配置
     */
    public OsSmsUtils(String accessKeyId, String accessKeySecret) throws Exception {
        // 设置公共请求参数，初始化Client。
        Config config = new Config()
                // 访问的域名
                .setEndpoint("dysmsapi.aliyuncs.com")
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)// 您的AccessKey ID。)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);// 您的AccessKey Secret。);
        this.client = new Client(config);
    }


    /**
     * 发送短信
     *
     * @param smsRequest
     * @return SendSmsResponse
     */
    public SendSmsResponse sendSms(SendSmsRequest smsRequest) throws Exception {
        SendSmsResponse res = client.sendSms(smsRequest);
        return res;
    }
}
