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

package com.ones.wx.mp.autoconfigure;

import com.ones.wx.mp.model.dto.MenuConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * os-wx-mp配置
 * @author Clark
 * @version 2021-04-29
 */
@Configuration
@EnableConfigurationProperties(OsWxmpMenuProperties.class)
@ConditionalOnProperty(prefix = "os.wxmp.menu", name = "enabled", havingValue = "true")
public class OsWxmpMenuConfiguration {
    @Autowired
    private OsWxmpMenuProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public MenuConfigs menuConfigs() {
        MenuConfigs config = new MenuConfigs();
        config.setConfigs(this.properties.getConfigs());
        return config;
    }
}
