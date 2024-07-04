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

import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * os-ali-rocketmq配置
 *
 * @author Clark
 * @version 2022-05-26
 */
@AutoConfiguration
@EnableConfigurationProperties(OsRocketMqProperties.class)
public class OsRocketmqAutoConfiguration {
    @Autowired
    private OsRocketMqProperties properties;

    @Autowired
    private LocalTransactionChecker localTransactionChecker;

    /**
     * 普通消息生产者
     *
     * @return ProducerBean
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        ProducerBean producer = new ProducerBean();
        producer.setProperties(properties.getMqPropertie());
        return producer;
    }

    /**
     * 顺序消息生产者
     *
     * @return OrderProducerBean
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(properties.getMqPropertie());
        return orderProducerBean;
    }

    /**
     * 事务消息生产者
     *
     * @return TransactionProducerBean
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerBean buildTransactionProducer() {
        TransactionProducerBean producer = new TransactionProducerBean();
        producer.setProperties(properties.getMqPropertie());
        producer.setLocalTransactionChecker(localTransactionChecker);
        return producer;
    }
}
