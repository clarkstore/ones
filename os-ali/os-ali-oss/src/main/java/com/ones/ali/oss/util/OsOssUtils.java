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

package com.ones.ali.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 阿里OSS服务工具类
 *
 * @author Clark
 * @version 2022-02-23
 */
public class OsOssUtils {
    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    private String endpoint;

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    private String bucketName;

    /**
     * OSS客户端
     */
    private OSS client;

    /**
     * 初始化配置
     */
    public OsOssUtils(String accessKeyId, String accessKeySecret, String endpoint, String bucketName) throws Exception {
        // 生成this.client，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        this.client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        this.bucketName = bucketName;
    }

    /**
     * 取得OSS client对象
     * @return OSS
     */
    public OSS getClient() {
        return this.client;
    }

    /**
     * 取得Bucket信息
     *
     * @return BucketInfo
     */
    public BucketInfo getBucketInfo() {
        BucketInfo info = this.client.getBucketInfo(this.bucketName);
        return info;
    }

    /**
     * 上传文件
     *
     * @param objectName Object完整路径（例如exampledir/exampleobject.txt），不能包含Bucket名称。
     * @param fileStream 文件流
     */
    public void upload(String objectName, InputStream fileStream) {
        this.client.putObject(this.bucketName, objectName, fileStream);
    }

    /**
     * 下载文件
     *
     * @param objectName Object完整路径（例如exampledir/exampleobject.txt），不能包含Bucket名称。
     * @param fullName   本地路径（例如"D:\\localpath\\examplefile.txt"）。
     */
    public void download(String objectName, String fullName) {
        // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
        // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
        this.client.getObject(new GetObjectRequest(this.bucketName, objectName), new File(fullName));
    }

    /**
     * 删除文件或目录。如果要删除目录，目录必须为空。
     *
     * @param objectName Object完整路径（例如exampledir/exampleobject.txt），不能包含Bucket名称。
     */
    public void deleteObject(String objectName) {
        // 删除文件或目录。如果要删除目录，目录必须为空。
        this.client.deleteObject(this.bucketName, objectName);
    }

    /**
     * 删除目录及目录下的所有文件
     *
     * @param prefix 完整路径（例如exampledir），不能包含Bucket名称。
     */
    public void deleteObjectList(String prefix) {
        String nextMarker = null;
        ObjectListing objectListing = null;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucketName)
                    .withPrefix(prefix)
                    .withMarker(nextMarker);

            objectListing = this.client.listObjects(listObjectsRequest);
            if (objectListing.getObjectSummaries().size() > 0) {
                List<String> keys = new ArrayList<String>();
                for (OSSObjectSummary s : objectListing.getObjectSummaries()) {
                    keys.add(s.getKey());
                }
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(this.bucketName).withKeys(keys);
                DeleteObjectsResult deleteObjectsResult = this.client.deleteObjects(deleteObjectsRequest);
                List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
//                try {
//                    for(String obj : deletedObjects) {
//                        String deleteObj =  URLDecoder.decode(obj, "UTF-8");
//                        System.out.println(deleteObj);
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
            }

            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());

        this.deleteObject(prefix);
    }

    /**
     * 取出文件上传路径
     * @param key objectName
     * @param expiration
     * @return 文件上传路径
     */
    public String generatePresignedUrl(String key, Date expiration) {
        String url = this.client
                .generatePresignedUrl(bucketName, key,
                        expiration).toString();
        return url;
    }
}