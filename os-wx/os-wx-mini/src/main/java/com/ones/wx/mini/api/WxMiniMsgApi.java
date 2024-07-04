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

package com.ones.wx.mini.api;

import com.ones.common.core.util.Res;
import com.ones.wx.mini.model.dto.SubscribeReqDto;
import com.ones.wx.mini.util.OsWxMiniUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序消息接口
 *
 * @author Clark
 * @version 2020-08-23
 */
@Slf4j
@RestController
@RequestMapping("${os.wxmini.apiPath}/msg")
public class WxMiniMsgApi {
    @Autowired
    private OsWxMiniUtils osWxMiniUtils;

    /**
     * 发送订阅消息
     * @param dto
     * @return
     */
    @PostMapping("/send")
    public Res send(@RequestBody SubscribeReqDto dto) {
        try {
            this.osWxMiniUtils.sendSubscribeMsg(dto);
            return Res.ok("发送订阅消息成功");
        } catch (WxErrorException e) {
            return Res.failed(e.getMessage());
        }
    }

//    /**
//     * 客服消息
//     */
//    @GetMapping("/kefu")
//    public Res kefu() {
//        final WxMaMsgService service = this.wxService.getMsgService();
//
//        WxMaKefuMessage kefuMessage = new WxMaKefuMessage();
//        kefuMessage.setToUser("ohsEJ0f4BYyFXdK9sXUr28zXNr08");
//        kefuMessage.setMsgType(WxMaConstants.KefuMsgType.TEXT);
//        kefuMessage.setText(new WxMaKefuMessage.KfText("test"));
//
//        try {
//            service.sendKefuMsg(kefuMessage);
//            //TODO 可以增加自己的逻辑，关联业务相关数据
//            return Res.ok();
//        } catch (WxErrorException e) {
//            log.error(e.getMessage(), e);
//            return Res.failed(e.getError().getErrorMsg());
//        }
//    }
}
