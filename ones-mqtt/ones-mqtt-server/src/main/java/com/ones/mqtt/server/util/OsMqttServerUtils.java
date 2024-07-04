package com.ones.mqtt.server.util;

import com.ones.mqtt.common.model.OsMqttMsgDto;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.core.server.MqttServer;
import net.dreamlu.iot.mqtt.spring.server.MqttServerTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.tio.utils.hutool.StrUtil;

import java.nio.charset.StandardCharsets;

/**
 * MqttServer工具类
 * @author Clark
 * @version 2024-06-11
 */
@Slf4j
@Component
public class OsMqttServerUtils implements SmartInitializingSingleton {
    @Resource
    private ApplicationContext applicationContext;
    private MqttServerTemplate server;

    /**
     * 单例 bean 初始化完成之后从 ApplicationContext 中获取 bean
     */
    @Override
    public void afterSingletonsInstantiated() {
        this.server = applicationContext.getBean(MqttServerTemplate.class);
    }

    /**
     * 发布消息
     * @param msgDto
     * @return
     */
    public boolean publish(OsMqttMsgDto msgDto) {
        boolean result;
        if (StrUtil.isBlank(msgDto.getClientId())) {
            result = this.server.publishAll(msgDto.getTopic(), msgDto.getPayload().getBytes(StandardCharsets.UTF_8), msgDto.getQoS(), msgDto.isRetain());
        } else {
            result = this.server.publish(msgDto.getClientId(), msgDto.getTopic(), msgDto.getPayload().getBytes(StandardCharsets.UTF_8), msgDto.getQoS(), msgDto.isRetain());
        }
        log.debug("publish:{}, result:{}", msgDto, result);
        return result;
    }
}