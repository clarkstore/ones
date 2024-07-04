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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 回复信息
 *
 * @author Clark
 * @version 2021/04/25
 */
@Getter
@Setter
@ToString
public class ReplyDto {

    private String id;
    /**
     * 规则名
     */
    private String name;
    /**
     * 回复类型 01：关注、02：关键字回复、03：客服消息
     */
    private String replyType;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 消息类型：文本、图片、图文、视频、音频
     */
    private String msgType;
    /**
     * 文本回复内容
     */
    private String replyText;
    /**
     * 图文回复内容
     */
    private WxMpKefuMessage.WxArticle article;
    /**
     * 删除标识
     */
    private String deleted;
}
