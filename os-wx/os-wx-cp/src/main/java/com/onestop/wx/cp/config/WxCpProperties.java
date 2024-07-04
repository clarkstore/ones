package com.onestop.wx.cp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Clark
 * @version 2020-08-25
 */
@Data
@ConfigurationProperties(prefix = "wx.cp")
public class WxCpProperties {
	/**
	 * 设置微信企业号的corpId
	 */
	private String corpId;
	/**
	 * 设置微信企业应用的AgentId
	 */
	private Integer agentId;
	/**
	 * 设置微信企业应用的Secret
	 */
	private String secret;
	/**
	 * 设置微信企业号的token
	 */
	private String token;
	/**
	 * 设置微信企业号的EncodingAESKey
	 */
	private String aesKey;
}