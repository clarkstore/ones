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
public class UnifiedOrderDto implements Serializable {
    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：013467007045764
     * 描述：终端设备号(门店号或收银设备Id)，注意：PC网页或公众号内支付请传"WEB"
     */
    private String deviceInfo;
    /**
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值： 腾讯充值中心-QQ会员充值
     * 描述：商品简单描述，该字段须严格按照规范传递，具体请见参数规定
     */
    private String body;
    /**
     * 字段名：商品详情.
     * 变量名：detail
     * 是否必填：否
     * 类型：String(6000)
     * 示例值： {  "goods_detail":[
     * {
     * "goods_id":"iphone6s_16G",
     * "wxpay_goods_id":"1001",
     * "goods_name":"iPhone6s 16G",
     * "goods_num":1,
     * "price":528800,
     * "goods_category":"123456",
     * "body":"苹果手机"
     * },
     * {
     * "goods_id":"iphone6s_32G",
     * "wxpay_goods_id":"1002",
     * "goods_name":"iPhone6s 32G",
     * "quantity":1,
     * "price":608800,
     * "goods_category":"123789",
     * "body":"苹果手机"
     * }
     * ]
     * }
     * 描述：商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
     * goods_detail []：
     * └ goods_id String 必填 32 商品的编号
     * └ wxpay_goods_id String 可选 32 微信支付定义的统一商品编号
     * └ goods_name String 必填 256 商品名称
     * └ goods_num Int 必填 商品数量
     * └ price Int 必填 商品单价，单位为分
     * └ goods_category String 可选 32 商品类目Id
     * └ body String 可选 1000 商品描述信息
     */
    private String detail;
    /**
     * 字段名：附加数据.
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值： 深圳分店
     * 描述：  附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;
    /**
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：20150806125346
     * 描述：商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     */
    private String outTradeNo;
    /**
     * 字段名：货币类型.
     * 变量名：fee_type
     * 是否必填：否
     * 类型：String(16)
     * 示例值：CNY
     * 描述： 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String feeType;
    /**
     * 字段名：总金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值： 888
     * 描述：订单总金额，单位为分，详见支付金额
     */
    private String totalFee;
    /**
     * 字段名：交易起始时间.
     * 变量名：time_start
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20091225091010
     * 描述：订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    private String timeStart;
    /**
     * 字段名：交易结束时间.
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20091227091010
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
     * 注意：最短失效时间间隔必须大于5分钟
     */
    private String timeExpire;
    /**
     * 字段名：商品标记.
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：WXG
     * 描述：商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     */
    private String goodsTag;
    /**
     * 字段名：交易类型.
     * 变量名：trade_type
     * 是否必填：是
     * 类型：String(16)
     * 示例值： JSAPI
     * 描述： 取值如下：JSAPI，NATIVE，APP，详细说明见参数规定:
     * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
     */
    private String tradeType;
    /**
     * 字段名：商品Id.
     * 变量名：product_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：12235413214070356458058
     * 描述：trade_type=NATIVE，此参数必传。此id为二维码中包含的商品Id，商户自行定义。
     */
    private String productId;
    /**
     * 字段名：指定支付方式.
     * 变量名：limit_pay
     * 是否必填：否
     * 类型：String(32)
     * 示例值：no_credit
     * 描述：no_credit--指定不能使用信用卡支付
     */
    private String limitPay;
    /**
     * 字段名：用户标识.
     * 变量名：openid
     * 是否必填：否
     * 类型：String(128)
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 描述：trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
     * openid如何获取，可参考【获取openid】。
     * 企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     */
    private String openid;
    /**
     * 字段名：电子发票入口开放标识.
     * 变量名：	receipt
     * 是否必填：否
     * 类型：String(8)
     * 示例值：Y
     * 描述：Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     */
    private String receipt;
    /**
     * 字段名：场景信息.
     * 变量名：scene_info
     * 是否必填：否，对H5支付来说是必填
     * 类型：String(256)
     * 示例值：{
     * "store_id": "SZT10000",
     * "store_name":"腾讯大厦腾大餐厅"
     * }
     * 描述：该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
     * {
     * "store_id": "", //门店唯一标识，选填，String(32)
     * "store_name":"”//门店名称，选填，String(64)
     * }
     */
    private String sceneInfo;

    /**
     * 字段名：是否需要分账
     * 变量名：profit_sharing
     * 描述：Y-是，需要分账 N-否，不分账 字母要求大写，不传默认不分账
     * 在统一下单API、付款码支付API、委托扣款API中上传新增参数profit_sharing，请求支付
     */
    private String profitSharing;
}