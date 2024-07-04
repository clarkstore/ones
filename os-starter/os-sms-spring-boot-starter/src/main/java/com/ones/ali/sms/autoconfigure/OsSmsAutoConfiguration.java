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

package com.ones.ali.sms.autoconfigure;

import com.ones.ali.sms.util.OsSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * os-ali-sms配置
 * @author Clark
 * @version 2022-05-26
 */
@AutoConfiguration
@EnableConfigurationProperties(OsSmsProperties.class)
@ConditionalOnProperty(value = {"os.sms.accessKeyId", "os.sms.accessKeySecret"})
public class OsSmsAutoConfiguration {
    @Autowired
    private OsSmsProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public OsSmsUtils osSmsUtils() {
        OsSmsUtils osSmsUtils = null;
        try {
            osSmsUtils = new OsSmsUtils(this.properties.getAccessKeyId(), this.properties.getAccessKeySecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return osSmsUtils;
    }
}
