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

package cn.devicelinks.framework.common.api;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 接口统一响应实体
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    /**
     * 接口响应状态码
     */
    private String code;
    /**
     * 状态码对应的描述
     */
    private String message;
    /**
     * 接口响应的数据
     */
    private Object data;
    /**
     * 附加信息
     */
    private final Map<String, Object> additional = new HashMap<>();

    private ApiResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success() {
        return success(null);
    }

    public static <T> ApiResponse success(T data) {
        return success(StatusCode.SUCCESS, data);
    }

    public static <T> ApiResponse success(StatusCode statusCode, T data, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), data);
    }

    public static ApiResponse error(StatusCode statusCode) {
        return new ApiResponse(statusCode.getCode(), statusCode.getMessage(), null);
    }

    public static ApiResponse error(StatusCode statusCode, Object... messageVariables) {
        return new ApiResponse(statusCode.getCode(), statusCode.formatMessage(messageVariables), null);
    }

    public ApiResponse putAdditional(String key, Object value) {
        this.additional.put(key, value);
        return this;
    }

    public ApiResponse putAdditional(Consumer<Map<String, Object>> consumer) {
        consumer.accept(this.additional);
        return this;
    }
}
