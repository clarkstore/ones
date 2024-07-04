package com.onestop.wx.cp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.onestop.wx.cp.config.WxCpProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpOauth2UserInfo;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 企业微信服务端API工具类
 * <a>https://work.weixin.qq.com/api/doc/90000/90135/90664</a>
 *
 * @author Clark
 * @version 2020-08-21
 */
@Slf4j
@Component
public class WxCpCoreUtils {
    private static final String LINE_BREAK_CHAR = "\n";

    @Autowired
    private WxCpService wxCpService;
    @Autowired
    private WxCpProperties properties;

    /**
     * 获取userid
     *
     * @param code
     * @return
     */
    public String getUserid(String code) {
        try {
            WxCpOauth2UserInfo userInfo = this.wxCpService.getOauth2Service().getUserInfo(code);
            return userInfo.getUserId();
        } catch (WxErrorException e) {
            log.error("==========获取userid异常==========");
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 文本消息
     * <a>https://work.weixin.qq.com/api/doc/90000/90135/90236#文本消息</a>
     *
     * @param toUser
     * @param content
     */
    @SneakyThrows
    public void sendMsgText(Integer agentId, String toUser, String content) {
        WxCpMessage msg = WxCpMessage.TEXT().agentId(agentId)
                .toUser(toUser).content(content).build();
        this.wxCpService.getMessageService().send(msg);
    }

    /**
     * 文本卡片消息
     * <a>https://work.weixin.qq.com/api/doc/90000/90135/90236#文本卡片消息</a>
     * @param toUser
     * @param title
     * @param description
     * @param url
     */
//    @SneakyThrows
//    public void sendMsgTextCard(String toUser, String title, String description, String url) {
//        WxCpMessage msg = WxCpMessage.TEXTCARD().agentId(this.agentId)
//                .toUser(toUser).title(title).description(description).url(url).build();
//        wxCpService.messageSend(msg);
//    }

    /**
     * markdown消息
     * <a>https://work.weixin.qq.com/api/doc/90000/90135/90236#markdown消息</a>
     *
     * @param toUser
     * @param content
     */
    @SneakyThrows
    public void sendMsgMarkdown(String toUser, String content) {
        WxCpMessage msg = WxCpMessage.MARKDOWN().agentId(this.properties.getAgentId())
                .toUser(toUser).content(content).build();
        wxCpService.getMessageService().send(msg);
    }

    /**
     * 转换Markdown格式
     * <p>apiUtils.sendMsgMarkdown(toUser, apiUtils.toMarkdown(title, detailList));</p>
     *
     * @param title
     * @param detailList
     * @return
     */
    public String toMarkdown(String title, List<String> detailList) {
        StringBuilder sb = new StringBuilder();
        if (StrUtil.isNotBlank(title)) {
            sb.append(title).append(LINE_BREAK_CHAR);
        }
        if (CollUtil.isNotEmpty(detailList)) {
            sb.append(String.join(LINE_BREAK_CHAR, detailList));
        }
        return sb.toString();
    }

    /**
     * 创建菜单
     * @param wxMenu
     * @return
     */
    public String menuCreate(WxMenu wxMenu) {
        try {
            wxCpService.getMenuService().create(wxMenu);
        } catch (Exception e) {
            log.error("菜单创建失败");
            log.error(e.toString());
            e.printStackTrace();
        }
        return "Menu Create Succeed";
    }
}
