/*
 *
 *  * Copyright (C) 2021 ClarkChang
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *         http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.ones.kit.oss.util;

import cn.hutool.core.util.StrUtil;
import com.aizuda.common.toolkit.DateUtils;
import com.aizuda.common.toolkit.IoUtils;
import com.aizuda.oss.IFileStorage;
import com.aizuda.oss.model.OssResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 基于aizuda OSS文件存储
 * <p>https://gitee.com/aizuda/aizuda-components/tree/master/aizuda-oss</p>
 *
 * @author Clark
 * @version 2024-06-17
 */
@Slf4j
public class OsOssUtils {
    @Resource
    private IFileStorage fileStorage;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    public OssResult upload(MultipartFile file) throws Exception {
        return this.fileStorage.upload(file.getInputStream(), file.getOriginalFilename());
    }

    /**
     * 上传文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public OssResult upload(String filePath) throws Exception {
        return this.upload(filePath, null);
    }

    /**
     * 上传文件
     * @param filePath
     * @param ossPath 文件对象路径：结尾不要加'/'
     * @return
     * @throws Exception
     */
    public OssResult upload(String filePath, String ossPath) throws Exception {
        File file = this.getFile(filePath);
        String objectName = this.getObjectName(file.getName(), ossPath);
        OssResult ossResult = this.fileStorage.upload(new FileInputStream(file), file.getName(), objectName);
        return ossResult;
    }

    /**
     * 下载文件
     * @param objectName
     * @return
     * @throws Exception
     */
    public InputStream download(String objectName) throws Exception {
        return this.fileStorage.download(objectName);
    }

    /**
     * 下载文件：默认文件名
     * @param response
     * @param objectName
     * @throws Exception
     */
    public void download(HttpServletResponse response, String objectName) throws Exception {
        this.fileStorage.download(response, objectName);
    }

    /**
     * 下载文件：指定文件名
     * @param response
     * @param objectName
     * @throws Exception
     */
    public void download(HttpServletResponse response, String objectName, String fileName) throws Exception {
        InputStream is = this.download(objectName);
        if (null != is) {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, "UTF-8") + StrUtil.DOT + this.fileStorage.getFileSuffix(objectName));
            IoUtils.write(is, response.getOutputStream());
        }
    }

    /**
     * 下载、多个打包 zip 下载
     *
     * @param response
     * @param objectNameList 文件对象名列表
     */
    public void download(HttpServletResponse response, List<String> objectNameList) throws Exception {
        this.fileStorage.download(response, objectNameList);
    }

    /**
     * 删除文件
     *
     * @param objectName 文件对象名
     * @throws Exception
     */
    public boolean delete(String objectName) throws Exception {
        return this.fileStorage.delete(objectName);
    }

    /**
     * 删除文件
     *
     * @param objectNameList 文件对象名列表
     * @throws Exception
     */
    public boolean delete(List<String> objectNameList) throws Exception {
        return this.fileStorage.delete(objectNameList);
    }

    /**
     * 获取文件地址，默认 3 小时有效期
     *
     * @param objectName 文件对象名
     * @return
     */
    public String getUrl(String objectName) throws Exception {
        return this.fileStorage.getUrl(objectName);
    }

    /**
     * 获取文件地址，指定有效期：0 表示永久
     *
     * @param objectName 文件对象名
     * @param duration   期间/小时
     * @return
     */
    public String getUrl(String objectName, int duration) throws Exception {
        // 永久
        if (duration == 0) {
            duration = Integer.MAX_VALUE;
        }
        return this.fileStorage.getUrl(objectName, duration, TimeUnit.HOURS);
    }

    /**
     * 生成File
     *
     * @param filePath
     * @return
     */
    private File getFile(String filePath) {
        //        //加载文件夹
//        File file = new File(filePath);
//        if (file.isDirectory()) {//将该目录下的所有文件放置在一个File类型的数组中
//            File[] fileList = file.listFiles();
//            log.error("=============================");
//            log.error("本次上传共计：" + fileList.length + "个文件");
//            for (File item : fileList) {
//            }
//        } else {
//
//        }

        File file = new File(filePath);
        return file;
    }

    /**
     * 获取存储对象名称
     *
     * @param fileName     文件名
     * @param ossPath 文件对象路径
     * @return 文件名，包含存储路径
     */
    private String getObjectName(String fileName, String ossPath) {
        String suffix = this.fileStorage.getFileSuffix(fileName);
        StringBuffer ojn = new StringBuffer();
        if (StrUtil.isEmpty(ossPath)) {
            ojn.append(DateUtils.nowTimeFormat("yyyyMM")).append("/");
        } else {
            ojn.append(ossPath).append("/");
        }
        ojn.append(UUID.randomUUID()).append(".").append(suffix);
        return ojn.toString();
    }
}