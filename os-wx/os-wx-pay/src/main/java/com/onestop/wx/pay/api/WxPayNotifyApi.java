package com.onestop.wx.pay.api;

import com.onestop.wx.pay.util.WxPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信支付通知控制器
 *
 * @author Clark
 * @version 2020-04-08
 */
@Slf4j
@Controller
@RequestMapping("${wx.apiPath}/notify")
public class WxPayNotifyApi {
    @Autowired
    private WxPayUtils payUtils;

    /**
     * 支付结果通知
     * 异步回调：mapping地址与PayUtils中的notifyUrl保持一致
     */
    @PostMapping(value = "/order")
    @ResponseBody
    public String payNotify(@RequestBody String xmlData) {
        log.info("支付通知=" + xmlData);
        return payUtils.payNotify(xmlData);
    }

    /**
     * 退款结果通知
     * 异步回调：mapping地址与PayUtils中的refundNotifyUrl保持一致
     */
    @PostMapping(value = "/refund")
    @ResponseBody
    public String refundNotify(@RequestBody String xmlData) {
        log.info("退款通知=" + xmlData);
        return payUtils.refundNotify(xmlData);
    }
}