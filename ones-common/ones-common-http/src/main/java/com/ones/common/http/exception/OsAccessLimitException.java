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

package com.ones.common.http.exception;

import com.ones.common.core.exception.OsBaseException;

/**
 * 限流异常
 * @author Clark
 * @version 2021/5/10
 */
public class OsAccessLimitException extends OsBaseException {
    private static final String ERR_MSG = "访问超出频率限制，请稍后再试!";
    public OsAccessLimitException() {
        super(ERR_MSG);
    }

    public OsAccessLimitException(String msg) {
        super(msg);
    }

    public OsAccessLimitException(int code, String msg) {
        super(code, msg);
    }
}
