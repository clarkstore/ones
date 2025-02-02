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

import cn.hutool.core.map.MapUtil;
import com.ones.wx.mini.model.dto.SubscribeConfigs;
import com.ones.wx.mini.model.dto.SubscribeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 小程序订阅消息配置
 * @author Clark
 * @version 2021-03-18
 */
@Configuration
@EnableConfigurationProperties(OsWxminiSubscribeProperties.class)
@ConditionalOnProperty(prefix = "os.wxmini.subscribe", name = "enabled", havingValue = "true")
public class OsWxminiSubscribeConfiguration {
    @Autowired
    private OsWxminiSubscribeProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public SubscribeConfigs subscribeConfigs() {
        Map<String, SubscribeDto> configMap = MapUtil.newHashMap();
        this.properties.getConfigs().forEach(item -> {
            configMap.put(item.getMsgId(), item);
        });

        return SubscribeConfigs.builder()
                .configMap(configMap)
                .miniprogramState(this.properties.getMiniprogramState())
                .build();
    }
}
