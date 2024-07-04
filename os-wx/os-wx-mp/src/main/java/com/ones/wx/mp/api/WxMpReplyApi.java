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

package com.ones.wx.mp.api;

import com.ones.common.core.exception.OsBizException;
import com.ones.common.core.util.Res;
import com.ones.wx.mp.model.dto.ReplyConfigs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信服务号关键字回复接口
 *
 * @author Clark
 * @version 2021-04-29
 */
@Slf4j
@RestController
@RequestMapping("${os.wxmp.apiPath}/reply")
public class WxMpReplyApi {
    @Autowired(required = false)
    private ReplyConfigs replyConfigs;

    @PostMapping("/update")
    public Res update(@RequestBody Map<String, String> replyTextMap) {
        try {
            this.replyConfigs.setReplyTextMap(replyTextMap);
            return Res.ok("关键字回复列表已更新");
        } catch (OsBizException e) {
            return Res.failed(e.getMsg());
        } catch (NullPointerException e) {
            return Res.failed("配置项：os.wxmp.reply.enabled 未设置");
        }
    }
}
