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

package com.ones.common.web.autoconfigure;

import com.ones.common.web.util.OsAesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AES加解密配置
 * @author Clark
 * @version 2021-09-10
 */
@Configuration
@EnableConfigurationProperties(OsAesProperties.class)
@ConditionalOnProperty(value = {"os.aes.secret"})
public class OsAesConfiguration {
    @Autowired
    private OsAesProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public OsAesUtils osAesUtils() {
        OsAesUtils utils = new OsAesUtils(this.properties.getSecret());
        return utils;
    }
}
