package com.onestop.wx.cp.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.onestop.wx.cp.CpApplication;
import com.onestop.wx.cp.util.WxCpCoreUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = CpApplication.class)
public class WxCpCoreUtilsTest {
    /*
     * serverHost
     */
    @Value("${wx.serverHost}")
    private String serverHost;
    /*
     * apiPath
     */
    @Value("${wx.apiPath}")
    private String apiPath;

    @Autowired
    private WxCpService wxCpService;

    @Autowired
    private WxCpCoreUtils coreUtils;

    @Test
    public void menuCreate() {
        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setName("服务中心");
        button1.setKey("menu1");

        WxMenuButton button3 = new WxMenuButton();
        button3.setName("在线帮助");
        button3.setKey("menu3");

        menu.getButtons().add(button1);
        menu.getButtons().add(button3);

        String returnUrl = this.serverHost + this.apiPath + "/goto/index";
        String authUrl = wxCpService.getOauth2Service().buildAuthorizationUrl(returnUrl, "cloud");
        WxMenuButton button11 = new WxMenuButton();
        button11.setType(WxCpConsts.EventType.VIEW);
        button11.setName("服务信息");
        button11.setUrl(authUrl);

        button1.getSubButtons().add(button11);

        String url31 = this.serverHost + "/home/index.html";
        WxMenuButton button31 = new WxMenuButton();
        button31.setType(WxCpConsts.EventType.VIEW);
        button31.setName("用户手册");
        button31.setUrl(url31);

        button3.getSubButtons().add(button31);

        coreUtils.menuCreate(menu);
    }

    @Test
    public void sendMsgMarkdown() {
        List<String> detailList = CollUtil.newArrayList();
        detailList.add(">**事项详情**");
        detailList.add(">事　项：<font color=\"info\">开会</font>");
        detailList.add(">组织者：@changch");
        detailList.add(">参与者：@miglioguan");
        detailList.add(">");
        detailList.add(">会议室：<font color=\"info\">广州TIT 1楼 301</font>");
        detailList.add(">日　期：<font color=\"warning\">2018年5月18日</font>");
        detailList.add(">时　间：<font color=\"comment\">上午9:00-11:00</font>");
        detailList.add(">");
        detailList.add(">请准时参加会议。");
        detailList.add(">");
        detailList.add(">如需修改会议信息，请点击：[修改会议信息](https://work.weixin.qq.com)");

        coreUtils.sendMsgMarkdown("changch", coreUtils.toMarkdown("您的会议室已经预定，稍后会同步到`邮箱`", detailList));
    }
}