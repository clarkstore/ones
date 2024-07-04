package com.ones.mqtt.client.autoconfigure;

import com.ones.mqtt.client.util.OsMqttClientUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置
 *
 * @author Clark
 * @version 2024-06-18
 */
@AutoConfiguration
public class OsMqttClientAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public OsMqttClientUtils osMqttClientUtils() {
        OsMqttClientUtils utils = new OsMqttClientUtils();
        return utils;
    }
}