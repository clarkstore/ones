package com.onestop.wx.pay.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 申请退款DTO
 *
 * @author Clark
 * @version 2020/04/07
 */
@Data
public class RefundDto implements Serializable {
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
    /**
     * 字段名：商户退款单号
     * 变量名：out_refund_no
     * 是否必填：是
     * 类型：String(64)
     * 描述：商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔
     */
    private String outRefundNo;
    /**
     * 字段名：订单金额
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 描述：订单总金额，单位为分，详见支付金额
     */
    private String totalFee;
    /**
     * 字段名：退款金额
     * 变量名：refund_fee
     * 是否必填：是
     * 类型：Int
     * 描述：退款总金额，订单总金额，单位为分，只能为整数
     */
    private String refundFee;
    /**
     * 字段名：退款原因
     * 变量名：refund_desc
     * 是否必填：否
     * 类型：String(80)
     * 描述：若商户传入，会在下发给用户的退款消息中体现退款原因
     */
    private String refundDesc;

}
