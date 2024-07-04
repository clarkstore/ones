package com.ones.mqtt.client.listener;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.iot.mqtt.codec.MqttQoS;
import net.dreamlu.iot.mqtt.core.common.TopicFilterType;
import net.dreamlu.iot.mqtt.spring.client.MqttClientSubscribe;

import java.nio.charset.StandardCharsets;

/**
 * 客户端消息监听演示代码
 *
 * @author Clark
 * @version 2024-06-12
 */
@Slf4j
public class OsDemoMqttClientSubscribeListener {
	@MqttClientSubscribe(value = "online", qos = MqttQoS.QOS2)
	public void online(String topic, byte[] payload) {
		log.info("--------------------online----------------------");
		log.info("topic:{} payload:{}", topic, new String(payload, StandardCharsets.UTF_8));
	}

	@MqttClientSubscribe(value = "offline", qos = MqttQoS.QOS2)
	public void offline(String topic, byte[] payload) {
		log.info("--------------------offline----------------------");
		log.info("topic:{} payload:{}", topic, new String(payload, StandardCharsets.UTF_8));
	}

	@MqttClientSubscribe(TopicFilterType.SHARE_GROUP_PREFIX + "wcs1/abc")
	public void shareGroup1(String topic, byte[] payload) {
		log.error("----------wcs1-------------MyClientSubscribeListener");
		log.info("topic:{} payload:{}", topic, new String(payload, StandardCharsets.UTF_8));
	}

	@MqttClientSubscribe(TopicFilterType.SHARE_GROUP_PREFIX + "wcs2/abc")
	public void shareGroup2(String topic, byte[] payload) {
		log.error("----------wcs2-------------MyClientSubscribeListener");
		log.info("topic:{} payload:{}", topic, new String(payload, StandardCharsets.UTF_8));
	}
}