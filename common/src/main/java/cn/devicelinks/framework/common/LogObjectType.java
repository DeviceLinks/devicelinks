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

package cn.devicelinks.framework.common;

import cn.devicelinks.framework.common.annotation.ApiEnum;
import lombok.Getter;

/**
 * 记录日志的对象类型定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@ApiEnum
public enum LogObjectType {
    User("用户"),
    Department("部门"),
    Device("设备"),
    Product("产品"),
    Ota("OTA"),
    FunctionModule("功能模块"),
    GlobalSetting("系统参数"),
    Attribute("属性"),
    DeviceDesiredAttribute("设备期望属性"),
    DeviceReportedAttribute("设备上报属性");

    private final String description;

    LogObjectType(String description) {
        this.description = description;
    }
}
