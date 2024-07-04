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

package com.ones.wx.mp.constant;

import me.chanjar.weixin.common.api.WxConsts;

/**
 * 微信服务号常量类
 * @author Clark
 * @version 2021/04/25
 */
public class OsWxMpConsts extends WxConsts {
    /**
     * 菜单级别
     */
    public static class MenuLevel {
        /**
         * 1级菜单
         */
        public static final String Level1 = "1";
        /**
         * 2级菜单
         */
        public static final String Level2 = "2";
    }

    /**
     * 菜单Key
     */
    public static class MenuKey {
        /**
         * 身份验证关键字
         */
        public static final String Auth = "auth";
        /**
         * 多客服关键字
         */
        public static final String KfService = "kf";
        /**
         * 回复关键字
         */
        public static final String Reply = "reply";
    }

    /**
     * 消息回复规则
     */
    public static class MsgReply {
        /**
         * 关注回复
         */
        public static final String REPLY_TYPE_SUBSCRIBE = "01";
        /**
         * 关键字回复
         */
        public static final String REPLY_TYPE_KEYWORD = "02";
        /**
         * 客服消息回复
         */
        public static final String REPLY_TYPE_KF = "03";
        /**
         * 关注欢迎语
         */
        public static final String KEYWORD_SUBSCRIBE = "subscribe";
        /**
         * 默认回复关键字
         */
        public static final String KEYWORD_DEFAULT = "default";
        /**
         * 多客服欢迎语
         */
        public static final String KEYWORD_KF_GREETING = "kfGreeting";
        /**
         * 多客服会话结束
         */
        public static final String KEYWORD_KF_CLOSE = "kfClose";
        /**
         * 多客服工作时间
         */
        public static final String KEYWORD_KF_WORKINGTIME = "kfWorkingTime";
    }

    /**
     * 接入多客服关键字列表
     */
    public static class TransferKf {
        public static final String[] KEYWORDS = {"你好", "客服"};
    }
}
