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

package com.onestop.common.sentinel.autoconfigure;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.onestop.common.sentinel.handler.OsBlockHandler;
import com.onestop.common.sentinel.handler.OsSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * Sentinel-Feign配置
 *
 * @author Clark
 * @version 2021-07-20
 */
@Configuration
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class OsSentinelAutoConfiguration {
    @Bean
    @Scope("prototype")
    @ConditionalOnClass({SphU.class, Feign.class})
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = {"feign.sentinel.enabled"})
    @Primary
    public Feign.Builder feignSentinelBuilder() {
        return OsSentinelFeign.builder();
    }

    /**
     * 不可与网关限流降级并用：os.sentinel.app代表独立服务，os.sentinel.gateway代表网关
     */
    @Bean
    @ConditionalOnProperty(name = {"os.sentinel.app"})
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new OsBlockHandler();
    }
}
