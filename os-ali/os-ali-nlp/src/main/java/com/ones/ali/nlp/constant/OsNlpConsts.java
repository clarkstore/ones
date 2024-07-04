package com.ones.ali.nlp.constant;

/**
 * 消息常量类
 *
 * @author Clark
 * @version 2021-11-10
 */
public interface OsNlpConsts {
    /**
     * RegionId
     */
    String REGION_ID = "cn-hangzhou";
    /**
     * SysEndpoint
     */
    String SYS_ENDPOINT = "alinlp.cn-hangzhou.aliyuncs.com";
    /**
     * ServiceCode
     */
    String SERVICE_CODE = "alinlp";
    /**
     * 中文分词基础版Action
     */
    String ACTION_GET_WS_CH_GENERAL = "GetWsChGeneral";
    /**
     * 中文分词高级版Action
     */
    String ACTION_GET_WS_CUSTOMIZED_CH_GENERAL = "GetWsCustomizedChGeneral";
    /**
     * 分词器类型，默认为GENERAL_CHN
     */
    String TOKENIZER_ID_GENERAL_CHN = "GENERAL_CHN";
}
