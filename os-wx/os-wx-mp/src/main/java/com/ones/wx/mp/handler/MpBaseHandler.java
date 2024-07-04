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

package com.ones.wx.mp.handler;

import cn.hutool.core.util.StrUtil;
import com.ones.wx.mp.model.dto.ReplyConfigs;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 消息处理基类
 *
 * @author Clark
 * @version 2021-04-29
 */
public abstract class MpBaseHandler implements WxMpMessageHandler {
    @Autowired
    public WxMpService wxMpService;
    /**
     * 关键字回复配置
     */
    @Autowired(required = false)
    public ReplyConfigs replyConfigs;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        return null;
    }

    /**
     * 构建关键字回复信息
     *
     * @param wxMessage
     * @return WxMpXmlOutMessage
     */
    public WxMpXmlOutMessage buildReply(WxMpXmlMessage wxMessage) {
        if (this.replyConfigs != null) {
            String replyText = this.replyConfigs.getReplyText(wxMessage.getContent());

            if (StrUtil.isNotBlank(replyText)) {
                return WxMpXmlOutMessage.TEXT().content(replyText)
                        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                        .build();
            }

            WxMpKefuMessage.WxArticle wxArticle = this.replyConfigs.getReplyNews(wxMessage.getContent());
            // 图文消息
            if (wxArticle != null) {
                WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                item.setTitle(wxArticle.getTitle());
                item.setDescription(wxArticle.getDescription());
                item.setUrl(wxArticle.getUrl());
                item.setPicUrl(wxArticle.getPicUrl());
                return WxMpXmlOutNewsMessage.NEWS()
                        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                        .addArticle(item)
                        .build();
            }
        }
        return null;
    }

    /**
     * 根据参数创建永久二维码,返回二维码链接
     *
     * @param sceneStr
     * @return
     */
//    public String getQrcodeImgUrl(String sceneStr) {
//        try {
//            WxMpQrCodeTicket ticket = this.wxMpService.getQrcodeService().qrCodeCreateLastTicket(sceneStr);
//            String url = this.wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket(), Boolean.TRUE);
//            return url;
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
