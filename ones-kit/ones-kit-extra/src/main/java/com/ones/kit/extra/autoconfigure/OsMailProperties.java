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

package com.ones.kit.extra.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mail配置
 *
 * @author Clark
 * @version 2021-02-24
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "os.mail")
public class OsMailProperties {
	/**
	 * 邮件服务器的SMTP地址
	 */
	private String host = "smtp.126.com";
	/**
	 * 邮件服务器的SMTP端口
	 */
	private Integer port = 25;
	/**
	 * 发件人邮箱（必须正确，否则发送失败）
	 */
	private String from;
	/**
	 * 发件人（必须正确，否则发送失败）
	 */
	private String user;
	/**
	 * 密码（注意，某些邮箱需要为SMTP服务单独设置密码，详情查看相关帮助）
	 */
	private String pass;
	/**
	 * 使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展
	 */
	private boolean starttlsEnable = false;
	/**
	 * 使用SSL安全连接
	 */
	private boolean sslEnable = false;
}