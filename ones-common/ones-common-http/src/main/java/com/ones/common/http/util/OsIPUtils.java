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

package com.ones.common.http.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 *
 * @author Clark
 * @version 2021/5/12
 */
@Slf4j
public class OsIPUtils {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取客户端的IP地址<br/>
     * 注意本地测试访问项目地址时，浏览器请求不要用 localhost，请用本机IP；否则，取不到 IP
     *
     * nginx配置
     * location ^~ /wxmini/ {
     *     proxy_set_header        X-Real-IP       $remote_addr;
     *     proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
     *     proxy_pass http://127.0.0.1:9931/wxmini/;
     * }
     *
     * @param request
     * @return IP地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String headerName = "x-forwarded-for";
        String ip = request.getHeader(headerName);
        if (null != ip && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个IP才是真实IP,它们按照英文逗号','分割
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (checkIp(ip)) {
            headerName = "Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "WL-Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_CLIENT_IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_X_FORWARDED_FOR";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "X-Real-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "remote addr";
            ip = request.getRemoteAddr();
            // 127.0.0.1 ipv4, 0:0:0:0:0:0:0:1 ipv6
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        log.debug("getClientIp  IP is " + ip + ", headerName = " + headerName);
        return ip;
    }

    private static boolean checkIp(String ip) {
        if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }
}
