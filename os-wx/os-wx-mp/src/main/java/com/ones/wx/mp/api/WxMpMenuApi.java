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

import com.ones.common.core.util.Res;
import com.ones.wx.mp.model.dto.MenuConfigs;
import com.ones.wx.mp.util.OsWxMpUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号菜单接口
 *
 * @author Clark
 * @version 2021-04-23
 */
@Slf4j
@RestController
@RequestMapping("${os.wxmp.apiPath}/menu")
public class WxMpMenuApi {
    @Autowired(required = false)
    private MenuConfigs menuConfigs;
    @Autowired
    private OsWxMpUtils osWxMpUtils;

    @GetMapping("/create")
    public Res create() {
        try {
            this.osWxMpUtils.menuCreate(this.menuConfigs);
            return Res.ok("菜单构建成功");
        } catch (WxErrorException e) {
            return Res.failed(e.getMessage());
        } catch (NullPointerException e) {
            return Res.failed("配置项：os.wxmp.menu.enabled 未设置");
        }
    }

    @PostMapping("/create")
    public Res create(@RequestBody MenuConfigs menu) {
        try {
            this.osWxMpUtils.menuCreate(menu);
            return Res.ok("菜单构建成功");
        } catch (WxErrorException e) {
            return Res.failed(e.getMessage());
        }
    }
}
