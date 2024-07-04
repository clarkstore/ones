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

package com.ones.wx.mp.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ones.common.core.exception.OsBizException;
import com.ones.wx.mp.model.dto.*;
import com.ones.wx.mp.constant.OsWxMpConsts;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.subscribe.WxMpSubscribeMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 微信服务号工具类
 *
 * @author Clark
 * @version 2020-09-03
 */
@Slf4j
@Component
public class OsWxMpUtils {
    @Getter
    @Autowired
    private WxMpService wxMpService;
    /**
     * 关键字回复配置类
     */
    @Autowired(required = false)
    private ReplyConfigs replyConfigs;
    /**
     * 订阅消息配置
     */
    @Autowired(required = false)
    private SubscribeConfigs subscribeConfigs;

    /**
     * 获取openid
     * @param code
     * @return
     * @throws WxErrorException the wx error exception
     */
    public String getOpenid(String code) throws WxErrorException {
        String openid = this.wxMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        return openid;
    }

    /**
     * 发送模板消息
     *
     * @param openid
     * @param templateId
     * @param url
     * @param data
     * @throws WxErrorException the wx error exception
     */
    public void sendTemplateMsg(String openid, String templateId, String url, Map<String, String> data) throws WxErrorException {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(openid).templateId(templateId).build();
        // 详情链接
        if (StrUtil.isNotBlank(url)) {
            templateMessage.setUrl(url);
        }
        // 填充参数
        data.forEach((k, v) -> templateMessage.addData(new WxMpTemplateData(k, v)));

        this.wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }

    /**
     * 发送订阅消息
     *
     * @param openid
     * @param templateId
     * @param url
     * @param dataMap
     * @throws WxErrorException the wx error exception
     */
    @Deprecated
    public void sendSubscribeMsg(String openid, String templateId, String url, Map<String, String> dataMap) throws WxErrorException {
        WxMpSubscribeMessage subscribeMessage = WxMpSubscribeMessage.builder().toUser(openid).templateId(templateId).dataMap(dataMap).build();
        // 详情链接
        if (StrUtil.isNotBlank(url)) {
            subscribeMessage.setUrl(url);
        }

        this.wxMpService.getSubscribeMsgService().send(subscribeMessage);
    }

    /**
     * 发送订阅消息
     *
     * @param dto 订阅消息请求类
     * @throws WxErrorException the wx error exception
     */
    public void sendSubscribeMsg(SubscribeReqDto dto) throws WxErrorException {
        log.debug("---------------sendSubscribeMsg----------------");
        log.debug("dto : " + dto.toString());
        //获取订阅消息配置
        SubscribeDto subscribe = this.subscribeConfigs.getConfig(dto.getMsgId());
        WxMpSubscribeMessage subscribeMessage = WxMpSubscribeMessage.builder()
                .toUser(dto.getOpenid())
                .templateId(subscribe.getTplId())
                .page(subscribe.getPage())
                .build();

        //封装参数
        Map<String, String> dataMap = MapUtil.newHashMap();
        for (int i = 0; i < subscribe.getNameList().size(); i++) {
            dataMap.put(subscribe.getNameList().get(i), dto.getValueList().get(i));
        }
        subscribeMessage.setDataMap(dataMap);

        this.wxMpService.getSubscribeMsgService().send(subscribeMessage);
    }

    /**
     * 发送客服消息
     * @param dto 客服消息请求类
     * @throws WxErrorException the wx error exception
     */
    public void sentKefuMessage(KefuMsgReqDto dto) throws WxErrorException {
        if (StrUtil.isBlank(dto.getOpenid()) || StrUtil.isBlank(dto.getMsgType())) {
            return;
        }
        switch (dto.getMsgType()) {
            case WxConsts.KefuMsgType.TEXT:
                // 发送文本客服消息
                WxMpKefuMessage textMsg = WxMpKefuMessage.TEXT()
                        .toUser(dto.getOpenid())
                        .content(dto.getContent())
                        .build();
                this.wxMpService.getKefuService().sendKefuMessage(textMsg);
                break;
            case WxConsts.KefuMsgType.NEWS:
                // 发送图文客服消息
                WxMpKefuMessage.WxArticle wxArticle = new WxMpKefuMessage.WxArticle();
                wxArticle.setTitle(dto.getTitle());
                wxArticle.setDescription(dto.getDescription());
                wxArticle.setPicUrl(dto.getPicUrl());
                wxArticle.setUrl(dto.getUrl());
                // 发送图文客服消息
                WxMpKefuMessage newsMsg = WxMpKefuMessage.NEWS()
                        .toUser(dto.getOpenid())
                        .addArticle(wxArticle)
                        .build();
                this.wxMpService.getKefuService().sendKefuMessage(newsMsg);
                break;

        }
    }

