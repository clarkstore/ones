package com.onestop.wx.pay.util;

import com.onestop.common.core.exception.PayException;
import com.onestop.wx.pay.WxPayApplication;
import com.onestop.wx.pay.model.dto.RedpackDto;
import com.onestop.wx.pay.model.dto.TransferDto;
import com.ijpay.core.kit.WxPayKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@Slf4j
@SpringBootTest(classes = WxPayApplication.class)
public class WxEntPayUtilsTest {

    @Autowired
    private WxEntPayUtils mmPayUtils;

    @Test
    public void sendRedPack() {
        RedpackDto dto = new RedpackDto();
        dto.setMchBillno(WxPayKit.generateStr());
        dto.setSendName("云科技");
        dto.setReOpenid("osTwywUY7QUlRvid8bzrSwOgPP-E");
        dto.setTotalAmount("200");
        dto.setTotalNum("1");
        dto.setWishing("红包祝福语");
        dto.setActName("活动名称");

        try {
            Map<String, String> map = this.mmPayUtils.sendRedpack(dto);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void getHbInfo() {
        try {
            Map<String, String> map = this.mmPayUtils.getHbInfo("5e8c219a776d31ed65a07dc3");
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void transfer() {
        TransferDto dto = new TransferDto();
        dto.setPartnerTradeNo(WxPayKit.generateStr());
        dto.setOpenid("osTwywUY7QUlRvid8bzrSwOgPP-E");
        dto.setReUserName("常春");
        dto.setAmount("200");
        dto.setDesc("IJPay test");

        try {
            Map<String, String> map = this.mmPayUtils.transfer(dto);
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }

    @Test
    public void getTransferInfo() {
        try {
            Map<String, String> map = this.mmPayUtils.getTransferInfo("868ea877b76d49dc9afca309eeca250d");
            log.warn(map.toString());
        } catch (PayException e) {
            log.warn(e.toString());
        }
    }
}
