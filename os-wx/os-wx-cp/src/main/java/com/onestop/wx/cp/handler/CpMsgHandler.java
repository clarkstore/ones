package com.onestop.wx.cp.handler;

import com.onestop.wx.cp.util.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.outxmlbuilder.TextBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 默认消息处理类
 *
 * @author Clark
 * @version 2019-12-09
 */
@Component
public class CpMsgHandler extends CpBaseHandler {
    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService cpService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //TODO 组装回复消息
        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);

        return new TextBuilder().content(content).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
    }
}
