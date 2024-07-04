/*
 *
 *  * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.ones.wx.mp.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 * 客服信息请求Dto
 *
 * @author Clark
 * @version 2022/12/06
 */
@Builder
@Getter
@Setter
@ToString
public class KefuMsgReqDto {
    @Tolerate
    public KefuMsgReqDto() {
    }
    /**
     * openid
     */
    private String openid;
    /**
     * 消息类型：文本、图文
     */
    private String msgType;
    /**
     * 文本内容
     */
    private String content;
    /**
     * 图文消息标题
     */
    private String title;
    /**
     * 图文消息描述
     */
    private String description;
    /**
     * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     */
    private String picUrl;
    /**
     * 点击图文消息跳转链接
     */
    private String url;
}
