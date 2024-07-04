package com.ones.mqtt.server.api;

import com.ones.mqtt.common.model.OsMqttMsgDto;
import com.ones.mqtt.server.util.OsMqttServerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发布Api
 *
 * @author Clark
 * @version 2024-06-11
 */
@Slf4j
@Tag(name = "Mqtt::服务端")
@RequestMapping("/mqtt/server")
@RestController
public class OsMqttServerApi {
	@Resource
	private OsMqttServerUtils serverUtils;

	/**
	 * 发布消息
	 * @param msgDto
	 * @return
	 */
	@Operation(summary = "publish")
	@PostMapping("/publish")
	public boolean publish(@RequestBody OsMqttMsgDto msgDto) {
		return this.serverUtils.publish(msgDto);
	}
}