package com.ones.ali.nlp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * 分词应答Dto
 * @author Clark
 * @version 2022-02-25
 */
@Builder
@Getter
@Setter
@ToString
public class OsNlpRes {
    @Tolerate
    public OsNlpRes() {
    }

    /**
     * 唯一请求id，排查问题的依据
     */
    private String requestId;
    /**
     * id: 词序号word: 词tags: 语义标签
     */
    private String data;
    /**
     * 分词列表
     */
    private List<String> wordList;
}
