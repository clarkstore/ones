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

package com.ones.mqtt.common.constant;

import cn.hutool.core.text.StrPool;
import net.dreamlu.iot.mqtt.core.common.TopicFilterType;

/**
 * 常量类
 *
 * @author Clark
 * @version 2023-11-28
 */
public interface OsMqttConsts {
    /**
     * topic常量类
     */
    interface TopicConsts {
        /**
         * 共享订阅关键字：MQTT 3.1.1协议
         */
        String SHARE_QUEUE_PREFIX = TopicFilterType.SHARE_QUEUE_PREFIX;

        /**
         * 共享订阅关键字：MQTT 5.0协议
         */
        String SHARE_GROUP_PREFIX = TopicFilterType.SHARE_GROUP_PREFIX;

        /**
         * 客户端上线
         */
        String CLIENT_ONLINE = "client/online";
        /**
         * 客户端全部信息主题
         */
        String TOPIC_CLIENT_LIST = "client/list";

        /**
         * 未知客户端上线主题
         */
        String TOPIC_CLIENT_ONLINE_UNKNOWN = CLIENT_ONLINE + StrPool.SLASH + "unknown";

        /**
         * 客户端上线主题
         */
        String TOPIC_CLIENT_ONLINE_ID = CLIENT_ONLINE + StrPool.SLASH + "id";

        /**
         * 客户端上线集合主题
         */
        String TOPIC_CLIENT_ONLINE_LIST = CLIENT_ONLINE + StrPool.SLASH + "list";

        /**
         * 客户端离线
         */
        String CLIENT_OFFLINE = "client/offline";

        /**
         * 未知客户端离线
         */
        String TOPIC_CLIENT_OFFLINE_UNKNOWN = CLIENT_OFFLINE + StrPool.SLASH + "unknown";

        /**
         * 客户端离线主题
         */
        String TOPIC_CLIENT_OFFLINE_ID = CLIENT_OFFLINE + StrPool.SLASH + "id";

        /**
         * 客户端离线集合主题
         */
        String TOPIC_CLIENT_OFFLINE_LIST = CLIENT_OFFLINE + StrPool.SLASH + "list";
    }
}