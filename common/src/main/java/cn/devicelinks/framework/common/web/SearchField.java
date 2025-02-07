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

package cn.devicelinks.framework.common.web;

import lombok.Getter;

/**
 * 检索字段
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
public enum SearchField {
    ID("id", "ID"),
    USER_NAME("name", "用户名称"),
    USER_ACCOUNT("account", "用户账号"),
    USER_PHONE("phone", "手机号"),
    USER_EMAIL("email", "邮箱地址"),
    USER_ACTIVATE_METHOD("activateMethod", "激活方式"),
    DEVICE_CODE("deviceCode", "设备号"),
    DEVICE_NAME("name", "设备名称"),
    DEVICE_TYPE("deviceType", "设备类型"),
    DEVICE_ACTIVATION_TIME("activationTime", "激活时间"),
    DEVICE_LAST_ONLINE_TIME("lastOnlineTime", "最后上线时间"),
    DEVICE_LAST_REPORT_TIME("lastReportTime", "最后上报时间"),
    LOG_ACTION("action", "动作"),
    LOG_OBJECT_TYPE("objectType", "对象类型"),
    LOG_OBJECT_ID("objectId", "对象ID"),
    LOG_MSG("msg", "日志内容"),
    LOG_SUCCESS("success", "是否成功"),
    ;
    private final String field;
    private final String text;

    SearchField(String field, String text) {
        this.field = field;
        this.text = text;
    }
}
