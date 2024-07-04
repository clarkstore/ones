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

package com.ones.common.core.exception;

import lombok.*;

/**
 * 自定义异常基类
 * @author Clark
 * @version 2021/04/29
 */
@Getter
@Setter
public abstract class OsBaseException extends RuntimeException {
    /**
     * 异常编号
     */
    protected int code;
    /**
     * 异常内容
     */
    protected String msg;

    /**
     *
     * @param msg
     */
    public OsBaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     *
     * @param code
     * @param msg
     */
    public OsBaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
