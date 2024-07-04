/*
 * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ones.common.log.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 业务接口
 * </p>
 *
 * @author Clark
 * @version 2022-03-03
 */
@Builder
@Getter
@Setter
@ToString
@TableName("biz_log")
public class BizLog extends Model<BizLog> {

    private static final long serialVersionUID = 1L;
    @Tolerate
    public BizLog() {}


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 接口名
     */
    @TableField("name")
    private String name;

    /**
     * 日志类型（1接口注释，2手动调用）
     */
    @TableField("type")
    private String type;

    /**
     * 交互状态（1代表交互成功，0代表交互失败）
     */
    @TableField("status")
    private String status;

    /**
     * 请求参数
     */
    @TableField("req_content")
    private String reqContent;

    /**
     * 应答参数
     */
    @TableField("res_content")
    private String resContent;

    /**
     * 耗费时间毫秒
     */
    @TableField("time_cost")
    private long timeCost;

    /**
     * 异常
     */
    @TableField("exception")
    private String exception;

    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
