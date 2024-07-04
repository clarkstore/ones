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

/**
 * 菜单信息
 *
 * @author Clark
 * @version 2021/04/23
 */
@Getter
@Setter
@ToString
public class MenuDto {

    private String id;
    /**
     * 父级菜单ID
     */
    private String parentId;
    /**
     * 菜单组
     */
    private String menuGroup;
    /**
     * 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型
     */
    private String menuType;
    /**
     * 菜单标题
     */
    private String menuName;
    /**
     * 菜单key
     */
    private String menuKey;
    /**
     * url: view、miniprogram类型必须
     */
    private String url;
    /**
     * 小程序的appid
     */
    private String appid;
    /**
     * 小程序的页面路径
     */
    private String pagepath;
    /**
     * 菜单级别：1：一级、2：二级
     */
    private String menuLevel;
    /**
     * 排序
     */
    private String sort;
    /**
     * 删除标识
     */
    private String deleted;
}
