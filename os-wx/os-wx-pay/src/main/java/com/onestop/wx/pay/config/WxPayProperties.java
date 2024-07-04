package com.onestop.wx.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wxpay pay properties
 *
 * @author Clark
 * @version 2020-04-02
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    /**
     * 支付结果通知-异步回调
     */
    private String payNotifyPath;
    /**
     * 退款结果通知-异步回调
     */
    private String refundNotifyPath;
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;
    /**
     * 应用密钥
     */
    private String appSecret;
    /**
     * 同 apiKey 后续版本会舍弃
     */
    private String partnerKey;
    /**
     * 微信支付商户号
     */
    private String mchId;
//    /**
//     * 服务商应用编号
//     */
//    private String slAppId;
//    /**
//     * 服务商商户号
//     */
//    private String slMchId;
    /**
     * 商户平台「API安全」中的 API 密钥
     */
    private String apiKey;
    /**
     * 应用域名，回调中会使用此参数
     */
    private String domain;
    /**
     * API 证书中的 p12：piclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String certPath;
    /**
     * 商户平台「API安全」中的 APIv3 密钥
     */
    private String apiKey3;
    /**
     * APIv3 证书中的 key.pem
     */
    private String keyPemPath;
    /**
     * APIv3 证书中的 cert.pem
     */
    private String certPemPath;
    /**
     * 微信支付终端IP
     */
    private String spbillCreateIp;
}
