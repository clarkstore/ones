package com.ones.ali.nlp.util;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alinlp.model.v20200629.GetWsChGeneralRequest;
import com.aliyuncs.alinlp.model.v20200629.GetWsChGeneralResponse;
import com.aliyuncs.alinlp.model.v20200629.GetWsCustomizedChGeneralRequest;
import com.aliyuncs.alinlp.model.v20200629.GetWsCustomizedChGeneralResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.ones.ali.nlp.constant.OsNlpConsts;
import com.ones.ali.nlp.model.dto.OsNlpGeneralDataDto;
import com.ones.ali.nlp.model.dto.OsNlpReq;
import com.ones.ali.nlp.model.dto.OsNlpRes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 中文分词工具类
 * @author Clark
 * @version 2022-04-13
 */
public class OsNlpUtils {
    private IAcsClient client;

    /**
     * 初始化配置
     */
    public OsNlpUtils(String accessKeyId, String accessKeySecret) {
        DefaultProfile defaultProfile = DefaultProfile.getProfile(
                OsNlpConsts.REGION_ID,
                accessKeyId,
                accessKeySecret);
        this.client = new DefaultAcsClient(defaultProfile);
    }

    /**
     * 中文分词-基础版
     * @param req
     * @return OsNlpRes
     * @throws Exception
     */
    public OsNlpRes parse(OsNlpReq req) throws Exception {
        GetWsChGeneralRequest request = new GetWsChGeneralRequest();
//        request.setSysEndpoint(OsNlpConsts.SYS_ENDPOINT);
        request.setServiceCode(OsNlpConsts.SERVICE_CODE);
//        request.setActionName(OsNlpConsts.ACTION_GET_WS_CH_GENERAL);
        request.setTokenizerId(OsNlpConsts.TOKENIZER_ID_GENERAL_CHN);
        request.setText(req.getText());
        request.setOutType(req.getOutType());

        GetWsChGeneralResponse response = client.getAcsResponse(request);
        OsNlpGeneralDataDto dto = JSONUtil.toBean(response.getData(), OsNlpGeneralDataDto.class);
        List<String> wordList = dto.getResult().stream().map(OsNlpGeneralDataDto.ResultDto::getWord).collect(Collectors.toList());
        OsNlpRes res = OsNlpRes.builder()
                .requestId(response.getRequestId())
                .data(response.getData())
                .wordList(wordList)
                .build();
        return res;
    }

    /**
     * 中文分词-高级版（定制）
     * @param req
     * @return OsNlpRes
     * @throws Exception
     */
    public OsNlpRes parseByCustomized(OsNlpReq req) throws Exception {
        GetWsCustomizedChGeneralRequest request = new GetWsCustomizedChGeneralRequest();
//        request.setSysEndpoint(OsNlpConsts.SYS_ENDPOINT);
        request.setServiceCode(OsNlpConsts.SERVICE_CODE);
//        request.setActionName(OsNlpConsts.ACTION_GET_WS_CUSTOMIZED_CH_GENERAL);
        request.setTokenizerId(OsNlpConsts.TOKENIZER_ID_GENERAL_CHN);
        request.setText(req.getText());
        request.setOutType(req.getOutType());

        GetWsCustomizedChGeneralResponse response = client.getAcsResponse(request);
        OsNlpGeneralDataDto dto = JSONUtil.toBean(response.getData(), OsNlpGeneralDataDto.class);
        List<String> wordList = dto.getResult().stream().map(OsNlpGeneralDataDto.ResultDto::getWord).collect(Collectors.toList());
        OsNlpRes res = OsNlpRes.builder()
                .requestId(response.getRequestId())
                .data(response.getData())
                .wordList(wordList)
                .build();
        return res;
    }
}