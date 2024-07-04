package com.onestop.wx.pay.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.enums.ReceiverType;
import com.ijpay.wxpay.model.*;
import com.onestop.common.core.exception.PayException;
import com.onestop.wx.pay.config.WxPayProperties;
import com.onestop.wx.pay.model.dto.OrderQueryDto;
import com.onestop.wx.pay.model.dto.RefundDto;
import com.onestop.wx.pay.model.dto.UnifiedOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 微信支付工具类
 * <p>JSAPI支付</p>
 *
 * @author Clark
 * @version 2022-10-24
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayUtils {
    /**
     * 微信支付配置属性
     */
    @Autowired
    private WxPayProperties properties;
    /**
     * 微信支付配置
     */
    private WxPayApiConfig apiConfig;

    private String payNotifyUrl;
    private String refundNotifyUrl;

    /**
     * 初始化配置
     */
    @PostConstruct
    public void initConfig() {
        try {
            apiConfig = WxPayApiConfigKit.getApiConfig(properties.getAppId());
        } catch (Exception e) {
            apiConfig = WxPayApiConfig.builder()
                    .appId(properties.getAppId())
                    .mchId(properties.getMchId())
                    .partnerKey(properties.getPartnerKey())
                    .certPath(properties.getCertPath())
                    .domain(properties.getDomain())
                    .build();
        }
        payNotifyUrl = apiConfig.getDomain().concat(properties.getPayNotifyPath());
        refundNotifyUrl = apiConfig.getDomain().concat(properties.getRefundNotifyPath());

        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);
    }

    /**
     * 统一下单
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param dto
     */
    public Map<String, String> unifiedOrder(UnifiedOrderDto dto) throws PayException {
        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(properties.getAppId())
                .mch_id(apiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .body(dto.getBody())
                .attach(dto.getAttach())
                .out_trade_no(dto.getOutTradeNo())
                .total_fee(dto.getTotalFee())
                .spbill_create_ip(this.properties.getSpbillCreateIp())
                .notify_url(payNotifyUrl)
                .trade_type(dto.getTradeType())
                .openid(dto.getOpenid())
                .profit_sharing(dto.getProfitSharing())
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5);

        String result = WxPayApi.pushOrder(false, params);
        log.info(result);

        Map<String, String> resultMap = this.xmlToMap(result);

        // 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
        String prepayId = resultMap.get("prepay_id");
        log.debug("prepay_id = " + prepayId);

        switch (dto.getTradeType()) {
            case "MWEB": {
                // resultMap.get("mweb_url")) 获取支付url
                return resultMap;
            }
            case "APP": {
                return null;
            }
            case "NATIVE": {
                // resultMap.get("code_url")) 获取二维码url
                // 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片
                return resultMap;
            }
            case "JSAPI": {
                Map<String, String> payResult = WxPayKit.prepayIdCreateSign(prepayId, apiConfig.getAppId(),
                        apiConfig.getPartnerKey(), SignType.MD5);

                return payResult;
            }
            default: {
                throw new PayException("E101", "该交易类型暂不支持");
            }
        }
    }

    /**
     * 查询订单
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用付款码支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
     *
     * @param dto
     */
    public Map<String, String> orderQuery(OrderQueryDto dto) {
        Map<String, String> params = OrderQueryModel
                .builder()
                .appid(apiConfig.getAppId())
                .mch_id(apiConfig.getMchId())
                .transaction_id(dto.getTransactionId())
                .out_trade_no(dto.getOutTradeNo())
                .nonce_str(WxPayKit.generateStr())
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5);
        String result = WxPayApi.orderQuery(params);
        log.debug("查询订单:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 申请退款
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 接口地址：https://api.mch.weixin.qq.com/pay/refund
     *
     * @param dto
     * @return
     */
    public Map<String, String> refund(RefundDto dto) {
        if (StrUtil.isBlank(dto.getTransactionId()) && StrUtil.isBlank(dto.getOutTradeNo())) {
//            return "transactionId、out_trade_no二选一";
        }
        Map<String, String> params = RefundModel.builder()
                .appid(apiConfig.getAppId())
                .mch_id(apiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .transaction_id(dto.getTransactionId())
                .out_trade_no(dto.getOutTradeNo())
                .out_refund_no(dto.getOutRefundNo())
                .total_fee(dto.getTotalFee())
                .refund_fee(dto.getRefundFee())
                .refund_desc(dto.getRefundDesc())
                .notify_url(refundNotifyUrl)
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5);

        String result = WxPayApi.orderRefund(false, params, apiConfig.getCertPath(), apiConfig.getMchId());
        log.debug("申请退款:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 查询退款
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口地址：https://api.mch.weixin.qq.com/pay/refundquery
     *
     * @param outTradeNo
     * @param outRefundNo
     * @return
     */
    public Map<String, String> refundQuery(String outTradeNo, String outRefundNo) {
        Map<String, String> params = RefundQueryModel.builder()
                .appid(apiConfig.getAppId())
                .mch_id(apiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .out_trade_no(outTradeNo)
                .out_refund_no(outRefundNo)
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5);

        String result = WxPayApi.orderRefundQuery(false, params);
        log.debug("查询退款:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 支付结果通知
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7&index=8
     *
     * @param xmlData
     * @return
     */
    public String payNotify(String xmlData) {
        Map<String, String> params = WxPayKit.xmlToMap(xmlData);

        String returnCode = params.get("return_code");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 注意此处签名方式需与统一下单的签名类型一致
        if (WxPayKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPartnerKey())) {
            if (WxPayKit.codeIsOk(returnCode)) {
                log.debug("支付结果通知成功，商户订单号:" + params.get("out_trade_no"));
                // 更新订单信息
                // 发送通知等
                Map<String, String> xml = MapUtil.newHashMap();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return WxPayKit.toXml(xml);
            }
        }
        return null;
    }

    /**
     * 退款结果通知
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_16&index=10
     *
     * @param xmlData
     * @return
     */
    public String refundNotify(String xmlData) {
        Map<String, String> params = WxPayKit.xmlToMap(xmlData);

        String returnCode = params.get("return_code");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        if (WxPayKit.codeIsOk(returnCode)) {
            String reqInfo = params.get("req_info");
            String decryptData = WxPayKit.decryptData(reqInfo, apiConfig.getPartnerKey());
            log.debug("退款通知通知成功，退款通知解密后的数据=" + decryptData);
            // 更新订单信息
            // 发送通知等
            Map<String, String> xml = MapUtil.newHashMap();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return WxPayKit.toXml(xml);
        }
        return null;
    }

    /**
     * 分账
     * @return
     * @throws PayException
     */
    @Deprecated
    public Map<String, String> profitSharing() throws PayException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("type", ReceiverType.WECHATID.getType());
        map.put("account", "changziyu");
        map.put("amount", 10);
        map.put("description","分到个人");
        list.add(map);
        String receivers = JSONUtil.toJsonStr(list);

        Map<String, String> params = ProfitSharingModel.builder()
                .nonce_str(WxPayKit.generateStr())
                .mch_id(apiConfig.getMchId())
                .appid(apiConfig.getAppId())
                .transaction_id("4200000567202004174355500726")
                .out_order_no("12345678902")
                .receivers(receivers)
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.HMACSHA256, false);

        String result = WxPayApi.profitSharing(params, apiConfig.getCertPath(), apiConfig.getMchId());
        log.info("请求单次分账:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 执行结果xml转Map
     *
     * @param result
     * @return
     */
    private Map<String, String> xmlToMap(String result) {
        Map<String, String> map = WxPayKit.xmlToMap(result);
        String returnCode = map.get("return_code");
        String resultCode = map.get("result_code");
        if (WxPayKit.codeIsOk(returnCode) && WxPayKit.codeIsOk(resultCode)) {
            // 成功
            return map;
        } else {
            // 失败
            throw new PayException(map.get("err_code"), map.get("err_code_des"));
        }
    }
}
