package com.onestop.wx.pay.util;

import com.onestop.common.core.exception.PayException;
import com.onestop.wx.pay.WxPayApplication;
import com.onestop.wx.pay.model.dto.OrderQueryDto;
import com.onestop.wx.pay.model.dto.RefundDto;
import com.onestop.wx.pay.model.dto.UnifiedOrderDto;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.QrCodeKit;
import com.ijpay.core.kit.WxPayKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest(classes = WxPayApplication.class)
public class WxPayUtilsTest {

    @Autowired
    private WxPayUtils payUtils;

    @Test
    public void unifiedOrder() {
        UnifiedOrderDto dto = new UnifiedOrderDto();
        dto.setBody("公众号支付-测试");
        dto.setOutTradeNo(WxPayKit.generateStr());
        dto.setTotalFee("10");
        dto.setTradeType(TradeType.JSAPI.getTradeType());
        dto.setOpenid("osTwywUY7QUlRvid8bzrSwOgPP-E");
        log.warn("OutTradeNo --> " + dto.getOutTradeNo());
        try {
            Map<String, String> map = this.payUtils.unifiedOrder(dto);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void scanCode2() {
        UnifiedOrderDto dto = new UnifiedOrderDto();
        dto.setBody("扫码支付模式二-测试");
        dto.setOutTradeNo(WxPayKit.generateStr());
        dto.setTotalFee("2000");
        dto.setTradeType(TradeType.NATIVE.getTradeType());
        // 分账
//        dto.setProfitSharing("Y");

        Map<String, String> payResult = payUtils.unifiedOrder(dto);
        log.info("统一下单:" + payResult);

        String qrCodeUrl = payResult.get("code_url");
        String name = "e:/payQRCode2.png";

        Boolean encode = QrCodeKit.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200, name);
        if (encode) {
            // 二维码支付页面
            log.info("支付二维码已生成");
        }
    }

    @Test
    public void orderQuery() {
        // transactionId / outTradeNo 二选一
        OrderQueryDto dto = new OrderQueryDto();
//        dto.setTransactionId("wx0814440479492649ece6468a1924400600");
        dto.setOutTradeNo("bae8be4fde66437b9bc6a9d5fe5362af");
        try {
            Map<String, String> map = this.payUtils.orderQuery(dto);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void refund() {
        RefundDto dto = new RefundDto();
        dto.setOutTradeNo("be44eafcb4874653ad4a0999b123c82f");
        dto.setOutRefundNo(WxPayKit.generateStr());
        dto.setTotalFee("100");
        dto.setRefundFee("100");
        dto.setRefundDesc("IJap测试");

        try {
            Map<String, String> map = payUtils.refund(dto);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void refundQuery() {
        try {
            Map<String, String> map = payUtils.refundQuery("bae8be4fde66437b9bc6a9d5fe5362af", null);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void profitSharing() {
        try {
            Map<String, String> map = payUtils.profitSharing();
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }
}
