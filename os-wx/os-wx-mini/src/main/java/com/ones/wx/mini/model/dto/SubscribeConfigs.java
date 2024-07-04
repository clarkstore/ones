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

package com.ones.wx.mini.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.Map;

/**
 * 订阅消息配置列表类
 * @author Clark
 * @version 2021-03-18
 */
@Builder
@Getter
@Setter
@ToString
public class SubscribeConfigs {
    @Tolerate
    public SubscribeConfigs() {
    }

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    private String miniprogramState;

    private Map<String, SubscribeDto> configMap;

    /**
     * 获取配置
     * @param msgId 消息id
     * @return SubscribeDto
     */
    public SubscribeDto getConfig(String msgId) {
        if (this.configMap != null) {
            return this.configMap.get(msgId);
        }
        return null;
    }
}
