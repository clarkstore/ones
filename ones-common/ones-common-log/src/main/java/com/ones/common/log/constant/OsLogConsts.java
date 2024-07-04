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

package com.ones.common.log.constant;

/**
 * Log常量
 * @author Clark
 * @version 2022-03-03
 */
public interface OsLogConsts {
    /**
     * 处理成功
     */
    String STATUS_SUCCESS = "1";
    /**
     * 处理失败
     */
    String STATUS_FAIL = "0";
    /**
     * 日志类型 1:接口注释
     */
    String TYPE_ANNOTATION = "1";
    /**
     * 日志类型 2:手动调用
     */
    String TYPE_MANUAL = "2";
}
