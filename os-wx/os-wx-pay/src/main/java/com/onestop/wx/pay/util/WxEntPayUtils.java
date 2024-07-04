package com.onestop.wx.pay.util;

import cn.hutool.core.util.IdUtil;
import com.onestop.common.core.exception.PayException;
import com.onestop.wx.pay.config.WxPayProperties;
import com.onestop.wx.pay.model.dto.RedpackDto;
import com.onestop.wx.pay.model.dto.TransferDto;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.GetHbInfoModel;
import com.ijpay.wxpay.model.GetTransferInfoModel;
import com.ijpay.wxpay.model.SendRedPackModel;
import com.ijpay.wxpay.model.TransferModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 支付工具类
 * <p>现金红包</p>
 * <p>企业付款</p>
 *
 * @author Clark
 * @version 2020-04-02
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
public class WxEntPayUtils {
    /**
     * 微信支付配置属性
     */
    @Autowired
    private WxPayProperties properties;
    /**
     * 微信支付配置
     */
    private WxPayApiConfig apiConfig;

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

        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);
    }

    /**
     * 发放普通红包
     * <pre>
     * 现金红包发放后会以公众号消息的形式触达用户，不同情况下触达消息的形式会有差别，相关规则如下：
     * 1.已关注公众号的用户，使用“防伪消息”触达；
     * 2.未关注公众号的用户，使用“模板消息”触达。
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack
     * </pre>
     *
     * @param dto
     * @return
     * @throws PayException
     */
    public Map<String, String> sendRedpack(RedpackDto dto) throws PayException {
        Map<String, String> params = SendRedPackModel.builder()
                .nonce_str(IdUtil.objectId())
                .mch_billno(IdUtil.objectId())
                .mch_id(apiConfig.getMchId())
                .wxappid(apiConfig.getAppId())
                .send_name(dto.getSendName())
                .re_openid(dto.getReOpenid())
                .total_amount(dto.getTotalAmount())
                .total_num(dto.getTotalNum())
                .wishing(dto.getWishing())
                .client_ip(this.properties.getSpbillCreateIp())
                .act_name(dto.getActName())
                .remark(dto.getRemark())
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5, false);
        String result = WxPayApi.sendRedPack(params, apiConfig.getCertPath(), apiConfig.getMchId());
        log.info("现金红包:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 查询红包记录
     * <pre>
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_6&index=5
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo
     * </pre>
     *
     * @param mchBillno
     * @return
     * @throws PayException
     */
    public Map<String, String> getHbInfo(String mchBillno) throws PayException {
        Map<String, String> params = GetHbInfoModel.builder()
                .nonce_str(IdUtil.objectId())
                .mch_billno(mchBillno)
                .mch_id(apiConfig.getMchId())
                .appid(apiConfig.getAppId())
                .bill_type("MCHT")
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5, false);
        String result = WxPayApi.getHbInfo(params, apiConfig.getCertPath(), apiConfig.getMchId());
        return this.xmlToMap(result);
    }

    /**
     * 企业付款到零钱
     * <pre>
     * 企业付款业务是基于微信支付商户平台的资金管理能力，为了协助商户方便地实现企业向个人付款，针对部分有开发能力的商户，提供通过API完成企业付款的功能。
     * 比如目前的保险行业向客户退保、给付、理赔。
     * 企业付款将使用商户的可用余额，需确保可用余额充足。查看可用余额、充值、提现请登录商户平台“资金管理”https://pay.weixin.qq.com/进行操作。
     * 注意：与商户微信支付收款资金并非同一账户，需要单独充值。
     * 文档详见:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers
     * </pre>
     *
     * @param dto
     * @return
     * @throws PayException
     */
    public Map<String, String> transfer(TransferDto dto) throws PayException {
        Map<String, String> params = TransferModel.builder()
                .mch_appid(apiConfig.getAppId())
                .mchid(apiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .partner_trade_no(dto.getPartnerTradeNo())
                .openid(dto.getOpenid())
                .check_name(dto.getCheckName())
                .re_user_name(dto.getReUserName())
                .amount(dto.getAmount())
                .desc(dto.getDesc())
                .spbill_create_ip(this.properties.getSpbillCreateIp())
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5, false);
        // 提现
        String result = WxPayApi.transfers(params, apiConfig.getCertPath(), apiConfig.getMchId());
        log.info("企业付款到零钱:" + result);

        return this.xmlToMap(result);
    }

    /**
     * 查询企业付款到零钱
     * <pre>
     * 查询企业付款API
     * 用于商户的企业付款操作进行结果查询，返回付款操作详细结果。
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3
     * 接口链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo
     * </pre>
     *
     * @param partnerTradeNo 商户订单号
     * @return
     * @throws PayException
     */
    public Map<String, String> getTransferInfo(String partnerTradeNo) throws PayException {
        Map<String, String> params = GetTransferInfoModel.builder()
                .nonce_str(WxPayKit.generateStr())
                .partner_trade_no(partnerTradeNo)
                .mch_id(apiConfig.getMchId())
                .appid(apiConfig.getAppId())
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.MD5, false);

        String result = WxPayApi.getTransferInfo(params, apiConfig.getCertPath(), apiConfig.getMchId());
        log.info("查询企业付款到零钱结果:" + result);

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