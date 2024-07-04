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

package com.ones.kit.extra.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

/**
 * 基于hutool邮件工具类
 * html邮件模板配置在：src/main/resources/templates
 *
 * @author Clark
 * @version 2021-01-05
 */
@Slf4j
public class OsMailUtils {
    @Resource
    private TemplateEngine templateEngine;

    /**
     * 邮件账户对象
     * 默认配置读取classpath（在标准Maven项目中为src/main/resources）的config目录下新建mail.setting文件
     * 如通过数据库配置，通过@PostConstruct的initAccount()方法覆盖
     */
    private MailAccount account = null;

    /**
     * 设置账号-覆盖默认配置
     * @param account 邮箱账户
     */
    public void setMailAccount(MailAccount account) {
        this.account = account;
    }

    /**
     * 发送html模板邮件
     *
     * @param toAddr  收件人
     * @param tplCode 模板文件名
     * @param map     邮件内容
     */
    public void sendMail(String toAddr, String tplCode, Map<String, Object> map) {
        List<String> toAddrList = CollUtil.newArrayList();
        toAddrList.add(toAddr);
        this.sendMail(toAddrList, tplCode, map);
    }

    /**
     * 发送html模板邮件
     *
     * @param toAddrList 收件人列表
     * @param tplCode    模板文件名
     * @param map        邮件内容
     */
    public void sendMail(List<String> toAddrList, String tplCode, Map<String, Object> map) {
        String subject = (map == null || map.get("subject") == null) ? tplCode : map.get("subject").toString();
        this.sendMail(toAddrList, tplCode, subject, map);
    }

    /**
     * 发送html模板邮件
     *
     * @param toAddrList 收件人
     * @param tplCode    模板文件名
     * @param subject    邮件主题
     * @param map        邮件内容
     */
    public void sendMail(List<String> toAddrList, String tplCode, String subject, Map<String, Object> map) {
        // 取得创建邮件内容
        try {
            log.debug("html邮件开始发送");
            Context context = new Context();
            context.setVariables(map);
            String content = templateEngine.process(tplCode, context);
            MailUtil.send(this.account, toAddrList, subject, content, Boolean.TRUE);
            log.debug("html邮件发送结束并成功！主题：" + subject);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("html邮件发送失败！主题：" + subject);
        }
    }
}
