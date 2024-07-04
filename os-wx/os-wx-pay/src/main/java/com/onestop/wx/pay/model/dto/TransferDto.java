package com.onestop.wx.pay.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 企业付款到零钱DTO
 *
 * @author Clark
 * @version 2020/04/07
 */
@Data
public class TransferDto implements Serializable {
    /**
     * 不校验真实姓名
     */
    public static final String CHECK_NAME_NO = "NO_CHECK";
    /**
     * 强校验真实姓名
     */
    public static final String CHECK_NAME_FORCE = "FORCE_CHECK";

    /**
     * 商户订单号
     */
    private String partnerTradeNo;
    /**
     * openid
     */
    private String openid;
    /**
     * 校验用户姓名选项
     */
    private String checkName = CHECK_NAME_NO;
    /**
     * 收款用户姓名
     */
    private String reUserName;
    /**
     * 企业付款金额，单位为分
     */
    private String amount;
    /**
     * 企业付款备注
     */
    private String desc;
}
