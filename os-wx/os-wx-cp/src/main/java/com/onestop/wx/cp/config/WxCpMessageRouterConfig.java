package com.onestop.wx.cp.config;

import com.onestop.wx.cp.handler.CpMenuHandler;
import com.onestop.wx.cp.handler.CpMsgHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息路由配置
 * @author Clark
 * @version 2020-08-25
 */
@AllArgsConstructor
@Configuration
public class WxCpMessageRouterConfig {
    private final CpMenuHandler menuHandler;
    private final CpMsgHandler msgHandler;

    private WxCpService wxCpService;

    @Bean
    public WxCpMessageRouter newRouter() {
        final WxCpMessageRouter newRouter = new WxCpMessageRouter(wxCpService);

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(this.menuHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
