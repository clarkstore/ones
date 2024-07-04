package com.onestop.wx.cp.controller;

import com.onestop.wx.cp.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信验证控制器
 *
 * @author Clark
 * @version 2020-08-25
 */
@Slf4j
@RestController
@RequestMapping("${wx.serverPath}/portal")
public class CpBaseController {
    @Autowired
    private WxCpService wxService;
    @Autowired
    private WxCpMessageRouter router;

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "msg_signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应agentId的配置，请核实！"));
        }

        if (wxService.checkSignature(signature, timestamp, nonce, echostr)) {
            return new WxCpCryptUtil(wxService.getWxCpConfigStorage()).decrypt(echostr);
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("msg_signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, timestamp, nonce, requestBody);

        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxCpConfigStorage(),
                timestamp, nonce, signature);
        log.debug("\n消息解密后内容为：\n{} ", JsonUtils.toJson(inMessage));
        WxCpXmlOutMessage outMessage = this.route(inMessage);
        if (outMessage == null) {
            return "";
        }

        String out = outMessage.toEncryptedXml(wxService.getWxCpConfigStorage());
        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxCpXmlOutMessage route(WxCpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}