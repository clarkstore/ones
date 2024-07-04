package com.ones.ali.nlp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * 分词请求Dto
 * @author Clark
 * @version 2022-02-25
 */
@Builder
@Getter
@Setter
@ToString
public class OsNlpGeneralDataDto {
    @Tolerate
    public OsNlpGeneralDataDto() {
    }

    /**
     * 需要分析的文本，最大长度1024个字符
     */
    private List<ResultDto> result;
    /**
     * 0：大粒度，1：中粒度（默认），2：小粒度
     */
    private boolean success;

    @Getter
    @Setter
    public class ResultDto {
        /**
         * 需要分析的文本，最大长度1024个字符
         */
        private String id;
        /**
         * 需要分析的文本，最大长度1024个字符
         */
        private String word;
        /**
         * 需要分析的文本，最大长度1024个字符
         */
        private List<String> tags;
    }
}
