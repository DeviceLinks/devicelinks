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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 状态码定义类
 * <p>
 * 用于{@link ApiResponse}接口数据响应类的初始化
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusCode {
    public static final StatusCode SUCCESS = StatusCode.build("SUCCESS", "success");
    public static final StatusCode SYSTEM_EXCEPTION = StatusCode.build("SYSTEM_EXCEPTION", "系统异常.");
    public static final StatusCode INVALID_TOKEN = StatusCode.build("INVALID_TOKEN", "令牌无效.");
    public static final StatusCode TOKEN_JWT_PARSING_FAILED = StatusCode.build("TOKEN_JWT_PARSING_FAILED", "令牌Jwt解析失败.");
    public static final StatusCode TOKEN_EXPIRED = StatusCode.build("TOKEN_EXPIRED", "令牌已过期.");
    public static final StatusCode ACCESS_TIME_NOT_ALLOWED = StatusCode.build("ACCESS_TIME_NOT_ALLOWED", "该时间段不允许访问数据.");
    public static final StatusCode TODAY_WEEK_NOT_ALLOWED = StatusCode.build("TODAY_WEEK_NOT_ALLOWED", "今日：[%s]，不允许访问数据.");
    public static final StatusCode PARAMETER_CANNOT_NULL = StatusCode.build("PARAMETER_CANNOT_NULL", "请求参数：[%s]，不允许为null.");
    public static final StatusCode PARAMETER_CANNOT_EMPTY = StatusCode.build("PARAMETER_CANNOT_EMPTY", "请求参数：[%s]，不允许为空.");

    /**
     * 状态码
     */
    private String code;
    /**
     * 状态码对应的消息描述
     */
    private String message;

    public static StatusCode build(String code, String message) {
        return new StatusCode(code, message);
    }

    /**
     * 根据变量列表格式化消息描述
     * <p>
     * 对于一些描述存在可以被{@link String#format}解析的占位符时，需要依次传递占位符的参数值进行格式化，格式化后再做接口响应
     *
     * @param messageVariables 消息变量列表
     * @return 格式化后的消息
     */
    public String formatMessage(Object... messageVariables) {
        return String.format(this.message, messageVariables);
    }
}
