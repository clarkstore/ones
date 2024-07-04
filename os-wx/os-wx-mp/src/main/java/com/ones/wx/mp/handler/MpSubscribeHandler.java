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

import com.ones.wx.mp.constant.OsWxMpConsts;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 关注事件处理类
 *
 * @author Clark
 * @version 2020-08-08
 */
@Slf4j
@Component
public class MpSubscribeHandler extends MpBaseHandler {
    /**
     * 扫码关注EventKey前缀
     */
    protected final static String QRCODE_SCENE_PREFIX = "qrscene_";
    /**
     * 是否首次关注
     */
    protected boolean isFirst = false;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        // 获取微信用户基本信息
//        WxMpUser wxMpUser = super.wxMpService.getUserService()
//                .userInfo(wxMessage.getFromUser(), null);

        // 保存关注用户到数据库
//        if (wxMpUser != null) {
//            isFirst = this.wechatUtils.subscribe(wxMpUser);
//        }

        WxMpXmlOutMessage outMessage = null;
        try {
            // 处理特殊请求
            outMessage = handleSpecial(wxMessage);
            if (outMessage != null) {
                return outMessage;
            }

            // 关注信息回复
            wxMessage.setContent(OsWxMpConsts.MsgReply.KEYWORD_SUBSCRIBE);
            outMessage = super.buildReply(wxMessage);
            if (outMessage != null) {
                return outMessage;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }
}
