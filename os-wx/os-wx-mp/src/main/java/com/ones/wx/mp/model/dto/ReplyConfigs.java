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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ones.common.redis.util.OsRedisUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 关键字回复
 *
 * @author Clark
 * @version 2021/05/10
 */
@Getter
@Setter
@ToString
public class ReplyConfigs {
    /**
     * 文本
     */
    private static final String REDIS_KEY_TEXT = "wxmpReplyText";
    /**
     * 图文
     */
    private static final String REDIS_KEY_NEWS = "wxmpReplyNews";
    /**
     * 使用Redis
     */
    @Autowired(required = false)
    private OsRedisUtils osRedisUtils;
    /**
     * 关键字回复配置
     */
    private List<ReplyDto> configs;
    /**
     * 文本关键字回复
     */
    private Map<String, String> replyTextMap;
    /**
     * 图文关键字回复
     */
    private Map<String, WxMpKefuMessage.WxArticle> replyNewsMap;

    /**
     * 设置关键字配置
     * @param replyTextMap
     */
    public void setReplyTextMap(Map<String, String> replyTextMap) {
        if (this.osRedisUtils == null) {
            this.replyTextMap = replyTextMap;
        } else {
            // 缓存数据
            Map<String, Object> map = MapUtil.newHashMap();
            replyTextMap.forEach((key, value) -> {
                map.put(key, value);
            });
            this.osRedisUtils.del(REDIS_KEY_TEXT);
            this.osRedisUtils.hmset(REDIS_KEY_TEXT, map);
        }
    }

    /**
     * 取得文本关键字回复Map
     * @return Map<String, String>
     */
    private Map<String, String> getReplyTextMap() {
        if (CollUtil.isEmpty(this.replyTextMap)) {
            this.replyTextMap = MapUtil.newHashMap();

            // 取配置数据
            if (CollUtil.isNotEmpty(this.configs)) {
                this.configs.forEach(item -> {
                    if (StrUtil.isBlank(item.getReplyType()) || WxConsts.KefuMsgType.TEXT.equals(item.getReplyType())) {
                        this.replyTextMap.put(item.getKeyword(), item.getReplyText());
                    }
                });
            }

            if (this.osRedisUtils != null) {
                // 取缓存数据
                Map<Object, Object> map = this.osRedisUtils.hmget(REDIS_KEY_TEXT);
                if (map != null) {
                    map.forEach((key, value) -> {
                        replyTextMap.put(String.valueOf(key), String.valueOf(value));
                    });
                }
            }
        }

        return this.replyTextMap;
    }

    /**
     * 取得关键字回复文本
     * @param keyword
     * @return 关键字回复文本
     */
    public String getReplyText(String keyword) {
        return this.getReplyTextMap().get(keyword);
    }

    /**
     * 取得图文关键字回复Map
     * @return Map<String, WxMpKefuMessage.WxArticle>
     */
    private Map<String, WxMpKefuMessage.WxArticle> getReplyNewsMap() {
        if (CollUtil.isEmpty(this.replyNewsMap)) {
            this.replyNewsMap = MapUtil.newHashMap();

            // 取配置数据
            if (CollUtil.isNotEmpty(this.configs)) {
                this.configs.forEach(item -> {
                    if (WxConsts.KefuMsgType.NEWS.equals(item.getReplyType())) {
                        this.replyNewsMap.put(item.getKeyword(), item.getArticle());
                    }
                });
            }

            if (this.osRedisUtils != null) {
                // 取缓存数据
                Map<Object, Object> map = this.osRedisUtils.hmget(REDIS_KEY_NEWS);
                if (map != null) {
                    map.forEach((key, value) -> {
                        replyNewsMap.put(String.valueOf(key), (WxMpKefuMessage.WxArticle)value);
                    });
                }
            }
        }

        return this.replyNewsMap;
    }

    /**
     * 取得关键字回复图文
     * @param keyword
     * @return 关键字回复图文
     */
    public WxMpKefuMessage.WxArticle getReplyNews(String keyword) {
        return this.getReplyNewsMap().get(keyword);
    }
}
