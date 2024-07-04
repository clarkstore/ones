package com.onestop.wx.pay.api;

import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.WxPayKit;
import com.onestop.common.core.util.Res;
import com.onestop.wx.pay.model.dto.UnifiedOrderDto;
import com.onestop.wx.pay.util.WxPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信支付api
 *
 * @author Clark
 * @version 2020-04-08
 */
@Slf4j
@RestController
@RequestMapping("${wx.apiPath}/pay")
public class WxPayApi {
    @Autowired
    private WxPayUtils payUtils;

    /**
     * 统一下单接口
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param dto 请求对象，注意一些参数如appid、mchid等不用设置
     */
    @PostMapping("/createOrder")
    public Res createOrder(@RequestBody UnifiedOrderDto dto) {
        dto.setOutTradeNo(WxPayKit.generateStr());
        dto.setTradeType(TradeType.JSAPI.getTradeType());

        Map<String, String> payResult = payUtils.unifiedOrder(dto);

        return Res.ok(payResult);
    }
}