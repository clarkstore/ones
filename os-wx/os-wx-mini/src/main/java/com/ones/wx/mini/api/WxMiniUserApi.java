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

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaRunStepInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import com.ones.common.core.util.Res;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 微信小程序用户接口
 * 如需扩展：业务模块中自行实现
 *
 * @author Clark
 * @version 2021-03-18
 */
@Slf4j
@RestController
@RequestMapping("${os.wxmini.apiPath}/user")
public class WxMiniUserApi {
    @Autowired
    private WxMaService wxService;

    /**
     * 登陆接口:auth.code2Session
     *
     * @param code code
     * @return Res
     */
    @GetMapping("/login")
    public Res login(String code) {
        if (StrUtil.isBlank(code)) {
            return Res.failed("empty jscode");
        }

        try {
            WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
            return Res.ok(session);
        } catch (WxErrorException e) {
            log.error("==========code2Session异常==========");
            log.error(e.getMessage());
        }
        return Res.failed();
    }

    /**
     * 获取用户信息接口
     *
     * @param sessionKey sessionKey
     * @param signature signature
     * @param rawData rawData
     * @param encryptedData 加密数据
     * @param iv iv
     * @return Res
     */
    @GetMapping("/info")
    public Res info(String sessionKey,
                    String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.checkUserInfo(sessionKey, rawData, signature)) {
            return Res.failed("user check failed");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return Res.ok(userInfo);
    }

    /**
     * 获取用户绑定手机号信息
     *
     * @param sessionKey sessionKey
     * @param signature signature
     * @param rawData rawData
     * @param encryptedData 加密数据
     * @param iv iv
     * @return Res
     */
    @GetMapping("/phone")
    public Res phone(String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.checkUserInfo(sessionKey, rawData, signature)) {
            return Res.failed("user check failed");
        }

        // 获取用户绑定手机号信息
        WxMaPhoneNumberInfo phoneNoInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return Res.ok(phoneNoInfo);
    }

    /**
     * 获取用户运动信息
     * @param sessionKey sessionKey
     * @param encryptedData 加密数据
     * @param iv iv
     * @return Res
     */
    @GetMapping("/runData")
    public Res runData(String sessionKey, String encryptedData, String iv) {
        List<WxMaRunStepInfo> list = this.wxService.getRunService().getRunStepInfo(sessionKey, encryptedData, iv);

        return Res.ok(list);
    }

    /**
     * 用户信息校验
     *
     * @param sessionKey sessionKey
     * @param signature  signature
     * @param rawData    rawData
     * @return boolean
     */
    private boolean checkUserInfo(String sessionKey,
                                 String signature, String rawData) {
        return this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature);
    }
}
