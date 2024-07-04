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

package com.ones.rocketmq.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author Clark
 * @version 2021-10-20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "os.rocketmq")
public class OsRocketMqProperties {
    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    /**
     * 普通消息
     */
    private String topic;
    private String groupId;
    private String tag = "*";
    /**
     * 顺序消息
     */
    private String orderTopic;
    private String orderGroupId;
    private String orderTag = "*";

    /**
     * 获取配置
     * @return Properties
     */
    public Properties getMqPropertie() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }
}
