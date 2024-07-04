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

package com.ones.kit.extra.autoconfigure;

import com.ones.extra.toolkit.OsSeqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 流水号配置
 * @author Clark
 * @version 2023-02-16
 */
@Configuration
@EnableConfigurationProperties(OsSeqProperties.class)
@ConditionalOnProperty(value = {"os.seq.seqLength"})
public class OsSeqConfiguration {
    @Autowired
    private OsSeqProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public OsSeqUtils osSeqUtils() {
        OsSeqUtils utils = new OsSeqUtils(this.properties);
        return utils;
    }
}
