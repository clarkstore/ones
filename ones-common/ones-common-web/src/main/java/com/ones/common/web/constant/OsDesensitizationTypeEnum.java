package com.ones.common.web.constant;

/**
 * 支持的脱敏类型枚举
 * 覆盖hutool中DesensitizedUtil.DesensitizedType所有类型
 *
 * @author Clark
 * @version 2023-02-27
 */
public enum OsDesensitizationTypeEnum {
    /** 自定义（此项需设置脱敏的范围）*/
    CUSTOMER,
    /**
     * 用户id
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * IPv4地址
     */
    IPV4,
    /**
     * IPv6地址
     */
    IPV6,
}
