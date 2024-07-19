package com.ones.mqtt.server.autoconfigure;

import com.ones.mqtt.server.listener.OsMqttConnectStatusListener;
import com.ones.mqtt.server.listener.OsMqttServerMessageListener;
import com.ones.mqtt.server.util.OsMqttServerUtils;
import net.dreamlu.iot.mqtt.core.server.event.IMqttConnectStatusListener;
import net.dreamlu.iot.mqtt.core.server.event.IMqttMessageListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * 自动配置
 *
 * @author Clark
 * @version 2024-07-19
 */
@AutoConfiguration
@ConditionalOnProperty(value = "mqtt.server.enabled", havingValue = "true")
public class OsMqttServerAutoConfiguration {
    /**
     *
     * @return
     */
    @Bean
    @Order
    @ConditionalOnMissingBean
    public IMqttConnectStatusListener mqttConnectStatusListener() {
        return new OsMqttConnectStatusListener();
    }

    /**
     *
     * @return
     */
    @Bean
    @Order
    @ConditionalOnMissingBean
    public IMqttMessageListener mqttServerMessageListener() {
        return new OsMqttServerMessageListener();
    }

    /**
     * 构建服务端工具类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public OsMqttServerUtils osMqttServerUtils() {
        OsMqttServerUtils utils = new OsMqttServerUtils();
        return utils;
    }
}