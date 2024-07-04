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

import java.util.List;

/**
 * 订阅消息类
 * @author Clark
 * @version 2022-11-28
 */
@Builder
@Getter
@Setter
@ToString
public class SubscribeDto {
    @Tolerate
    public SubscribeDto() {
    }
    /**
     * 订阅编号:业务调用自定义常量
     */
    private String msgId;
    /**
     * 订阅消息模板ID
     */
    private String tplId;
    /**
     * 订阅消息跳转页面
     */
    private String page;
    /**
     * 订阅消息参数名列表
     */
    private List<String> nameList;
}
