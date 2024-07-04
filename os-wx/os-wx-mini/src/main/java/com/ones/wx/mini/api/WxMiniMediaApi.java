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
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *  小程序临时素材接口
 *  Created by BinaryWang on 2017/6/16.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@RequestMapping("${os.wxmini.apiPath}/media")
public class WxMiniMediaApi {
    @Autowired
    private WxMaService wxService;

    /**
     * 上传临时素材
     * @param request request
     * @return 素材的media_id列表，实际上如果有的话，只会有一个
     * @throws WxErrorException WxErrorException
     */
    @PostMapping("/upload")
    public List<String> uploadMedia(HttpServletRequest request) throws WxErrorException {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
//        if (!resolver.isMultipart(request)) {
//            return Lists.newArrayList();
//        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multiRequest.getFileNames();
        List<String> result = Lists.newArrayList();
        while (it.hasNext()) {
            try {
                MultipartFile file = multiRequest.getFile(it.next());
                File newFile = new File(Files.createTempDir(), file.getOriginalFilename());
                this.log.info("filePath is ：" + newFile.toString());
                file.transferTo(newFile);
                WxMediaUploadResult uploadResult = this.wxService.getMediaService().uploadMedia(WxMaConstants.KefuMsgType.IMAGE, newFile);
                this.log.info("media_id ： " + uploadResult.getMediaId());
                result.add(uploadResult.getMediaId());
            } catch (IOException e) {
                this.log.error(e.getMessage(), e);
            }
        }

        return result;
    }

    /**
     * 下载临时素材
     * @param mediaId mediaId
     * @return File
     * @throws WxErrorException WxErrorException
     */
    @GetMapping("/download/{mediaId}")
    public File getMedia(@PathVariable String mediaId) throws WxErrorException {
        return this.wxService.getMediaService().getMedia(mediaId);
    }
}
