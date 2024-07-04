package com.onestop.wx.pay.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 现金红包DTO
 *
 * @author Clark
 * @version 2020/04/07
 */
@Data
public class RedpackDto implements Serializable {
    /**
     * 商户订单号
     */
    private String mchBillno;
    /**
     * 商户名称
     */
    private String sendName;
    /**
     * 用户openid
     */
    private String reOpenid;
    /**
     * 付款金额
     */
    private String totalAmount;
    /**
     * 红包发放总人数
     */
    private String totalNum;
    /**
     * 红包祝福语
     */
    private String wishing;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 场景id
     */
    private String sceneId;
    /**
     * 活动信息
     */
    private String riskInfo;
}
