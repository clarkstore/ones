package com.ones.mqtt.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.dreamlu.iot.mqtt.codec.MqttQoS;

import java.io.Serializable;

/**
 * 消息Dto
 */
@Getter
@Setter
@ToString
public class OsMqttMsgDto implements Serializable {
    /**
     * 客户端Id
     */
    private String clientId;
    /**
     * 主题
     */
    private String topic = "test/connected";
    /**
     * QoS
     */
    private MqttQoS qoS = MqttQoS.QOS2;
    /**
     * 消息体
     */
    private String payload = "test succeed";
    /**
     * 是否在服务器上保留消息
     */
    private boolean retain = Boolean.FALSE;
}
