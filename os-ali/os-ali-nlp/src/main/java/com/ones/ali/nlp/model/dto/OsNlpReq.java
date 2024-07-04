package com.ones.ali.nlp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 * 分词请求Dto
 * @author Clark
 * @version 2022-02-25
 */
@Builder
@Getter
@Setter
@ToString
public class OsNlpReq {
    @Tolerate
    public OsNlpReq() {
    }

    /**
     * 需要分析的文本，最大长度1024个字符
     */
    private String text;
    /**
     * 0：大粒度，1：中粒度（默认），2：小粒度
     */
    private String outType = "1";
}
