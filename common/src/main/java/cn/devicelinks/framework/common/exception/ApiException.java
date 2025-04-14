/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.common.exception;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.api.StatusCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 接口异常
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    /**
     * 状态码
     */
    private final StatusCode statusCode;
    /**
     * 消息描述占位符变量列表
     */
    private Object[] messageVariables;

    public ApiException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public ApiException(Throwable cause, StatusCode statusCode, Object... messageVariables) {
        super(cause);
        this.statusCode = statusCode;
        this.messageVariables = messageVariables;
    }

    public ApiException(StatusCode statusCode, Object... messageVariables) {
        super(statusCode.formatMessage(messageVariables));
        this.statusCode = statusCode;
        this.messageVariables = messageVariables;
    }
}
