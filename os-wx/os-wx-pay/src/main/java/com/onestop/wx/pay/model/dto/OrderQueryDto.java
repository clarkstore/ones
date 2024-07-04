package com.onestop.wx.pay.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一下单DTO
 *
 * @author Clark
 * @version 2020/04/07
 */
@Data
public class OrderQueryDto implements Serializable {
    /**
     * 字段名：微信订单号  二选一
     * 变量名：transaction_id
     * 是否必填：否
     * 类型：String(32)
     * 描述：微信的订单号，建议优先使用
     */
    private String transactionId;
    /**
     * 字段名：商户订单号  二选一
     * 变量名：out_trade_no
     * 是否必填：否
     * 类型：String(32)
     * 描述：商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     */
    private String outTradeNo;
}