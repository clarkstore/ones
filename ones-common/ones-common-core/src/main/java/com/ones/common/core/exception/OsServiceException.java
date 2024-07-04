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

/**
 * 服务异常类
 * @author Clark
 * @version 2023/04/23
 */
public class OsServiceException extends OsBaseException {
    /**
     *
     * @param msg
     */
    public OsServiceException(String msg) {
        super(msg);
    }

    /**
     *
     * @param code
     * @param msg
     */
    public OsServiceException(int code, String msg) {
        super(code, msg);
    }
}