    /**
     * 关键字回复
     * 文本与图文消息
     *
     * @param keyword
     * @throws WxErrorException the wx error exception
     */
    public void keywordReply(String openid, String keyword) throws WxErrorException {
        if (this.replyConfigs == null) {
            throw new OsBizException("配置项：os.wxmp.reply.enabled 未设置");
        }
        if (StrUtil.isBlank(openid) || StrUtil.isBlank(keyword)) {
            return;
        }
        // 文本消息
        String replyText = this.replyConfigs.getReplyText(keyword);
        if (StrUtil.isNotBlank(replyText)) {
            // 发送文本客服消息
            WxMpKefuMessage message = WxMpKefuMessage.TEXT()
                    .toUser(openid)
                    .content(replyText)
                    .build();
            this.wxMpService.getKefuService().sendKefuMessage(message);
            return;
        }
        // 图文消息
        WxMpKefuMessage.WxArticle wxArticle = this.replyConfigs.getReplyNews(keyword);
        if (wxArticle != null) {
            // 发送图文客服消息
            WxMpKefuMessage message = WxMpKefuMessage.NEWS()
                    .toUser(openid)
                    .addArticle(wxArticle)
                    .build();
            this.wxMpService.getKefuService().sendKefuMessage(message);
        }
    }

    /**
     * 创建菜单
     *
     * @param configs
     * @throws WxErrorException the wx error exception
     */
    public void menuCreate(MenuConfigs configs) throws WxErrorException {
        WxMenu wxMenu = this.getMenu(configs);
        this.wxMpService.getMenuService().menuCreate(wxMenu);
    }

    /**
     * 默认从配置文件中取得
     * 支持小程序跳转菜单
     *
     * @param configs
     * @return me.chanjar.weixin.common.bean.menu.WxMenu
     */
    private WxMenu getMenu(MenuConfigs configs) {
        WxMenu wxMenu = new WxMenu();

        List<MenuDto> menuList = configs.getConfigs();

        for (MenuDto m : menuList) {
            if (OsWxMpConsts.MenuLevel.Level1.equals(m.getMenuLevel())) {
                WxMenuButton b1 = new WxMenuButton();
                b1.setType(m.getMenuType());
                b1.setName(m.getMenuName());
                b1.setKey(m.getMenuKey());
                b1.setUrl(m.getUrl());
                b1.setMediaId(m.getUrl());
                b1.setAppId(m.getAppid());
                b1.setPagePath(m.getPagepath());
                wxMenu.getButtons().add(b1);
            } else {
                WxMenuButton b2 = new WxMenuButton();
                b2.setType(m.getMenuType());
                b2.setName(m.getMenuName());
                b2.setKey(m.getMenuKey());
                b2.setUrl(m.getUrl());
                b2.setMediaId(m.getUrl());
                b2.setAppId(m.getAppid());
                b2.setPagePath(m.getPagepath());
                wxMenu.getButtons().get(wxMenu.getButtons().size() - 1).getSubButtons().add(b2);
            }

        }
        return wxMenu;
    }

    /**
     * 根据参数创建临时二维码,返回二维码链接
     *
     * @param sceneStr
     * @return String
     */
    public String getQrcodeTmpImgUrl(String sceneStr, Integer expireSeconds) throws WxErrorException {
        WxMpQrCodeTicket ticket = this.wxMpService.getQrcodeService().qrCodeCreateTmpTicket(sceneStr, expireSeconds);
        String url = this.wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
        return url;
    }

    /**
     * 根据参数创建永久二维码,返回二维码链接
     *
     * @param sceneStr
     * @return String
     */
    public String getQrcodeImgUrl(String sceneStr) throws WxErrorException {
        WxMpQrCodeTicket ticket = this.wxMpService.getQrcodeService().qrCodeCreateLastTicket(sceneStr);
        String url = this.wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
        return url;
    }

    /**
     * 网站应用授权登录的url
     *
     * @param redirectUri 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param state       非必填，用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     * @return url string
     */
    public String buildQrConnectUrl(String redirectUri, String state) {
        return this.wxMpService.buildQrConnectUrl(redirectUri, WxConsts.QrConnectScope.SNSAPI_LOGIN, state);
    }

    /**
     * 构造oauth2授权的url连接.
     *
     * @param redirectUri 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scope       scope,静默:snsapi_base, 带信息授权:snsapi_userinfo
     * @param state       state
     * @return url
     */
    public String buildAuthorizationUrl(String redirectUri, String scope, String state) {
        return this.wxMpService.getOAuth2Service().buildAuthorizationUrl(redirectUri, scope, state);
    }
}