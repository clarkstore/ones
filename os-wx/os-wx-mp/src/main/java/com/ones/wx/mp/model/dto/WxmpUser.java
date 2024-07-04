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

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Clark
 * @version 2020/08/06
 */
@Getter
@Setter
@ToString
public class WxmpUser {

    private String id;
    /**
     * openid
     */
    private String openid;
    /**
     * 是否关注
     */
    private String subscribe;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 关注时间
     */
    private LocalDateTime subscribeTime;
    /**
     * 取消关注时间
     */
    private LocalDateTime unsubscribeTime;
}
