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

package com.ones.common.log.autoconfigure;

import com.ones.common.log.aspect.OsBizLogAspect;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * os-common-log配置
 * @author Clark
 * @version 2022-05-26
 */
@Setter
@AutoConfiguration
public class OsLogAutoConfiguration {
    private static final int MAX_POOL_SIZE = 10;

    private static final int CORE_POOL_SIZE = 5;
    /**
     * 自定义日志拦截器
     */
    @Value("${os.log.aspect:com.onestop.common.log.aspect.OsBizLogAspect}")
    private String className;

    @Bean("asyncLogExecutor")
    public AsyncTaskExecutor asyncLogExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("async-task-thread-pool-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    /**
     * 日志Aop切面
     *
     * @return OsBizLogAspect
     */
    @Bean
    @ConditionalOnMissingBean
    public OsBizLogAspect bizLogAspect() {
        //根据读取的配置文件创建类对象
        try {
            Class c1 = Class.forName(this.className);
            //根据类对象创建类实例
            OsBizLogAspect bizLogAspect = (OsBizLogAspect) c1.newInstance();
            return bizLogAspect;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}